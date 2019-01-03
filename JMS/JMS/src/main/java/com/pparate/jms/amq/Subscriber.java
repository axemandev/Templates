package com.pparate.jms.amq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Subscriber implements MessageListener {
	
	public Subscriber() throws Exception{
		Connection connection = (new ActiveMQConnectionFactory("tcp://localhost:61616"))
				.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("TYPE1.T");
		MessageConsumer subscriber = session.createConsumer(topic);
		subscriber.setMessageListener(this);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("Starting sub..");
		Subscriber subscriber = new Subscriber();
		System.out.println("Main exiting. Listening to msgs..");
	}

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage)message;
		try {
			System.out.println(textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}
}
