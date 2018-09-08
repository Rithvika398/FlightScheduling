import java.util.ArrayList;
import java.util.Collections;
import java.util.*;
import java.io.*;
import java.nio.file.*;


public class Main {

	private Thread schedulerThread=null;
	
	public List<Plane> planelist;
	public List<Plane> takeoff;
	public List<Plane> landing;

	public Semaphore mutex;
	public Semaphore writing;
	public Semaphore reading;
	
	//private Main main ;
	public static void main(String args[]){
		//main = new Main();
		System.out.println("********** This program schedules all the flight available **************\n");
		
		new Main().main();
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
		
	}	
	
	
	public void main(){
		initialize();
		
		System.out.println("\n1.Schedule flight\n2.View flight available\n3.Exit\nEnter your choice: ");
		Scanner scan = new Scanner(System.in);
		int temp = 1;
		try{
			temp = scan.nextInt();
		}
		catch(Exception e){
			System.out.println("Please retry");
			main();
		}
		
		switch(temp){
			case 1:
				startSchedulingPlane();
				//System.out.println("\nNO more flight in the queue.\n");
				try
				{
				if(schedulerThread!=null)
					schedulerThread.join();
				}
				
				catch(Exception e)
				{
				
				}
					
				main();
				break;
			case 2:
				ViewFlight();
				main();
				break;
			case 3:
				break;
			default:
				System.out.println("Invalid option");
				main();
		}
		
		
	}
	
	
	public void ViewFlight(){
	
		System.out.println("+--------------------------------------------------------------------------------------------------------------------------------------------------------------+");	
		System.out.format("| %10s | %20s | %15s | %20s | %20s | %20s | %15s | %15s |\n","Flight ID","Flight Name","Flight Time","No.Of Passengers","Tank Capacity","Permitted delay","Flight Type", "OperationTime");
		System.out.println("+--------------------------------------------------------------------------------------------------------------------------------------------------------------+");	
		                    
		
		for(int itr=1 ; itr<=getIdFile();itr++){
			
			Plane temp = null ;
			try
			{
				FileInputStream fis = new FileInputStream("./File/"+itr+".txt");
				ObjectInputStream ois = new ObjectInputStream(fis);
				temp = (Plane)ois.readObject();
				//System.out.println(temp.getId()+"\t\t"+temp.getName()+"\t"+temp.getTime()+"\t\t"+temp.getNoOfPassengers()+"\t\t"+temp.getCapacity()+"\t\t"+temp.getDelay()+"\t\t"+temp.getType()+"\t\t");
				System.out.format("| %10d | %20s | %15s | %20d | %20d | %20d | %15d | %15d |\n", temp.getId(), temp.getName(), temp.getTime(), temp.getNoOfPassengers(), temp.getCapacity(),temp.getDelay(), temp.getType(), temp.getOperationTime());
			}
			catch (Exception e)
			{
				e.printStackTrace(); 
			}
			
		}
		System.out.println("+--------------------------------------------------------------------------------------------------------------------------------------------------------------+");	
		
	}
	
	
	public boolean checkFileExist(){
		File lockFile = new File("File/write.lck");
		boolean lckExist = lockFile.exists();
		return lckExist;
	}
	
