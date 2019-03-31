//package com.ortakat.kudos.backend.api.mailapi.handler;
//
//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.services.lambda.runtime.LambdaLogger;
//import com.google.gson.Gson;
//import com.ortakat.lambda.LambdaHandler;
//import com.ortakat.lambda.message.ApiGatewayResponse;
//import com.ortakat.lambda.message.Response;
//import com.ortakat.kudos.backend.api.mailapi.enums.MailServerEnum;
//import com.ortakat.kudos.backend.api.mailapi.mailserver.Grid;
//import com.ortakat.kudos.backend.api.mailapi.mailserver.SMTP;
//import com.ortakat.kudos.backend.api.mailapi.model.MailPost;
//
//import java.util.Collections;
//import java.util.Map;
//import java.util.Properties;
//
//public class MessageHandler implements LambdaHandler {
//
//	private static LambdaLogger LOG = null;
//
//	private static Properties properties;
//
//
//	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
//		try {
//			LOG = context.getLogger();
//			String body = "";
//			MailPost mailPost = new Gson().fromJson((String) input.get("body"), MailPost.class);
//			if (mailPost.getMailServer() == MailServerEnum.SMTP) {
//				body = new SMTP().sendMail(mailPost);
//			} else if (mailPost.getMailServer() == MailServerEnum.GRID) {
//				body = new Grid().sendMail(mailPost);
//			} else {
//				throw new Exception("Unexpected Mail Server");
//			}
//			return ApiGatewayResponse.builder()
//					.setStatusCode(200)
//					.setObjectBody(new Response("Message Sended successfully! " + body, input))
//					.setHeaders(Collections.singletonMap("X-Powered-By", "Solid-ICT"))
//					.build();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return ApiGatewayResponse.builder()
//					.setStatusCode(501)
//					.setObjectBody(new Response("Message is not Sended! Message: " + ex.getLocalizedMessage(), input))
//					.setHeaders(Collections.singletonMap("X-Powered-By", "Ortak@"))
//					.build();
//		}
//	}
//
//
//}
