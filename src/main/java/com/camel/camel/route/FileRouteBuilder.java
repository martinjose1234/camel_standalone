package com.camel.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;

import com.camel.camel.model.Employee;
import com.camel.camel.processor.EmployeeProcessor;
import com.camel.camel.processor.ResponseSysoutProcessor;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class FileRouteBuilder extends RouteBuilder {

	@JsonIgnore
	JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);

	@Override
	public void configure() throws Exception {

		// TO RUN ::::::::::::::::::::::::
		// 1. For this Eg, we need to create 2 APIs. and make it up and running in separate springboot application. These are dummy thired party apis.
		//			a) http://localhost:8080/employees/getrequest  (GET)
		//          b) http://localhost:8080/employees/postrequest (POST)
		// 2. Run Camel application 'camel_standalone' by (rightclick) com.example.camelstandalone.main.MainApp.java -> Run As -> Spring Boot App/Java Application
		// 3. follow bellow Actions : and check the response. 
		
		// ROUTE 1. Simple GET Call.
		// Action : (if u copypast any file in E:/JAVA WORKS $$$/inputFolderREST folder, below rest call will happen and output of the rest call will print in RestGetCallProcessor.java)
		from("file:E:/JAVA WORKS $$$/inputFolderREST?noop=true")
			.setHeader(Exchange.HTTP_METHOD, simple("GET"))
			.to("http://localhost:8080/employees/getrequest")
			.process(new ResponseSysoutProcessor());

		// ROUTE 2. Simple POST Call by File reading.
		// Action : (if u copypast a file 1.txt contain json request body into E:/JAVA WORKS $$$/inputFolderPOST folder, below post call will invoke with body as the file content json request)
		from("file:E:/JAVA WORKS $$$/inputFolderPOST?fileName=1.txt")
			.log("came here : ${body}") // logging the body.
			.setHeader(Exchange.HTTP_METHOD, simple("POST"))	
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
			.to("http://localhost:8080/employees/postrequest") 		// NOTE : Here the content of 1.txt (${body}) will automatically set for Body Request for this POST API call...
			.process(new ResponseSysoutProcessor());
		
		// ROUTE 3. Use of Process Class.
		// Action : (if u copypast a file 2.txt contain json request in inputFolderPOST, below post call will invoke with body as the file content json request after all operations done in EmployeeProcessor class)
		from("file:E:/JAVA WORKS $$$/inputFolderPOST?fileName=2.txt")
			.log("came here : ${body}") // logging the body.
			.setHeader(Exchange.HTTP_METHOD, simple("POST"))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
			.process(new EmployeeProcessor()) // here in this process we are doing some process to the input body.
			.to("http://localhost:8080/employees/postrequest") 		// NOTE : Here the above processed body will automatically set for Body Request for this POST API call...
			.process(new ResponseSysoutProcessor());
		
		
	}

}
