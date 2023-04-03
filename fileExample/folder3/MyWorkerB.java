package pcd.lab01.step05;

public class MyWorkerB extends Worker {
	String name;
	public MyWorkerB(String name){
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
		 println(name+" b1");
		 sleepFor(1000);
	}
	
	protected void action2(){
		println(name+" b2");
		sleepFor(1000);
	}
}
