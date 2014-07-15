package com.greglturnquist.learningspringws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class NetworkEventConfiguration {

	@Bean
	Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.greglturnquist.wsdl");
		return marshaller;
	}

	@Bean
	NetworkEventClient client(Jaxb2Marshaller marshaller) {
		NetworkEventClient networkEventClient = new NetworkEventClient();
		networkEventClient.setDefaultUri("http://localhost:8080/ws");
		networkEventClient.setMarshaller(marshaller);
		networkEventClient.setUnmarshaller(marshaller);
		return networkEventClient;
	}

}
