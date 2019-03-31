package com.ortakat.kudos.backend.controller;

import com.google.common.cache.LoadingCache;
import com.ortakat.kudos.backend.api.firestore.BaseObject;
import com.ortakat.kudos.backend.api.firestore.FirebaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class MainController {

	private final FirebaseService firebaseService;

	private final LoadingCache<String, Map<String, String>> cacheMap;

	@GetMapping("/test")
	@ResponseBody
	public String test() throws Exception {
		BaseObject hubs = BaseObject.getInstance("hubs").setKey("sda").read(firebaseService);
		log.info("DDDDebug ", hubs);
		BaseObject integrations = hubs.getChildObject("integrations").setKey("slack").read(firebaseService);
		integrations.setInputs(new HashMap<String, Object>() {{
			put("access_token", "tken");
			put("scope", "scope");
			put("user_id", "userID");
			put("team_name", "teamName");
			put("team_id", "teamId");
		}});
		integrations.save(firebaseService);
		return "success";
	}


	private String key = "sdadas";

	@GetMapping("test2")
	@ResponseBody
	public String test2() {
		String cache = cacheMap.getUnchecked("base").get(key);
		System.out.println(cache);
		cacheMap.getUnchecked("base").put(key, "dsfsdfsdfs");
		return "wwqda";
	}


}
