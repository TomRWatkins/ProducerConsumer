import java.util.*;
/**
 * User class creates a user to add an amount of elements to a buffer.
 * @author Thomas Watkins
 */
public class user implements Runnable{
	//Creation of instance variables
	private int elemsAdded = 0;
    private int id;
    private int num_elements;
    public static Buffer buf;
    private semaphore s;
    private semaphore n;
    private semaphore e;
    
  /**
   * Constructor. Creates an instance of user that will add a number
   * of elements to a given buffer.
   * @param id the ID of this user.
   * @param num_elements the number of elements this user will add to the buffer.
   * @param b the buffer the user will add elements to.
   * @param s the semaphore for mutual exclusion.
   * @param n the semaphore for lower bound protection.
   * @param e the semaphore for upper bound protection.
   */
    public user(int id, int num_elements, Buffer b, semaphore s, semaphore n, semaphore e) {
        this.id = id;
        this.num_elements = num_elements;
        buf = b;
        this.s = s;
        this.n = n;
        this.e = e;        
    }
    
    /**
     * This method adds a number of elements to a given buffer. Elements
     * iterate from o -> num_elements.
     */
    public void add_elements() {       
        int num = 0;        
        while (num_elements > 0) {        	
        	try {       		
				e.down();	
				//Thread.sleep(1000);
				s.down();				
	            buf.add(new Integer(num));
	            elemsAdded++;
	            int bufElems = buf.getElements();
	            System.out.println("User "+ id +" adds an element " + bufElems +"/"+ buf.getBufSize());
	            num++;
	            num_elements--;  
	            s.up();        
	            if(bufElems >= buf.getBufSize() && num_elements > 0)
					System.out.println("Buffer full – User now sleeping");
	            n.up();
			} catch (InterruptedException e) {e.printStackTrace();}           
        }
    }
    
    /**
     * The run method is called when the Thread associated
     * with this object is started. Simply calls this users
     * add_elements method.
     */
	public void run() {		
		add_elements();				
	}
	/**
	 * Returns the ID for this user.
	 * @return id
	 */
	public int getId() {
		return this.id;
	}
	/**
	 * This method is called once the user has finished adding all of its elements.
	 */
	public void finished() {
		System.out.println("User " + id + " created a total of " + elemsAdded + " elements");
	}
	
}