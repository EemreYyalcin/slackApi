package com.ortakat.kudos.backend.api.firebase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ortakat.kudos.backend.model.EventRequest;
import com.ortakat.kudos.backend.service.SlackService;
import com.ortakat.kudos.backend.util.Constrants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FirebaseDbService {

	private final FirebaseService firebaseService;
	private final SlackService slackService;

	public Optional<BaseObject> findBoard(BaseObject hubs, String boardName) throws Exception {
		Optional<String> boardKey = Optional.empty();
		BaseObject boards = hubs.getChildObject(Constrants.boards).read(firebaseService);
		if (!(Objects.isNull(boardName) || boardName.isEmpty())) {
			boardKey = boards.getAllData()
					.stream()
					.filter(e -> e.getData().get("name").equals(boardName))
					.map(e -> e.getId())
					.findFirst();
		}
		//select default
		if (!boardKey.isPresent()) {
			boardKey = boards.getAllData()
					.stream()
					.filter(e -> e.getData().get("default").equals(true))
					.map(e -> e.getId())
					.findFirst();
		}
		return Optional.ofNullable(hubs.getChildObject(Constrants.boards).setKey(boardKey.get()).read(firebaseService));
	}


	public Optional<String> slackTeamFindHubKey(String teamId) throws Exception {
		BaseObject hubsAll = BaseObject.getInstance("hubs").read(firebaseService);
		return hubsAll.getAllData()
				.stream()
				.filter(e -> {
					try {
						return BaseObject.getInstance(e.getReference().getPath() + "/" + Constrants.integrations)
								.setKey("slack")
								.read(firebaseService)
								.getInputs()
								.get("team_id")
								.equals(teamId);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					return false;
				})
				.map(e -> e.getId())
				.findFirst();
	}

	public Optional<BaseObject> getAccessToken(BaseObject hubs) throws Exception {
		BaseObject slack = hubs.read(firebaseService).getChildObject(Constrants.integrations).setKey("slack").read(firebaseService);
		return Optional.of(slack);
	}

	public Optional<String> findUser(String slackUserId) throws Exception {
		// User Finder
		return BaseObject.getInstance(Constrants.users).read(firebaseService).getAllData().stream()
				.filter(e -> (((Map<String, Object>) e.getData().get("integration_id")).get("slack_user_id")).equals(slackUserId))
				.map(e -> e.getId())
				.findFirst();
	}


	public BaseObject addUser(Optional<String> accessToken, String userId) throws Exception {

		Map<String, Object> userProfile = slackService.getUserProfile(accessToken.get(), userId);
		System.out.println("---> " + new ObjectMapper().writeValueAsString(userProfile));
		System.out.println("accessToken:" + accessToken + " -- userID:" + userId);
		if (!userProfile.get("ok").equals(true)) {
			throw new Exception("To User not Found Slack " + userId);
		}

		Map<String, Object> profile = (Map<String, Object>) userProfile.get("profile");

		String displayName = (String) profile.get("display_name");
		if (Objects.isNull(displayName) || displayName.isEmpty()) {
			displayName = (String) profile.get("real_name");
		}
		final String displayNameFinal = displayName;

		BaseObject users = BaseObject.getInstance(Constrants.users);
		users.setInputs(new HashMap<String, Object>() {{
			put("display_name", displayNameFinal);
			put("email", profile.get("email"));
			put("create_at", new Date());
			put("activate", false);
			put("user_role", "user");
			put("photo", profile.get("image_72"));
			put("integration_id", new HashMap<String, Object>() {{
				put("slack_user_id", userId);
			}});
		}});
		return users.save(firebaseService);
	}

	public void userAddReference(List<BaseObject> boards, String userRef) throws Exception {
		boards.stream().forEach(e -> {
			try {
				List<String> userRefs = (List<String>) e.getInputs().get("users");
				userRefs.add(userRef);
				e.getInputs().put("users", userRefs);
				e.save(firebaseService);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}

	public void addKudos(List<BaseObject> boards, EventRequest eventRequest, String fromUser, List<String> toUserList) {
		boards.forEach(e ->
				{
					try {
						e.getChildObject(Constrants.kudos)
								.setInputs(new HashMap<String, Object>() {{
									put("creat_at", new Date());
									put("description", eventRequest.getEvent().getText());
									put("from", "/" + Constrants.users + "/" + fromUser);
									put("to", toUserList.stream().map(e -> "/" + Constrants.users + "/" + e).collect(Collectors.toList()));
									put("type", "kudos");
								}}).save(firebaseService);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
		);
	}



}
