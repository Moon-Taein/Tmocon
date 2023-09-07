package com.example.demo.my;

import org.json.simple.JSONArray;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnlineStreamer {
	private String user_name;
	private String game_id;
	private String game_name;
	private String title;
	private Long viewer_count;
	private String started_at;
	private String thumbnail_url;
	private JSONArray tags;
	private String user_login;
	private String user_id;

}
