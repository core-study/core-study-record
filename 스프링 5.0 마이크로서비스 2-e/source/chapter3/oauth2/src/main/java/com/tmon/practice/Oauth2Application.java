package com.tmon.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableResourceServer
//@EnableAuthorizationServer
@SpringBootApplication
public class Oauth2Application {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2Application.class, args);
	}
}
