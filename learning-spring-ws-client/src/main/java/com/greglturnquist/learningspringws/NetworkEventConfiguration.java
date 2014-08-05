package com.greglturnquist.learningspringws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SpringPlainTextPasswordValidationCallbackHandler;

@Configuration
public class NetworkEventConfiguration {

	@Bean
	Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.greglturnquist.wsdl");
		return marshaller;
	}

	@Bean
	NetworkEventClient client(Jaxb2Marshaller marshaller, XwsSecurityInterceptor securityInterceptor) {
		NetworkEventClient networkEventClient = new NetworkEventClient();
		networkEventClient.setDefaultUri("http://localhost:8080/ws");
		networkEventClient.setMarshaller(marshaller);
		networkEventClient.setUnmarshaller(marshaller);
		networkEventClient.setInterceptors(new ClientInterceptor[]{securityInterceptor});
		//networkEventClient.setInterceptors(new ClientInterceptor[]{securityInterceptor});
		return networkEventClient;
	}

	@Bean
	XwsSecurityInterceptor securityInterceptor() {
		XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
		securityInterceptor.setCallbackHandler(new SpringPlainTextPasswordValidationCallbackHandler());
		securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		return securityInterceptor;
	}

}
