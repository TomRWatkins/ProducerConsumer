import java.util.*;
import java.util.concurrent.Semaphore;
/**
 * The Buffer Provides data and operations onto the fixed-length queue 
 * which is a LinkedList.
 * @author Thomas Watkins
 */
public class Buffer {   
	//Variables to signify number of elements currently on the 
	//queue, total capacity of the queue, the buffer list itself
	//and the synchronisation Objects.
	
    private LinkedList<Integer> buf_list;		    
    private int elements;						
    private int buf_size;						
    private semaphore s;
    private semaphore n;
    private semaphore e;
    
    /**
     * Constructor. Creates a new buffer of size buf_size.
     * @param buf_size the capacity of the buffer.
     */	
    public Buffer(int buf_size) {
    	this.buf_list = new LinkedList<Integer>();
    	this.elements = 0;
        this.buf_size = buf_size;
    }
    /**
     * Adds an integer to the buffer and increments total elements on buffer.
     * @param integer the integer to be added to the buffer.
     */
    public void add(Integer integer) {	    
			elements++;		
			buf_list.add(integer);			    
    }
    /**
     * Removes an integer from the buffer and decrements total elements on buffer.
     * @param integer the integer to be removed from the buffer.
     */
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
 }	
