package com.lio.BlogApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lio.BlogApi.services.common.jwtToken.JwtTokenService;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class BlogApiApplication implements CommandLineRunner {

	@Autowired
	private JwtTokenService jwtTokenService;

	@Override
	public void run(String... args) throws Exception {
		Map<String,String> map = new HashMap<>();
		map.put("email","myominhtoo2003@gmail.com");
		String token = jwtTokenService.generateToken(map);
		// System.out.println(this.jwtTokenService.isTokenExpire(token));
		System.out.println(this.jwtTokenService.getTokenPayload(token));
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}

}