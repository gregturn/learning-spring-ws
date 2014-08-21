package com.greglturnquist.learningspringws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SpringUsernamePasswordCallbackHandler;

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
		networkEventClient.setInterceptors(new ClientInterceptor[]{securityInterceptor()});
		return networkEventClient;
	}

	@Bean
	XwsSecurityInterceptor securityInterceptor() {
		XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
		securityInterceptor.setSecureRequest(true);
		securityInterceptor.setValidateRequest(true);
		securityInterceptor.setCallbackHandler(callbackHandler());
		securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		return securityInterceptor;
	}

	@Bean
	SpringUsernamePasswordCallbackHandler callbackHandler() {
		SecurityContextHolder.setContext(new SecurityContextImpl());
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("user", "password"));
		return new SpringUsernamePasswordCallbackHandler();
	}

}
