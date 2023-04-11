package com.lio.BlogApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lio.BlogApi.services.common.jwtToken.JwtTokenService;

@SpringBootApplication
public class BlogApiApplication implements CommandLineRunner {

	@Autowired
	private JwtTokenService jwtTokenService;

	@Override
	public void run(String... args) throws Exception {
		String token = jwtTokenService.generateToken(null);
		// System.out.println(this.jwtTokenService.isTokenExpire(token));
		System.out.println(this.jwtTokenService.isValidToken(token));
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}

}