package com.example.demo.my;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyObject {
	private String broadcaster_login;
	private String display_name;
	private String id;

}
