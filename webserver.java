import java.util.*;
/**
 * user class symbolises a user to add an amount of elements
 * to the buffer.
 * @author Thomas Watkins
 *
 */
public class webserver implements Runnable {
	int elemsRemoved = 0;
    int id;
    int num_elements;
    public static Buffer buf;
    semaphore s;
    semaphore n;
    semaphore e;
    //User arguments: User ID, number of elements to add, and buffer
    public webserver(int i, int el, Buffer b, semaphore s, semaphore n, semaphore e) {
        this.id = i;
        this.num_elements = el;
        buf = b;
        this.s = s;
        this.n = n;
        this.e = e;
    }

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
    
	public void run() {
		remove_elements();				
	}
	public int getId() {
		return this.id;
	}
	
	public void finished() {
		System.out.println("Consumer "+id+" consumed a total of "+elemsRemoved+" elements");
	}
}