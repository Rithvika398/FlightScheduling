import java.util.*;

class Plane {
	private int priority;
	private String time;
	private int type;
	private int capacity;
	private int id;
	private String name;
	private int operationTime;

	public Plane(int id, String name, String time, int type, int operationTime){
		this.id = id;
		this.name = name;
		this.time = time;
		this.type = type;
		this.operationTime = operationTime;
	}

	public Plane(int id, String name, String time, int type, int capacity, int operationTime){
		this.id = id;
		this.name = name;
		this.time = time;
		this.type = type;
		this.capacity = capacity;
		this.operationTime = operationTime;
	}


	public int getPriority(){
		return this.priority;
	}

	public void setPriority(int priority){
		this.priority = priority;
	}

	public String getTime(){
		return this.time;
	}

	public void setTime(String time){
		 this.time = time;
	}

	public String	getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		this.id = id;
	}
	
	public void schedule(){
		monitor.
	}

}

class Monitor{
	private int value;
	
	public Monitor(){
		this.value = 1;
	}
	
	public void waitTime(){
		while(value<=0)
			;
			//System.out.print("");
		value--	;
	}
	
	public void signalTime(){
		value++;
	}
	
}


public class starting{

	private List<Plane> planelist;
	private List<Plane> takeoff;
	private List<Plane> landing;

	public Monitor monitor;
	
	public static void main(String args[]){
		new starting().main();
	}
	
	
	private void initialize(){
		planelist = new ArrayList<Plane>();
		takeoff= new ArrayList<Plane>();
		landing = new ArrayList<Plane>();
		monitor = new Monitor();
	}	
	
	
	public  void main(){
		
		initialize();
		addPlane(1, "Indigo A234" , "12:30", 1, 2000);
		addPlane(1, "Indigo A567" , "10:30", 1, 3000);
		addPlane(1, "Indigo A!!!" , "13:30", 0, 500);
		addPlane(1, "SpiceJet A908" , "22:30", 0, 200);
		addPlane(1, "SPICY A2--" , "3:30", 1, 5000);
		addPlane(1, "Damnik A2-4" , "1:30", 0, 100);
		startScheduling();
		
		
		
	}
	
	
	public startScheduling(){
		sortArray();
		
	}
	
	
	
	
	public void addPlane(int id, String name, String time, int type, int capacity, int operationTime){
		
		monitor.waitTime();
		Plane plane = new Plane(planelist.size()+1, name, time, type, capacity, operationTime);
		assignPriority(plane);
		planelist.add(plane);
	
		if(type==1)
			takeoff.add(plane);
		else if(type==0)
			landing.add(plane);
		monitor.signalTime();
	}
	
	public void assignPriority(Plane p){
		
		p.setPriority(p.getId());
	}
	
	
	
}