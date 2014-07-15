package com.greglturnquist.learningspringws;

import com.greglturnquist.wsdl.SendNetworkEventResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class Client {

	private static final Logger log = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = SpringApplication.run(NetworkEventConfiguration.class, args);

		NetworkEventClient networkEventClient = ctx.getBean(NetworkEventClient.class);

//		SendNetworkEventResponse response = networkEventClient.sendNetworkEvent();
//		log.info(response.getStatus());

		SendNetworkEventResponse response2 = networkEventClient.sendNetworkEventDom();
		log.info(response2.getStatus());
	}

}
