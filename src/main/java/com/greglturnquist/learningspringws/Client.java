package com.greglturnquist.learningspringws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class Client {

	public static void main(String[] args) {
		SpringApplication.run(Client.class, args);
	}

}
