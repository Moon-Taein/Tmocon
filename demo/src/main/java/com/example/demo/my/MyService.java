package com.example.demo.my;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Component
public class MyService {
	// OkHttp 클라이언트 객체 생성
	OkHttpClient client = new OkHttpClient();

	static String clientIdProp;
	static String authorizationProp;
	static {
		try {
			Properties prop = new Properties();
			ClassLoader classLoader = MyService.class.getClassLoader();
			prop.load(classLoader.getResourceAsStream("AuthInformation.properties"));
			clientIdProp = prop.getProperty("client-id");
			System.out.println(clientIdProp);
			authorizationProp = prop.getProperty("Authorization");
			System.out.println(authorizationProp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object getUserInfo(String targetUrl, String param) {

		// 아래 메소드를 통해 얻어진 json 데이터중에
		// "broadcaster_login": "mgi3988"
		// param에 넣은 id와 broadcaster_login 가 일치하는 데이터를 가져오기

		List<MyObject> list = new ArrayList<>();

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String url = targetUrl + param;

			// GET 요청 객체 생성
			Request.Builder builder = new Request.Builder().url(url).get();
			builder.addHeader("client-id", clientIdProp);
			builder.addHeader("Authorization", authorizationProp);
			Request request = builder.build();

			// OkHttp 클라이언트로 GET 요청 객체 전송
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				// 응답 받아서 처리
				ResponseBody body = response.body();
				if (body != null) {
					String bodyToString = body.string();
					System.out.println("Response:" + bodyToString);
					JSONParser parser = new JSONParser();

					JSONObject jsonObject = (JSONObject) parser.parse(bodyToString);
					System.out.println("body를 파싱한 값: " + jsonObject);
					JSONArray jsonArray = (JSONArray) jsonObject.get("data");
					System.out.println("data의 값: " + jsonArray.get(0));

//					List<MyObject> parsingDataList = objectMapper.readValues(jsonArray,
//							new TypeReference<List<MyObject>>() {
//							});
//					System.out.println(parsingDataList.toString());

					for (Object o : jsonArray) {
						JSONObject jsonObjectTarget = (JSONObject) o;
						list.add(
								MyObject.builder().broadcaster_login((String) jsonObjectTarget.get("broadcaster_login"))
										.display_name((String) jsonObjectTarget.get("display_name"))
										.id((String) jsonObjectTarget.get("id")).build());
					}
					System.out.println("parsing all jsonData: " + list.toString());

					for (MyObject my : list) {
						if (my.getBroadcaster_login().equals(param)) {
							System.out.println("해당 param의 정보: " + my.toString());
							return my;
						}
					}
				}

			} else
				System.err.println("Error Occurred");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Follow> getUserFollow(String targetUrl, String param) {
		// 아래 메소드를 통해 얻어진 json 데이터중에
		// "broadcaster_login": "mgi3988"
		// param에 넣은 id와 broadcaster_login 가 일치하는 데이터를 가져오기

		List<Follow> list = new ArrayList<>();

		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(param);
		try {
			String url = targetUrl + "?from_id=" + param + "&first=100";
			System.out.println(url);

			// GET 요청 객체 생성
			Request.Builder builder = new Request.Builder().url(url).get();
			builder.addHeader("client-id", clientIdProp);
			builder.addHeader("Authorization", authorizationProp);
			Request request = builder.build();

			// OkHttp 클라이언트로 GET 요청 객체 전송
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				// 응답 받아서 처리
				ResponseBody body = response.body();
				if (body != null) {
					String bodyToString = body.string();
					JSONParser parser = new JSONParser();

					JSONObject jsonObject = (JSONObject) parser.parse(bodyToString);
					JSONArray jsonArray = (JSONArray) jsonObject.get("data");
//							List<MyObject> parsingDataList = objectMapper.readValues(jsonArray,
//									new TypeReference<List<MyObject>>() {
//									});
//							System.out.println(parsingDataList.toString());

					for (Object o : jsonArray) {
						JSONObject jsonObjectTarget = (JSONObject) o;
						list.add(Follow.builder().to_name((String) jsonObjectTarget.get("to_name"))
								.followed_at((String) jsonObjectTarget.get("followed_at"))
								.to_id((String) jsonObjectTarget.get("to_id")).build());
					}
					System.out.println("parsing all jsonData: " + list.toString());

					return list;
				}

			} else
				System.err.println("Error Occurred");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Map<String, List<?>> getOnOrOffStreamers(List<Follow> list) {
		Map<String, List<?>> map = new HashMap<>();
		List<OnlineStreamer> onlineList = new ArrayList<>();
		List<OfflineStreamer> offlineList = new ArrayList<>();

		// 멀티쓰레드로 리퀘스트 한번에 여러개 보내고 받아보기
		// 버퍼 사용?
		// api parameter 를 & 로 연결해서 여러개 한번에 받기
		// for문을 통해서 모든 user_id 를 담은 url 만들고
		// streams api 통하면 방송하는 사람만 가져오게된다
		// onlineList 에 없는 애들은 users api 사용해서 정보 가져오기
		try {
			String url = "https://api.twitch.tv/helix/streams?user_id=";
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					url += list.get(i).getTo_id();
				} else {
					url += "&user_id=";
					url += list.get(i).getTo_id();
				}
			}
			System.out.println("현재 url의 형태: " + url);

			// GET 요청 객체 생성
			Request.Builder builder = new Request.Builder().url(url).get();
			builder.addHeader("client-id", clientIdProp);
			builder.addHeader("Authorization", authorizationProp);
			Request request = builder.build();

			// OkHttp 클라이언트로 GET 요청 객체 전송
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				// 응답 받아서 처리
				ResponseBody body = response.body();
				if (body != null) {
					String bodyToString = body.string();
					JSONParser parser = new JSONParser();

					JSONObject jsonObject = (JSONObject) parser.parse(bodyToString);
					JSONArray jsonArray = (JSONArray) jsonObject.get("data");
					System.out.println("온라인 스트리머의 데이터 사이즈는 " + jsonArray.size());

					for (Object o : jsonArray) {
						JSONObject jsonObjectTarget = (JSONObject) o;
						onlineList.add(OnlineStreamer.builder().user_name((String) jsonObjectTarget.get("user_name"))
								.game_id((String) jsonObjectTarget.get("game_id"))
								.game_name((String) jsonObjectTarget.get("game_name"))
								.title((String) jsonObjectTarget.get("title"))
								.viewer_count((Long) jsonObjectTarget.get("viewer_count"))
								.started_at((String) jsonObjectTarget.get("started_at"))
								.thumbnail_url((String) jsonObjectTarget.get("thumbnail_url"))
								.tags((JSONArray) jsonObjectTarget.get("tags"))
								.user_id((String) jsonObjectTarget.get("user_id"))
								.user_login((String) jsonObjectTarget.get("user_login")).build());
					}

					// onlineList에 포함되지 않은 offline 애들 처리하기
					List<String> onlineIdList = new ArrayList<>();
					for (OnlineStreamer on : onlineList) {
						onlineIdList.add(on.getUser_id());
					}
					System.out.println("온라인 스트리머 비교 리스트 " + onlineIdList);

					// list의 0번째가 방송중이면 ?id=&id=12312 이렇게 되어버린다
					String offUrl = "https://api.twitch.tv/helix/users";
					for (int i = 0; i < list.size(); i++) {
						if (!onlineIdList.contains(list.get(i).getTo_id())) {
							offUrl += "&id=";
							offUrl += list.get(i).getTo_id();
						}
					}
					System.out.println("off url: " + offUrl);
					offUrl = offUrl.replaceFirst("&", "?");
					System.out.println("replace off Url : " + offUrl);
					offlineList = getOffLineStreamer(offUrl);
				}

			} else {
				System.err.println("Error Occurred");
			}

//			for (Follow f : list) {
//				String target_id = f.getTo_id();
//				String url = "https://api.twitch.tv/helix/streams?user_id=" + target_id;
//				System.out.println("streamer 들의 정보 " + url);
//

//

//				if (response.isSuccessful()) {

//					if (body != null) {

//
//						// online
//						if (jsonArray.size() > 0) {
//							JSONObject jsonObjectTarget = (JSONObject) jsonArray.get(0);
//							System.out.println("data" + jsonArray.get(0));

//							// offline
//						} else {
//							// okhttp 한번더 근데 따로 메소드를 파놓자
//
//							OfflineStreamer offStreamer = getOffLineStreamer(target_id);
//							offlineList.add(offStreamer);
//
//						}
//
//					}

			System.out.println("online streamers data: " + onlineList.toString());
			System.out.println("offline streamers data: " + offlineList.toString());
			map.put("online", onlineList);
			map.put("offline", offlineList);
			return map;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private List<OfflineStreamer> getOffLineStreamer(String url) {
		List<OfflineStreamer> offlist = new ArrayList<>();
		try {

			// GET 요청 객체 생성
			Request.Builder builder = new Request.Builder().url(url).get();
			builder.addHeader("client-id", clientIdProp);
			builder.addHeader("Authorization", authorizationProp);
			Request request = builder.build();

			// OkHttp 클라이언트로 GET 요청 객체 전송
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				// 응답 받아서 처리
				ResponseBody body = response.body();
				if (body != null) {
					String bodyToString = body.string();
					JSONParser parser = new JSONParser();

					JSONObject jsonObject = (JSONObject) parser.parse(bodyToString);
					JSONArray jsonArray = (JSONArray) jsonObject.get("data");

					System.out.println("asdfasdfasdf: " + jsonArray.toJSONString());

					for (Object o : jsonArray) {
						JSONObject jsonObjectTarget = (JSONObject) o;
						offlist.add(
								OfflineStreamer.builder().display_name((String) jsonObjectTarget.get("display_name"))
										.profile_image_url((String) jsonObjectTarget.get("profile_image_url")).build());

					}
					return offlist;

				}

			} else
				System.err.println("Error Occurred");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Object getUser(String targetUrl) {

		// 아래 메소드를 통해 얻어진 json 데이터중에
		// "broadcaster_login": "mgi3988"
		// param에 넣은 id와 broadcaster_login 가 일치하는 데이터를 가져오기

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			String url = targetUrl;

			// GET 요청 객체 생성
			Request.Builder builder = new Request.Builder().url(url).get();
			Request request = builder.build();

			// OkHttp 클라이언트로 GET 요청 객체 전송
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				// 응답 받아서 처리
				ResponseBody body = response.body();
				if (body != null) {
					String bodyToString = body.string();
					System.out.println("Response:" + bodyToString);
					JSONParser parser = new JSONParser();

//					JSONObject jsonObject = (JSONObject) parser.parse(bodyToString);
//					System.out.println("body를 파싱한 값: " + jsonObject);
//					JSONArray jsonArray = (JSONArray) jsonObject.get("data");
//					System.out.println("data의 값: " + jsonArray.get(0));

				}

			} else
				System.err.println("Error Occurred");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
