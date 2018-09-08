
public class Semaphore {
	
	private int value;
	
	
	public int getValue(){
		return this.value;
	}
	
	public Semaphore(int i){
		this.value = i;
	}
	
	public void waitTime(){
		//System.out.println("---  waitTime() calling!!!");
		while(value<=0)
			System.out.print("");
			
		//System.out.println("waitTime() called!!!");
		value--	;
	}
	
	public void signalTime(){
		value++;	
		//System.out.println("Value signal: "+value+"\n");
	}
	
	public void signalTime(Plane p){
		value++;	//System.out.println("SIgnal() called!!");
		//System.out.println("Value signal("+p.getId()+"): "+value+"\n");
	}
	
}
