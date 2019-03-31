package com.ortakat.kudos.backend.model.firebase;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Workspace {


	public static void main(String[] args) throws Exception {
//		FirebaseClient.init(new File(ClassLoader.getSystemClassLoader().getResource("kudos-d0fb3-firestore-adminsdk-w80g7-701bc206f6.json").getFile()), "kudos-d0fb3");
//		FirebaseClient.init();

//		BaseObject hubs = new BaseObject("hubs").setKey("1hBnMrSjEVKyRnP8WCMj").read();

//		String userFindKey = KudosEvent.userFindKey("UGTEdSTasas");
//		BaseObject users = new BaseObject("users").read();
//		Optional<String> userFindKey = Finder.findKeyFromChildMap(users, "UGTEdSTasas", "integration_id", "slack_user_id");
//		System.out.println(userFindKey);
//

		//add User

//		BaseObject users = new BaseObject("users");
//		Map<String, Object> inputUser = new HashMap<String, Object>() {{
//			put("display_name", displayName);
//			put("email", email);
//			put("create_at", new Date());
//			put("activate", false);
//			put("user_role", "user");
//			put("photo", photoUrl);
//			put("integration_id", new HashMap<String, Object>() {{
//				put("slack_user_id", slackUserId);
//			}});
//		}};
//
//		Adder.addDB(users, inputUser);


//		if (!userFind){
//			System.out.println(KudosEvent.userAdd("TestMain", "testmain@test", "UGTEdSTasas", "photoURL"));
//		}
//
//


//		KudosEvent.userAddReference("1hBnMrSjEVKyRnP8WCMj", "fKkM4Spf078uhoja4lr3", "userRef2");


		//Eğer yoksa oluşturup hem usersa hemde huba referas eklemeli


		// Eğer user varsa gidip hubs ın altına referans eklemeli


		//Kudosa kayıt atılmalı


//		BaseObject workSpace = new BaseObject("Workspace")
//				.addElement("name", "workspace1")
//				.addElement("date", new Date());
//
//		workSpace = workSpace.save();
//
//
//		BaseObject slack = workSpace.getChildObject("Slack")
//				.addElement("accessToken", "accessToken")
//				.addElement("scope", "scope1")
//				.addElement("teamName", "teamName1");
//
//		slack = slack.save();
//		System.out.println(new ObjectMapper().writeValueAsString(slack));
//
//		slack = slack.read();
//		System.out.println(slack);
//
//		List<BaseObject> users = Arrays.asList(workSpace.getChildObject("User")
//				.addElement("email", "emre@emre")
//				.addElement("password", "1234")
//				.addElement("admin", false)
//				.addElement("slackId", "SlackId"), workSpace.getChildObject("User")
//				.addElement("email", "emre@emre2")
//				.addElement("password", "12342")
//				.addElement("admin", false)
//				.addElement("slackId", "SlackId2"));
//
//		users.forEach(e -> e = e.save());
//
//		users.forEach(e -> {
//			try {
//				System.out.println(new ObjectMapper().writeValueAsString(e));
//			} catch (JsonProcessingException e1) {
//				e1.printStackTrace();
//			}
//		});
//
//		List<BaseObject> kudos = Arrays.asList(workSpace.getChildObject("Kudos")
//						.addReferenceElement("from", users.get(0))
//						.addElement("text", "Thanks ")
//						.addReferenceElement("to", users.get(1)),
//				workSpace.getChildObject("Kudos")
//						.addReferenceElement("from", users.get(1))
//						.addElement("text", "Thanks 2")
//						.addReferenceElement("to", users.get(0))
//		);
//
//		kudos.forEach(e -> e = e.save());
//		System.out.println(new ObjectMapper().writeValueAsString(kudos));
//		BaseObject kudosRead = kudos.get(0).read();
//		String fromUserId = (String) kudosRead.getInputs().get("from");
//		BaseObject read = workSpace.getChildObject("User").setKey(fromUserId).read();
//		System.out.println("Kudos1 Users:" + new ObjectMapper().writeValueAsString(read));


	}
}
