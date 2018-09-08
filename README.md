Aim:

Create an application to facilitate dynamic,synchronized Flight scheduling with Real-Time data entry access to ensure optimization of cost and time of flight scheduling on a single runway.


Project Overview:
We have implemented real time dynamic flight scheduling using the principles of synchronisation for a single runway with Java as the programming language. Our application allows the user to input flight details in real time and the incoming and outgoing flights are each assigned a priority(according to a set of parameters) according to which they are scheduled on the runway. This ensures that we are optimizing time and cost by dynamicaly assigning a priority as opposed to mainataining a First Come First Serve Queue.

Solution:
Our project includes two main classes which perform two main function:

1)Creating the entry for the new flights and adding them to the files
2)Scheduling the flights based on their current priority at the that moment. It schedules the flight with high priority first (0 means high and 5 means low)

 One file is used to add flight to the database basically to create an entry for the flight in the file system. It takes input as the information of the flight and then it saves it’s entry in the file system in a specific folder. Whenever, it tries to create an entry for the flight, it first checks if any read.lck files exists or not. If it exists then it waits until the files get deleted else it creates a lock file named write.lck which gets deleted when the flight entry is created successfully.
 
Second class(Main.java) is use to schedule the flights available in the file system at that moment.First it checks if any write.lck exists or not. If it exists then it waits until it gets deleted else it creates a read.lck files, since the program is reading data from the file system. Then it reads all the flights available in the file system folder. After that it create an arraylist of objects in java which contains the data of all flights. Now it assigns the priority to all the planes depending on a combination of factors. Once the priority has been assigned, the program sorts the arraylist in descending order of priority and then it schedules the flight with highest priority. This process continues until there is no more flight for scheduling. Everytime the flights gets schedule, priority of all the flights are reassigned.

Files Description
Semaphore.java
This file contains the semphore class which is used to synchronize the flight opeartion (like takeoff and landing). Since the number of runway is only one, takeoff and landing cannot happen at the same time. Therefore, synchronization is required.
Plane.java
It represent the flight object in real world. It a flight class that contains the variable like time of takeoff or landing, time to land or takeoff, name, number of passengers, etc.
Scheduler.java
This file contains the code for the scheduling the flight that is passed in the paramter of the constructor. In short, it schedules the flights.
Sorting.java
It is the Comparator interface used to sort the arraylist containing the list of all the flight in ascending order.
AddFlight.java
It is a program that is used to add(create an entry of) a flight in the file system. It take the required input and then create a plane object, serializes the object and then adds the stream(serialize object stream) to a txt file. During the process it first creates a write.lck file which gets deleted when the entry is made successfully.
Main.java
It is the main file that schedules the flights present in the directory at that moment. The working of this file is as explained above. It contains the PriorityList method which assigns a priority to all the flights currently in the queue.


Priority assignment is done on the basis of the following factors:
1)Time of flight
2) If Time of takeoff or landing is the same these factors are taken into account:
•	No of Passengers
•	Scheduled and expected time of arrival (for outgoing flights)
•	Scheduled and expected time of departure(for incoming flights)
•	Fuel Tank Capacity
