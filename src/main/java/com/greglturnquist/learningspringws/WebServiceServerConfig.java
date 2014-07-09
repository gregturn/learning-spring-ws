package com.greglturnquist.learningspringws;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WebServiceServerConfig extends WsConfigurerAdapter {

	@Bean
	ServletRegistrationBean dispatcherServlet(ApplicationContext ctx) {

		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(ctx);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}

	@Bean
	DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema networkEventSchema) {

		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("NetworkEventPort");
		wsdl11Definition.setLocationUri("/networkevents/");
		wsdl11Definition.setTargetNamespace("http://greglturnquist.com/test");
		wsdl11Definition.setSchema(networkEventSchema);
		return wsdl11Definition;
	}

	@Bean
	XsdSchema networkEventSchema() {
		return new SimpleXsdSchema(new ClassPathResource("network-event.xsd"));
	}

	@Bean
	NetworkEventConsumer networkEventConsumer() {
		return new NetworkEventConsumer();
	}

}
