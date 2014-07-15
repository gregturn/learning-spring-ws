package com.greglturnquist.learningspringws;

import java.math.BigInteger;

import com.greglturnquist.wsdl.SendNetworkEventRequest;
import com.greglturnquist.wsdl.SendNetworkEventResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class NetworkEventClient extends WebServiceGatewaySupport {

	public SendNetworkEventResponse sendNetworkEvent() {

		SendNetworkEventRequest request = new SendNetworkEventRequest();
		request.setHostname("retina");
		request.setDescription("New event!");
		request.setSeverity(BigInteger.valueOf(5));

		return (SendNetworkEventResponse) getWebServiceTemplate().marshalSendAndReceive(
				request,
				new SoapActionCallback("http://localhost:8080/ws"));
	}

}
