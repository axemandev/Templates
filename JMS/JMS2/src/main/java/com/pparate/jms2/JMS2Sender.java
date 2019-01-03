package com.pparate.jms2;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

public class JMS2Sender {
	
	public static void main(String[] args) {
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
		try(JMSContext context = cf.createContext();) {
			Queue queue = context.createQueue("PENTAQ");
			context.createProducer().send(queue, "Where do we go nobody knows");
			System.out.println("Message sent.");
		}
	}

}
