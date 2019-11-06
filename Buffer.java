import java.util.*;
import java.util.concurrent.Semaphore;
/**
 * Provides data and operations onto the fixed-length queue 
 * which is a LinkedList.
 * @author Thomas Watkins
 */
public class Buffer {   
	//Variables to signify number of elements currently on the 
	//queue, total capacity of the queue, the buffer list itself
	//and the synchronisation Objects.
	
    LinkedList<Integer> buf_list;		    
    int elements;						
    int buf_size;						
    semaphore s;
    semaphore n;
    semaphore e;
    
	//Queue creation, with n indicating the maximum capacity
    public Buffer(int buf_size) {
    	buf_list = new LinkedList<Integer>();
    	elements = 0;
        this.buf_size = buf_size;
    }
    
    //This method acts as the critical section and is protected
    //by a synchronised lock and a semaphore. The method adds the 
    //integer passed to the buffer list and increments the total
    //elements on the queue. (CRITICAL SECTION)
    public void add(Integer integer) {	    
			elements++;		
			buf_list.add(integer);			    
    }
    public void remove(Integer integer) {  
		
			elements--;	
			buf_list.remove(integer); 
		
            	    	
    }
    
    /**
     * Returns total elements on the queue.
     * @return elements
     */
    public int getElements() {
    	return elements;
    }
    
    /**
     * Returns the total capacity of the queue. 
     * @return buf_size
     */
    public int getBufSize() {
    	return buf_size;
    }

    //Calculates the total sum of element value within the buffer
    public void finalSummation() {	
    	int sum = 0;
    	for(int i: buf_list) {
    	sum+=i;
    }
     
    System.out.println("--------------------------");
    System.out.println("Count total: " + sum); 
    System.out.println("--------------------------");
    }
 }	
