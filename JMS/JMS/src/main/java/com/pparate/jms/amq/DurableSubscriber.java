package com.pparate.jms.amq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

public class DurableSubscriber implements MessageListener{

	public DurableSubscriber() throws JMSException {
		TopicConnection connection = new ActiveMQConnectionFactory("tcp://localhost:61616?jms.clientID=penClient:3")
				.createTopicConnection();
		connection.start();
		TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("TYPE2.T");
		TopicSubscriber subscriber = session.createDurableSubscriber(topic, "sub:101");
		subscriber.setMessageListener(this);
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
	
	public static void main(String[] args) throws JMSException {
		DurableSubscriber sub = new DurableSubscriber();
		System.out.println("Waiting on msgs...");
	}

}
