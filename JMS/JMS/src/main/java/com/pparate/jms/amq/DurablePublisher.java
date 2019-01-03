package com.pparate.jms.amq;

import java.util.Random;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;


public class DurablePublisher {

	public static void main(String[] args) throws JMSException {
		Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616")
				.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("TYPE2.T");
		MessageProducer publisher = session.createProducer(topic);
		String msg = "RandVal - "+(new Random()).nextInt(99);
		TextMessage message = session.createTextMessage(msg);
		publisher.send(message);
		System.out.println("Sent: "+msg);
		session.close();
		connection.close();
	}
}
