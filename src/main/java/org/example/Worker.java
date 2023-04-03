package org.example;

import java.util.Random;

public abstract class Worker extends Thread {
	

	public Worker(){
		super();
	}

	protected void println(String msg){
		synchronized (System.out){
			System.out.println(msg);
		}
	}

}
