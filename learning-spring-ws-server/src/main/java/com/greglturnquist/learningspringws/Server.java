package com.greglturnquist.learningspringws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(WebServiceServerConfig.class)
@EnableAutoConfiguration
public class Server {

	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
	}
}
