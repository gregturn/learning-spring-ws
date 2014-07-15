package com.greglturnquist.learningspringws;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import java.math.BigInteger;

import com.greglturnquist.wsdl.SendNetworkEventRequest;
import com.greglturnquist.wsdl.SendNetworkEventResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.xml.transform.StringResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class NetworkEventClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(NetworkEventClient.class);

	private static final String NAMESPACE_URI = "http://greglturnquist.com/test";

	public SendNetworkEventResponse sendNetworkEvent() {

		SendNetworkEventRequest request = new SendNetworkEventRequest();
		request.setHostname("retina");
		request.setDescription("New event!");
		request.setSeverity(BigInteger.valueOf(5));

		return (SendNetworkEventResponse) getWebServiceTemplate().marshalSendAndReceive(request);
	}

	public SendNetworkEventResponse sendNetworkEventDom() throws Exception {

		Document request = createRequest();
		Source requestSource = new DOMSource(request);
		DOMResult responseResult = new DOMResult();
		getWebServiceTemplate().sendSourceAndReceiveToResult(requestSource, responseResult);

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		Source responseSource = new DOMSource(responseResult.getNode());
		//transformer.transform(responseSource, new StreamResult(System.out));
		StringResult stringResult = new StringResult();
		transformer.transform(responseSource, stringResult);

		log.info(stringResult.toString());

		return (SendNetworkEventResponse) getUnmarshaller().unmarshal(responseSource);
	}

	private Document createRequest() throws ParserConfigurationException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();

		Element sendNetworkEvenRequest = document.createElementNS(NAMESPACE_URI,
				"test:sendNetworkEventRequest");
		document.appendChild(sendNetworkEvenRequest);

		Element hostname = document.createElementNS(NAMESPACE_URI, "hostname");
		sendNetworkEvenRequest.appendChild(hostname);
		hostname.setTextContent("router101");

		Element severity = document.createElementNS(NAMESPACE_URI, "severity");
		sendNetworkEvenRequest.appendChild(severity);
		severity.setTextContent("5");

		Element description = document.createElementNS(NAMESPACE_URI, "description");
		sendNetworkEvenRequest.appendChild(description);
		description.setTextContent("Ping failure");

		return document;
	}


}
