package pcd.lab01.step05;

public class MyWorkerA extends Worker {
	String name;
	public MyWorkerA(String name){
		super(name);
		this.name = name;
	}
	
	public void run(){
		while (true){
		  action1();	
		  action2();	
		}
	}
	
	protected void action1(){
		println(name+" a1");
		sleepFor(1000);
	}
	
	protected void action2(){
		println(name+" a2");
		sleepFor(1000);
	}
}