	public void startSchedulingPlane(){
		
		while(checkFileExist())
			;
		int termination = getIdFile();
		for(int i=0;i<termination;i++)
		{
			startScheduling();
		}
		
	}
	
	
	public void startScheduling(){
		
		planelist = new ArrayList<Plane>();
		takeoff= new ArrayList<Plane>();
		landing = new ArrayList<Plane>();
		
		
		mutex.waitTime();
		BufferedWriter out = null;
			
		try {
			out = new BufferedWriter(new FileWriter("File/read.lck", false));
			out.write("");
			out.close();
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
		
		
		for(int itr=1 ; itr<=getIdFile();itr++){
			
			Plane temp = null ;
			try
			{
				FileInputStream fis = new FileInputStream("./File/"+itr+".txt");
				ObjectInputStream ois = new ObjectInputStream(fis);
				temp = (Plane)ois.readObject();
			}
			catch (Exception e)
			{
				e.printStackTrace(); 
			}
			
			addPlaneObject(temp);	
			
		}
		//System.out.println("\n\n\n"+Arrays.toString(planelist.toArray()));
		try{
			if(schedulerThread!=null)
				schedulerThread.join();
		
		}catch(Exception e){
			System.out.println(" -- Joining() error "+e);
		}
		
		
		PriorityList(planelist);
		
		//System.out.println("\n\n\n\nPRiority()\n\n\n\n"+Arrays.toString(planelist.toArray())+"\n\n\n\n");
		
		
		Collections.sort(planelist, new Sorting());
		System.out.println("+----------------------------------------------------------+");
		//System.out.println("Order of Scheduling:\nFlight ID\tFlight Name\tPriority");
		System.out.format("| %19s%18s%19s|\n","  ", "Order of Scheduling", "  ");
		System.out.println("+----------------------------------------------------------+");
		System.out.format("| %10s | %30s | %10s |\n","Flight ID", "Flight Name", "Priority" );
		System.out.println("+----------------------------------------------------------+");
		
		for(int i=0;i<planelist.size();i++)
		{
		   System.out.format("| %10d | %30s | %10d |\n",planelist.get(i).getId(), planelist.get(i).getName(), planelist.get(i).getPriority() );
		}
		System.out.println("+----------------------------------------------------------+");
		
		
		
		
		int itr=0;
		Plane temp = null;
		for(itr=0;itr<planelist.size();itr++){
			deletePlane((itr+1)+"");
			if(itr==planelist.size()-1){
				//System.out.println("Lp : "+itr);
				createPlaneFile(planelist.get(itr), "lp");
				temp = planelist.get(itr);
			}	
			else
				createPlaneFile(planelist.get(itr), (itr+1)+"");
			
		}

		writeIdFile(itr-1);
		
	
		//Plane temp = getPlaneByName("lp");
		schedulerThread = new Thread(new Scheduler(this, temp ));
		schedulerThread.start();
		deletePlane("lp");

		
		try {
			Files.deleteIfExists(Paths.get("./File/read.lck"));
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
		
		
		mutex.signalTime();
		
	}
	
	public void deletePlane(String id){
		BufferedWriter out = null;
		try {
			
			try
			{
				Files.deleteIfExists(Paths.get("File/"+id+".txt"));
			}
			catch(Exception e)
			{
				
			}
				
			/*File file = new File("File/"+id+".txt");
			if(file.delete()){
				System.out.println("File "+id+".txt deleted");
			}else{
				System.out.println("File "+id+".txt cannot be deleted");
			}*/
		
		
			/*
			// Read File Contents - score
			BufferedReader br = new BufferedReader(new FileReader("./File/id.txt"));
			String storedScore="0";
			int storedScoreNumber = 0;
			while ((storedScore = br.readLine()) != null) {
				storedScoreNumber=(Integer.parseInt(storedScore==null?"0":storedScore));
			}

			// Write File Contents - incremented socre
			out = new BufferedWriter(new FileWriter("File/id.txt", false));
			out.write(String.valueOf(storedScoreNumber-1));
	
			System.out.println("Written in deletePlane() to id.txt : "+(storedScoreNumber-1));
			*/
		} catch (Exception e) {
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
	
	public void createPlaneFile(Plane plane, String name){
		
		//System.out.println("File: "+name+".txt");
		//System.out.println(plane);
		//System.out.println(getIdFile());
		
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
			//System.out.println("Written in creatPlaneFile() to id.txt : "+(storedScoreNumber+1));

			
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
	
	public void createPlaneFile(Plane plane){
		createPlaneFile(plane, (getIdFile()+1)+"");
	}
	
	
	public void addPlane(int id, String name, String time, int type, int capacity, int operationTime,int noOfPassengers,int delay){
		Plane plane = new Plane( name, time, type, capacity, operationTime, noOfPassengers, delay);
		
		mutex.waitTime();
		addPlaneObject(plane);
		//createPlaneFile(plane);
		mutex.signalTime();
		
	}
	
	public void addPlaneObject(Plane plane){
		int type = plane.getType();
		planelist.add(plane);
		if(type==1)
			takeoff.add(plane);
		else if(type==0)
			landing.add(plane);
		
	}
	
	public void PriorityList(List<Plane> planelist)
	{
	     
	
	int arr[]=new int[getIdFile()];
	int cost[]=new int[getIdFile()];
	int id[]=new int[getIdFile()];
	int time[]=new int[getIdFile()];
	
	for(int i=0;i<getIdFile();i++)
	{
	    time[i]=60*(Integer.parseInt(planelist.get(i).getTime().substring(0,2)))+Integer.parseInt(planelist.get(i).getTime().substring(3,5));
	    cost[i]=10*(planelist.get(i).getNoOfPassengers())-planelist.get(i).getCapacity();
	    arr[i]=planelist.get(i).getDelay()*cost[i];
	    id[i]=planelist.get(i).getId();
	
	}
	int max;
	for(int j=0;j<getIdFile()-1;j++)
             { max=-1;
                  for(int k=j+1;k<getIdFile();k++)
                  {
                      if(time[j]>time[k])
                      {
                         max=k;
                      }
                      else if(time[j]==time[k])
                      {
                           max=arr[j]>arr[k]?j:k;
                      
                      }
                      
                  	
                  }
                  
                  if(max!=-1)
                      {
                      int tmp=time[j];
                      time[j]=time[max];
                      time[max]=tmp;
                      
                      tmp=id[j];
                      id[j]=id[max];
                      id[max]=tmp;
                      
                      Plane temp=planelist.get(j);
                      planelist.set(j, planelist.get(max));
                      planelist.set(max, temp);
                      }
                  
                  
             
             }

            /* for(int i=0;i<getIdFile();i++)
             {
             
             	for(int itr=0;itr<planelist.size();itr++)
             		if(planelist.get(itr).getId() == id[i]){
             			planelist.get(itr).setPriority(i);
             			System.out.println("**** "+planelist.get(itr));
             		}
             			
             
             }*/
             
             for(int i=0;i<planelist.size();i++)
             {
                planelist.get(i).setPriority(i+1);
             }
	
	
	}
	
	
	public void assignPriority(Plane p)
	{
	    //PriorityList(planelist);
	
		System.out.println("AssignPriority should not be called!!!");
	
	
		//p.setPriority(p.getId());
	}
	
		
}








/*
New Output

rithvika@rithvika-VirtualBox:~/sem4/os/flightScheduling/PlaneScheduling/src$ java Main
********** This program schedules all the flight available **************


1.Schedule flight
2.View flight available
3.Exit
Enter your choice: 
2
-------------------------------------------------------------------------------------------------------------------------------------------------------
Flight ID|	Flight Name|	Flight Time|	No.Of Passengers|	Tank Capacity|	Permitted delay	Flight Type|
-------------------------------------------------------------------------------------------------------------------------------------------------------
1		Indigo		10:30		200		0		7		1		
2		JetAir		10:20		185		0		8		0		
3		JetLit		12:30		220		0		7		1		
-------------------------------------------------------------------------------------------------------------------------------------------------------

1.Schedule flight
2.View flight available
3.Exit
Enter your choice: 
1
Order of Scheduling:
Flight ID	Flight Name	Priority
2		JetAir		1
1		Indigo		2
3		JetLit		3
Order of Scheduling:
Flight ID	Flight Name	Priority
2		JetAir		1
1		Indigo		2
Order of Scheduling:
Flight ID	Flight Name	Priority
2		JetAir		1
--------------------------------------------------------------------------------


[+] Scheduling the flight with:
 Id=3 
 Name=JetLit
Plane [priority=3,
 time=12:30,
 type=1,
 capacity=0,
 id=3,
 name=JetLit,
 operationTime=3]
    It will take 3sec
Plane has taken off
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------


[+] Scheduling the flight with:
 Id=1 
 Name=Indigo
Plane [priority=2,
 time=10:30,
 type=1,
 capacity=0,
 id=1,
 name=Indigo,
 operationTime=6]
    It will take 6sec
Plane has taken off
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------


[+] Scheduling the flight with:
 Id=2 
 Name=JetAir
Plane [priority=1,
 time=10:20,
 type=0,
 capacity=0,
 id=2,
 name=JetAir,
 operationTime=4]
    It will take 4sec
Plane has landed
--------------------------------------------------------------------------------

1.Schedule flight
2.View flight available
3.Exit
Enter your choice: 
3







Old Output:

F:\PlaneScheduling\src>java Main
********** This program schedules all the flight available **************


1.Schedule flight
2.View flight available
3.Exit
Enter your choice: 2
1) Plane [priority=6,
 time=1:30,
 type=0,
 capacity=100,
 id=6,
 name=Damnik A2-4,
 operationTime=4]

2) Plane [priority=5,
 time=3:30,
 type=1,
 capacity=5000,
 id=5,
 name=SPICY A2--,
 operationTime=6]

3) Plane [priority=4,
 time=22:30,
 type=0,
 capacity=200,
 id=4,
 name=SpiceJet A908,
 operationTime=8]

4) Plane [priority=3,
 time=13:30,
 type=0,
 capacity=500,
 id=3,
 name=Indigo A!!!,
 operationTime=2]

5) Plane [priority=2,
 time=10:30,
 type=1,
 capacity=3000,
 id=2,
 name=Indigo A567,
 operationTime=5]

1.Schedule flight
2.View flight available
3.Exit
Enter your choice: 1


[+] Scheduling the flight with Id=2 & Name=Indigo A567
Plane [priority=2,
 time=10:30,
 type=1,
 capacity=3000,
 id=2,
 name=Indigo A567,
 operationTime=5]
    It will take 5sec


[+] Scheduling the flight with Id=3 & Name=Indigo A!!!
Plane [priority=3,
 time=13:30,
 type=0,
 capacity=500,
 id=3,
 name=Indigo A!!!,
 operationTime=2]
    It will take 2sec


[+] Scheduling the flight with Id=4 & Name=SpiceJet A908
Plane [priority=4,
 time=22:30,
 type=0,
 capacity=200,
 id=4,
 name=SpiceJet A908,
 operationTime=8]
    It will take 8sec

1.Schedule flight
2.View flight available
3.Exit
Enter your choice:

[+] Scheduling the flight with Id=5 & Name=SPICY A2--
Plane [priority=5,
 time=3:30,
 type=1,
 capacity=5000,
 id=5,
 name=SPICY A2--,
 operationTime=6]


[+] Scheduling the flight with Id=6 & Name=Damnik A2-4
    It will take 6sec
Plane [priority=6,
 time=1:30,
 type=0,
 capacity=100,
 id=6,
 name=Damnik A2-4,
 operationTime=4]
    It will take 4sec
*/
