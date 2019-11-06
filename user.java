import java.util.*;
/**
 * user class symbolises a user to add an amount of elements
 * to the buffer.
 * @author Thomas Watkins
 *
 */
public class user implements Runnable{
	private int elemsAdded = 0;
    private int id;
    int num_elements;
    public static Buffer buf;
    semaphore s;
    semaphore n;
    semaphore e;
    //User arguments: User ID, number of elements to add, and buffer
    public user(int i, int el, Buffer b, semaphore s, semaphore n, semaphore e) {
        this.id = i;
        this.num_elements = el;
        buf = b;
        this.s = s;
        this.n = n;
        this.e = e;        
    }

    public void add_elements() {
        //Add element to buffer, element value iterates from 0, 1, 2 .... num_elements
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
    
	public void run() {		
		add_elements();				
	}
	public int getId() {
		return this.id;
	}
	public void finished() {
		System.out.println("User " + id + " created a total of " + elemsAdded + " elements");
	}
	
}