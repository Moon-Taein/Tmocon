package com.example.demo.my;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follow {
	private String to_id;
	private String to_name;
	private String followed_at;
}
