
public class Scheduler implements Runnable{

	private Main main;
	private Plane plane;
	
	public Scheduler(Main m, Plane plane){
		this.main = m;
		this.plane = plane;
	}
	
	public void run(){
	//	main.mutex.waitTime();
		
		try{    
		
			System.out.println("[+] Scheduling the flight with:");
			System.out.println(plane);
			System.out.println("[+] It will take "+plane.getOperationTime()+"sec");
			
			//Only for debugging purpose
			if(plane.getOperationTime()<10)
				Thread.currentThread().sleep(plane.getOperationTime()*1000);
			else
				Thread.currentThread().sleep(4*1000);
			if(plane.getType()==1)
				System.out.println("    Plane has taken off\n\n\n");
			else if(plane.getType()==0)
				System.out.println("    Plane has landed\n\n\n");
			
		}catch(Exception e)
		{
			System.out.println("Thread sleep exception: "+e);
		}
		//main.mutex.signalTime(plane);
	}
	
}
