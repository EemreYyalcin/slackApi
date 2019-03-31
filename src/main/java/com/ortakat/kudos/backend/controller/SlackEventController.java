package com.ortakat.kudos.backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.LoadingCache;
import com.ortakat.kudos.backend.api.firebase.BaseObject;
import com.ortakat.kudos.backend.api.firebase.FirebaseDbService;
import com.ortakat.kudos.backend.api.firebase.FirebaseService;
import com.ortakat.kudos.backend.model.EventRequest;
import com.ortakat.kudos.backend.util.Constrants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/slack")
@RequiredArgsConstructor
@Slf4j
public class SlackEventController {

	private final FirebaseService firebaseService;

	private final LoadingCache<String, Map<String, String>> cacheMap;

	private final FirebaseDbService firebaseDbService;

	@PostMapping("/event")
	@ResponseBody
	public String eventProcess(@RequestBody EventRequest eventRequest) throws Exception {

		Optional<String> repeatEvent = Optional.ofNullable(cacheMap.getUnchecked(Constrants.cacheMapKey).get(eventRequest.getEvent_time()));
		if (repeatEvent.isPresent()) {
			return eventRequest.getChallenge();
		}
		cacheMap.getUnchecked(Constrants.cacheMapKey).put(eventRequest.getEvent_time(), eventRequest.getEvent_time());

		System.out.println("Event Control " + new ObjectMapper().writeValueAsString(eventRequest));
		if (!eventRequest.getType().equals("message") && !eventRequest.getType().equals("event_callback")) {
			return eventRequest.getChallenge();
		}

		log.info("D2 ");
		Optional<String> findHubKey = firebaseDbService.slackTeamFindHubKey(eventRequest.getTeam_id());
		System.out.println(findHubKey);
		findHubKey.orElseThrow(Exception::new);

		BaseObject hubs = BaseObject.getInstance(Constrants.hubs).setKey(findHubKey.get()).read(firebaseService);

		String[] words = eventRequest.getEvent().getText().split(" ");

		if (words.length <= 0) {
			System.out.println("Kudos Error Text: " + eventRequest.getEvent().getText());
			return eventRequest.getChallenge();
		}


		Optional<BaseObject> slackProps = firebaseDbService.getAccessToken(hubs);
		slackProps.orElseThrow(Exception::new);

		if (!eventRequest.getEvent().getChannel_type().equals("im")){
			String botUserId = (String)slackProps.get().getInputs().get("bot_user_id");
			if (!eventRequest.getEvent().getText().contains(botUserId)){
				log.info("Message not mention");
				return eventRequest.getChallenge();
			}
				eventRequest.getEvent().setText(eventRequest.getEvent().getText().replaceAll("<@" + botUserId + ">",Constrants.thanksText));
		}
		log.info("D3 ");

		List<String> boardsKey = Arrays.stream(words)
				.filter(e -> e.startsWith("#"))
				.map(e -> e.substring(1))
				.collect(Collectors.toList());

		List<String> toUsers = Arrays.stream(words)
				.filter(e -> e.startsWith("<@"))
				.map(e -> e.substring(2, e.indexOf(">")))
				.collect(Collectors.toList());

		List<BaseObject> boards = new ArrayList<>();

		if (boardsKey.size() == 0) {
			boards.add(firebaseDbService.findBoard(hubs, null).orElseThrow(Exception::new));
		} else {
			boards = boardsKey.stream().map(e -> {
				try {
					return firebaseDbService.findBoard(hubs, e).get();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				return null;
			}).collect(Collectors.toList());
		}
		log.info("D4 ");

		if (toUsers.size() <= 0) {
			System.out.println("To User not Found: " + eventRequest.getEvent().getText());
			return eventRequest.getChallenge();
		}
		log.info("D5 ");

		List<String> toUserList = new ArrayList<>();
		for (int i = 0; i < toUsers.size(); i++) {
			Optional<String> userKey = firebaseDbService.findUser(toUsers.get(i));
			if (userKey.isPresent()) {
				firebaseDbService.userAddReference(boards, "/" + Constrants.users + "/" + userKey.get());
				toUserList.add(userKey.get());
				continue;
			}
			BaseObject user = firebaseDbService.addUser(Optional.ofNullable((String)slackProps.get().getInputs().get("access_token")), toUsers.get(i));
			if (Objects.isNull(user)) {
				throw new Exception("User not added: " + toUsers.get(i));
			}
			firebaseDbService.userAddReference(boards, "/" + Constrants.users + "/" + userKey.get());
			toUserList.add(user.getKey());
		}
		log.info("D6 ");

		String fromUser = firebaseDbService.findUser(eventRequest.getEvent().getUser()).orElseGet(() -> {
			try {
				return firebaseDbService.addUser(Optional.ofNullable((String) slackProps.get().getInputs().get("access_token")), eventRequest.getEvent().getUser()).getKey();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		});
		if (Objects.isNull(fromUser)) {
			throw new Exception("From User fail ");
		}
		log.info("D7 ");

		firebaseDbService.addKudos(boards, eventRequest, fromUser, toUserList);
		log.info("D8 ");
		return eventRequest.getChallenge();
	}


}
