
public class OprandProcess extends State
{
	
	private int totalOprands;
	
	
	public OprandProcess()
    {   	
	  totalOprands = 0;
		
    }
    
   public void update(int num)
   {	   
	   totalOprands += num ;
   } 
   
   
   public void flip()
	 {
	     if(totalOprands>0)  	totalOprands = 0;  
	    
	 }
	 
	 public boolean on()
	 {
	       return (totalOprands>0);	
	 }	
   
	 public int getTotalOprands()
	 {
	       return totalOprands;	
	 }
	 
   
}