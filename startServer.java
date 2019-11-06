import java.util.Scanner;
public class startServer {
	//Creation of buffer object
	Buffer b;   
	private int bufCapacity;
	private int numUsers;
	private int numServers;
	private int totalElems;
	private semaphore s = new semaphore(1);
	private semaphore n = new semaphore(0);
	private semaphore e;
    //Creates execution scenario between user and webservers on buffer
    public startServer(int bufCapacity,	int numUsers, int numServers, int totalElems) { 
    	this.bufCapacity = bufCapacity;
    	this.numUsers = numUsers;
    	this.numServers = numServers;
    	this.totalElems = totalElems;
    	e = new semaphore(bufCapacity);
    	
    	long startTime = System.currentTimeMillis();

        //Instantiate all objects (webserver, users, buffer)
    	b = new Buffer(bufCapacity);
    	
    	user users[] = new user[numUsers];   
    	webserver webServers[] = new webserver[numServers];
    	
    	Thread userThread[] = new Thread[numUsers];
    	Thread webServersThread[] = new Thread[numServers];
    	
    	//Equally subdivide user inputted elements across all user objects
    	int divisionUsers[] = new int[numUsers];
    	for(int i = 0; i < numUsers; i++) {
    		divisionUsers[i] = 0;
		}
    	int divisionServers[] = new int[numServers];
    	for(int i = 0; i < numServers; i++) {
    		divisionServers[i] = 0;
		}
    	int elemsUser = totalElems;
    	int elemsServer = totalElems;
    	
    	while(elemsUser > 0) {
    		for(int i = 0; i < numUsers; i++) {
    			if(elemsUser > 0) {
    				divisionUsers[i]++;
    				elemsUser--;
    			}
    			else {
    				break;
    			}
    		}
    	}
    	while(elemsServer > 0) {
    		for(int i = 0; i < numServers; i++) {
    			if(elemsServer > 0) {
    				divisionServers[i]++;
    				elemsServer--;
    			}
    			else {
    				break;
    			}
    		}
    	}   	
    	
    	//Instantiate User and Thread Arrays
    	for(int i = 0; i < users.length; i++) {			
			users[i] = new user(i,divisionUsers[i],b,s,n,e);
			user x = users[i];
			userThread[i] = new Thread(x);
		}
    	
    	//Instantiate Server and Thread Arrays
    	for(int i = 0; i < webServers.length; i++) {			
			webServers[i] = new webserver(i,divisionServers[i],b,s,n,e);
			webserver x = webServers[i];
			webServersThread[i] = new Thread(x);
		}
    	
    	for(Thread t: userThread) {			
			t.start();			
		}
		
		for(Thread t: webServersThread) {
			t.start();		
		}	
    	
		for(Thread t: userThread) {
			try {
				t.join();
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}			
		}		
		for(Thread t: webServersThread) {
			try {
				t.join();
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}			
		}
				
        System.out.println("-----------------------");
        
        //Outputs the total number of elements added/removed from user and webserver
        for(user u: users) {
        	u.finished();
        }
        for(webserver ws: webServers) {
        	ws.finished();
        }
        System.out.println("-----------------------");
        //Check to see buffer if all elements produced from users have been successfully removed by webservers
        System.out.println("Buffer has " + b.getElements() + " elements remaining");			
        System.out.println("-----------------------");
        //Checks if all users and web servers successfully finished
        for(int i = 0; i < numServers; i++) {
        	System.out.println("Server thread " + webServers[i].getId() + " is alive: "+ webServersThread[i].isAlive());
        }
        for(int i = 0; i < numUsers; i++) {
        	System.out.println("User thread " + users[i].getId() + " is alive: "+ userThread[i].isAlive());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("-----------------------");
        System.out.println("Program took " + (endTime - startTime) + " milliseconds to complete");

    }

	public static void main(String[] args) {
    	Scanner s = new Scanner(System.in);
    	
    	int bufCapacity;
    	int numUsers;
    	int numServers;
    	int totalElems;
    	
        System.out.print("Enter buffer capacity: "); //Insert user inputted values for program execution
        bufCapacity = s.nextInt();
        System.out.print("Enter number of users: ");
        numUsers = s.nextInt();
        System.out.print("Enter number of servers: ");
        numServers = s.nextInt();
        System.out.print("Enter total number of elements: ");
        totalElems = s.nextInt();
        
        startServer start = new startServer(bufCapacity,numUsers,numServers,totalElems);
    }
}