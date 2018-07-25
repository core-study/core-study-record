package com.tmon.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableResourceServer
@EnableAuthorizationServer
@SpringBootApplication
public class SecurityApplication {
	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}
}

@RestController
class Greeting2Controller{
	@GetMapping("/")
	Greet greet(){
		return new Greet("Hello World!");
	}
}

class Greet {
	private String message;
	public Greet() {}
	public Greet(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
