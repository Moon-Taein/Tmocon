package com.example.demo.my;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
	@Autowired
	MyService service;

	@GetMapping("/my")
	public String my(Model model) {
		MyObject myObject = (MyObject) service.getUserInfo("https://api.twitch.tv/helix/search/channels?query=",
				"mgi3988");
		model.addAttribute("target", myObject);
		return "my";
	}

	@GetMapping("/follow")
	public String follow(Model model, HttpServletRequest request) {

		// username form에서 받아오기
		String username = request.getParameter("username");
		System.out.println("username의 값은: " + username);

		// 1. mgi3988 값을 트위치 로그인을 통해 가져와야 한다. - 굳이 Oauth 없이도 내 토큰으로 가능
		// 2. twitch webhook eventSub 통해서 알람 구현 - 중요포인트
		// 3. 추가 기능 더 생각하기 리모컨을 동작하는거 생각하기

		// 해당 사용자의 정보 가져오기(필요업다)
		MyObject myObject = (MyObject) service.getUserInfo("https://api.twitch.tv/helix/search/channels?query=",
				username);

		// 사용자의 팔로우 리스트 가져오기
		List<Follow> list = service.getUserFollow("https://api.twitch.tv/helix/users/follows", myObject.getId());

		// 온라인, 오프라인 리스트를 가져오기
		Map<String, List<?>> targetMap = service.getOnOrOffStreamers(list);
		List<OnlineStreamer> online = (List<OnlineStreamer>) targetMap.get("online");
		List<OfflineStreamer> offline = (List<OfflineStreamer>) targetMap.get("offline");

		// 온라인 스트리머의 썸네일 이미지 size 조정
		online = sizingImage(online);

		model.addAttribute("list", list);
		model.addAttribute("online", online);
		model.addAttribute("offline", offline);
		model.addAttribute("user_id", username);
		return "follow";
	}

	@GetMapping("/")
	public String getUserName() {
		return "inputUserName";
	}

	@GetMapping("/auth")
	public void auth(Model model) {
		service.getUser(
				"https://id.twitch.tv/oauth2/authorize?client_id=577e3iq6fdnqon0lzxr59ghwei2d3r&redirect_uri=http://localhost:8080/&response_type=token&scope=channel:read:subscriptions");
	}

	// image resizing method
	public List<OnlineStreamer> sizingImage(List<OnlineStreamer> list) {
		for (OnlineStreamer on : list) {
			String setImageSize = on.getThumbnail_url().replace("{width}", "295");
			String setImageSizeFinal = setImageSize.replace("{height}", "200");
			on.setThumbnail_url(setImageSizeFinal);
		}
		return list;
	}

}