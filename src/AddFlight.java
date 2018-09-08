import java.util.*;
import java.io.*;
import java.nio.file.*;


public class AddFlight {

	public List<Plane> planelist;
	public List<Plane> takeoff;
	public List<Plane> landing;

	public Semaphore mutex;
	public Semaphore writing;
	public Semaphore reading;

	//private Main main ;

	public static void main(String args[]){
		//main = new Main();
		new AddFlight().main();
	}
	
	public int getIdFile(){
		File file = new File("File/id.txt");
		int value  = 0;
		try{
			BufferedReader out = null;
			out = new BufferedReader(new FileReader("File/id.txt"));
			// Read File Contents - score
			BufferedReader br = new BufferedReader(new FileReader("File/id.txt"));
			String storedScore="0";
			int storedScoreNumber = 0;
			while ((storedScore = br.readLine()) != null) {
				value=(Integer.parseInt(storedScore==null?"0":storedScore));
			}

		}catch(Exception e){
		}
		return value;
	}
	
	public void writeIdFile(int i){
		File file = new File("File/id.txt");
		try{
			BufferedWriter out = null;
			out = new BufferedWriter(new FileWriter("File/id.txt", false));
			out.write(String.valueOf(i));	
			out.flush();
			out.close();
			
			
		}catch(Exception e){
			
		}
	}
	
	private void initialize(){
		planelist = new ArrayList<Plane>();
		takeoff= new ArrayList<Plane>();
		landing = new ArrayList<Plane>();
		mutex = new Semaphore(1);
		reading = new Semaphore(1);
		writing = new Semaphore(1);	
		//writeIdFile(0);
	}	
	
	
	public void main(){

		//initialize();
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Using this program you can add flights.");
		
		
		int temp =1;
		while(temp==1 && !checkFileExist("read")){
			System.out.println("Enter flight name: ");
			String name = scanner.next();
			System.out.println("Enter flight time:  ");
			String time = scanner.next();
			System.out.println("Enter flight type(1/0):   (1->takeoff & 0->landing) ");
			int type = scanner.nextInt();
			System.out.println("Enter flight fuel capacity:  ");
			int capacity = scanner.nextInt();
			System.out.println("Enter flight operation time:  ");
			int operation = scanner.nextInt();
			System.out.println("Enter total number of passengers:  ");
			int noOfPassengers=scanner.nextInt();
			int delay=0;
			if(type==1)
			{System.out.println("Enter expected delay between scheduled and expected time of arrival at this airport(time in Minutes---0 if the flight arrives at appointed time)");
			    delay=scanner.nextInt();
			
			}
			else if(type==0)
			{System.out.println("Enter maximum allowed delay between scheduled and expected time of departure from this airport(time in Minutes---0 if the flight cannot be delayed further)");
			   delay=scanner.nextInt();
			
			}
			
			System.out.println("Trying to register the plane...");
			addPlane(getIdFile()+1,name, time, (int)type, capacity, (int)operation,(int)noOfPassengers,(int)delay);
			System.out.println("Registration done successfully.\n\n");
			System.out.print("\nDo you want to add another flight(1/0)? ");
			temp = scanner.nextInt();
			
			System.out.println("\n\n");
			
		}
		
		
		
		
	}
	
	public boolean checkFileExist(String name){
		File lockFile = new File("File/"+name+".lck");
		boolean lckExist = lockFile.exists();
		return lckExist;
	}
	
	
	
	public void createPlaneFile(Plane plane, String name){
		
		
		BufferedWriter out = null;
		try {

			FileOutputStream fos = new FileOutputStream("File/"+name+".txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(plane);
			oos.close();
			fos.close();
			
			// Read File Contents - score
			BufferedReader br = new BufferedReader(new FileReader("File/id.txt"));
			String storedScore="0";
			int storedScoreNumber = 0;
			while ((storedScore = br.readLine()) != null) {
				storedScoreNumber=(Integer.parseInt(storedScore==null?"0":storedScore));
			}

			// Write File Contents - incremented socre
			out = new BufferedWriter(new FileWriter("File/id.txt", false));
			out.write(String.valueOf(storedScoreNumber+1));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	
	
	public void addPlane(int id, String name, String time, int type, int capacity, int operationTime,int noOfPassengers,int delay){
		
		//Waiting if no one is writing
		while(checkFileExist("write"))
			;
		
		//Craeting a lock
		BufferedWriter out = null;
		try {

			
			// Write File Contents - incremented socre
			out = new BufferedWriter(new FileWriter("./File/write.lck", true));
			out.write("");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		Plane plane = new Plane( getIdFile()+1, name, time, type, capacity, operationTime,noOfPassengers,delay);
		//assignPriority(plane);
		createPlaneFile(plane, plane.getId()+"");
		
		
		try {
			Files.deleteIfExists(Paths.get("./File/write.lck"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		
		
	}
	
	
	public void assignPriority(Plane p){
	
	       
		p.setPriority(p.getId());
	}
	
		
}




/*
Output:

F:\PlaneScheduling\src> java AddFlight
Using this program you can add flights.
Enter flight name:
Hurricane
Enter flight time:
19:56
Enter flight type(1/0):   (1->takeoff & 0->landing)
0
Enter flight fuel capacity:
1790
Enter flight operation time:
9
Trying to register the plane...
Registration done successfully.



Do you add another flight(1/0)? 0



*/
