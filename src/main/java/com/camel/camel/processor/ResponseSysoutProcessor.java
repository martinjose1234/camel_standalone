package com.camel.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ResponseSysoutProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		
		System.out.println("CAME HERE - ResponseSysoutProcessor.java");
		System.out.println("Response : " + exchange.getIn().getBody(String.class));
	}
}
