class Plane {
	private int priority;
	private String time;
	private int type;
	private int capacity;
	private int id;
	private String name;


	public Plane(int id, String name, String time, int type){
		this.id = id;
		this.name = name;
		this.time = time;
		this.type = type;
	}

	public Plane(int id, String name, String time, int type, int capacity){
		this.id = id;
		this.name = name;
		this.time = time;
		this.type = type;
		this.capacity = capacity;
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




























}