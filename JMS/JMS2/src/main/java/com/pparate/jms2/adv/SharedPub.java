package com.pparate.jms2.adv;

import java.text.DecimalFormat;
import java.util.concurrent.CountDownLatch;

import javax.jms.CompletionListener;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Topic;

public class SharedPub {

	public static void main(String[] args) throws InterruptedException {
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
		JMSContext context = cf.createContext();
		Topic topic = context.createTopic("PENTA.T");
		
		JMSProducer producer = context.createProducer();
		DecimalFormat decimalFormat = new DecimalFormat("##0.00");
		CountDownLatch latch = new CountDownLatch(1);
		MyMessageCompletionListener cl = new MyMessageCompletionListener(latch);
		producer.setAsync(cl).send(topic, "ORCL current rate: "+decimalFormat.format(46+Math.random()));
		for (int i=0; i<5; i++) {
			System.out.println("working..");
			Thread.sleep(1000);
		}
		latch.await();
		context.close();
		
	}
}

class MyMessageCompletionListener implements CompletionListener {

	CountDownLatch latch;
	public MyMessageCompletionListener(CountDownLatch latch) {
		this.latch = latch;
	}
	
	@Override
	public void onCompletion(Message arg0) {
		System.out.println("Message ack complete");
		latch.countDown();
	}

	@Override
	public void onException(Message arg0, Exception arg1) {
		System.out.println("Message ack errored");
		latch.countDown();		
	}
	
}
