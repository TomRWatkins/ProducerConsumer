public class semaphore{
    
    private int permits;
    
    /** permits is the initial number of permits available.           
    */
    public semaphore(int permits) {
           this.permits = permits;
    }
 
    /**Acquires a permit if one is available and decrements the
       number of available permits by 1.           
    */
    public synchronized void down() throws InterruptedException {
    	//Acquires a permit, if permits is greater than 0 decrements
        //the number of available permits by 1.
    	permits--;
    	if(permits < 0)
    	{    		
            this.wait();
    	}
    }
 
    /** Releases a permit and increases the number of available permits by 1.          
    */
    public synchronized void up() {
           //increases the number of available permits by 1.
           permits++;
           
           //If permits are greater than 0, notify waiting threads.
           if(permits <= 0)
                  this.notify();
    }
    
    public synchronized int getPermits() throws InterruptedException {
    	//Acquires a permit, if permits is greater than 0 decrements
        //the number of available permits by 1.
    	return this.permits; 	
    }
 
}