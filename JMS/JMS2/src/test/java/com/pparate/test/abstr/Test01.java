package com.pparate.test.abstr;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Test01 {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		while(true) {
			new Thread(new LocalThreads()).start();
			//Thread.sleep(1000);
		}
		
	}

}

class LocalThreads implements Runnable {
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName());
		}
	}
}
