package com.camel.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.camel.camel.model.Employee;
import com.google.gson.Gson;

public class EmployeeProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		System.out.println("CAME HERE - EmployeeProcessor.java");

		// taking String input.
		String strEmp = exchange.getIn().getBody(String.class);

		// converting to Object
		Gson g = new Gson();
		Employee emp = g.fromJson(strEmp, Employee.class);

		// OPERATIONS.// (increasing the age by 10)
		int age = emp.getAge();
		emp.setAge(age + 10);

		// converting to String
		String outStrEmp = g.toJson(emp, Employee.class);

		// setting String output.
		exchange.getIn().setBody(outStrEmp, String.class);

	}

}
