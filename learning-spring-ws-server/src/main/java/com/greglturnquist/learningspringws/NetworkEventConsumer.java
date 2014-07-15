package com.greglturnquist.learningspringws;

import com.greglturnquist.test.SendNetworkEventRequest;
import com.greglturnquist.test.SendNetworkEventResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class NetworkEventConsumer {

	Logger log = LoggerFactory.getLogger(NetworkEventConsumer.class);

	private static final String URI = "http://greglturnquist.com/test";

	@PayloadRoot(namespace = URI, localPart = "sendNetworkEventRequest")
	@ResponsePayload
	public SendNetworkEventResponse sendNetworkEvent(@RequestPayload SendNetworkEventRequest networkEvent) {

		String message = "Received " + networkEvent.getHostname() + ":" +
				networkEvent.getSeverity() + ":" + networkEvent.getDescription();

		log.info(message);

		SendNetworkEventResponse ack = new SendNetworkEventResponse();
		ack.setStatus(message);
		return ack;
	}
}
