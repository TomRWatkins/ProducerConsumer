import java.util.*;
/**
 * Webserver class creates a webserver to remove an amount of elements from a buffer.
 * @author Thomas Watkins
 *
 */
public class webserver implements Runnable {
	//Creation of instance variables
	private int elemsRemoved = 0;
    private int id;
    private int num_elements;
    public static Buffer buf;
    private semaphore s;
    private semaphore n;
    private semaphore e;
    
    /**
     * Constructor. Creates an instance of webserver that will remove a number
     * of elements from a given buffer.
     * @param id the ID of this user.
     * @param num_elements the number of elements this server will remove from the buffer.
     * @param b the buffer the user will add elements to.
     * @param s the semaphore for mutual exclusion.
     * @param n the semaphore for lower bound protection.
     * @param e the semaphore for upper bound protection.
     */
    public webserver(int id, int num_elements, Buffer b, semaphore s, semaphore n, semaphore e) {
        this.id = id;
        this.num_elements = num_elements;
        buf = b;
        this.s = s;
        this.n = n;
        this.e = e;
    }
    
    /**
     * This method removes a number of elements from the given buffer.
     * Elements iterate from 0 -> num_elements
     */
    public void remove_elements() { 
		int num = 0;        
		while (num_elements > 0) { 
			try {				
				n.down();
				//Thread.sleep(1000);
				s.down();
				buf.remove(new Integer(num));
				int bufElems = buf.getElements();				
				elemsRemoved++;				
				System.out.println("Serv " + id + " removed element " + bufElems + "/" + buf.getBufSize());
				num++;
				num_elements--;	
				s.up();
				if(bufElems <= 0  && num_elements > 0)
					System.out.println("Buffer empty – web server wait");
				e.up();					
			} catch (InterruptedException e1) {e1.printStackTrace();}
			
		}
    }
    
    /**
     * The run method is called when the Thread associated with
     * this object is started. Simply calls this servers 
     * remove_elements method.
     */
	public void run() {
		remove_elements();				
	}
	/**
	 * Returns the ID for this server
	 * @return id
	 */
	public int getId() {
		return this.id;
	}
	/**
	 * This method is called once the server has finished removing all of its elements.
	 */
	public void finished() {
		System.out.println("Consumer "+id+" consumed a total of "+elemsRemoved+" elements");
	}
}