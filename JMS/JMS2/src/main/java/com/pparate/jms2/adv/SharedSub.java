package com.pparate.jms2.adv;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;

public class SharedSub implements MessageListener{

	public SharedSub() {
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
		JMSContext context = cf.createContext();
		Topic topic = context.createTopic("PENTA.T");
//		JMSConsumer consumer = context.createConsumer(topic);
		JMSConsumer consumer = context.createSharedConsumer(topic, "CONS_X");
		consumer.setMessageListener(this);
	}

	@Override
	public void onMessage(Message msg) {
		try {
			System.out.println(msg.getBody(String.class));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Thread thread = new Thread (new Runnable() {

			@Override
			public void run() {
				SharedSub sub = new SharedSub();
			}
			
		});
		thread.start();
		System.out.println("I'm done. B'bye");
	}
}
