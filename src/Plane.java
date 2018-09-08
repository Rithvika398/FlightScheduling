import java.io.*;


public class Plane implements Serializable {

	private static final long serialVersionUID = 6529685098267757690L;

	private int priority; //Integer value of priority
	private String time; //Time to takeoff or land
	private int type; //Type means takeoff type(1) or landing type(0)
	private int capacity; //Petrol tank capacity
	private int id; //Unique id for each plane
	private String name; //Name of flight
	private int operationTime; //Time to takeoff or land
	private static int idCount=1;
	private int delay;
	//private int cost;//cost of unit time delay
	private int noOfPassengers;
	

	
	public Plane( String name, String time, int type, int operationTime,int noOfPassengers,int delay){
		this.id = idCount++;
		this.name = name;
		this.time = time;
		this.type = type;
		this.operationTime = operationTime;
		this.noOfPassengers=noOfPassengers;
		this.delay=delay;

	}
	
	public Plane( int id,  String name, String time, int type, int capacity, int operationTime,int noOfPassengers,int delay){
		this.id = id;
		this.name = name;
		this.time = time;
		this.type = type;
		this.capacity = capacity;
		this.operationTime = operationTime;
	        this.noOfPassengers=noOfPassengers;
	        this.delay=delay;

	}

	public Plane( String name, String time, int type, int capacity, int operationTime,int noOfPassengers,int delay){
		this.id = idCount++;
		this.name = name;
		this.time = time;
		this.type = type;
		this.capacity = capacity;
		this.operationTime = operationTime;
	        this.noOfPassengers=noOfPassengers;
		this.delay=delay;
		
	}
	
	public int getDelay()
	{
	 return delay;
	}
	
	public void setDelay(int delay)
	{
	  this.delay=delay;
	
	}
	
	public int getNoOfPassengers()
	{
	 return noOfPassengers;
	}
	
	public void setNoOfPassengers(int noOfPassengers)
	{
	  this.noOfPassengers=noOfPassengers;
	
	}


	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(int operationTime) {
		this.operationTime = operationTime;
	
	}

	@Override
	public String toString() {
		return "Plane [priority=" + priority + ",\n time=" + time + ",\n type=" + type + ",\n capacity=" + capacity + ",\n id="
				+ id + ",\n name=" + name + ",\n "+(type==0?"LandingTime":"TakeoffTime")+"=" + operationTime + "]";
	}

	
	
}
