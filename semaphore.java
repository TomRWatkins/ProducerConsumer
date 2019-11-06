import java.util.*;
/**
 * Semaphore class enables concurrency mechanisms to be used.
 * @author Thomas Watkins
 *
 */
public class semaphore {    
    private int permits;
    
    /**
     * Constructor. Creates a new instance of semaphore.
     * @param permits the initial number of permits available.
     */
    public semaphore(int permits) {
           this.permits = permits;
    }
    /**
     * Aquires a permit if one is available and decrements the number
     * of available permits by 1.
     * @throws InterruptedException try/catch
     */
    public synchronized void down() throws InterruptedException {    
    	permits--;
    	if(permits < 0)    		
            this.wait();    	
    }
    /**
     * increases the number of available permits by 1 and releases a permit.
     */
    public synchronized void up() {
           permits++;
           if(permits <= 0)
                  this.notify();
    } 
}