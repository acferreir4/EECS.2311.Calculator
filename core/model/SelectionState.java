/**     
 *  =================================================== 
 *  author :  Yari Yousefian
 *  This class is the user interface   
 *  =================================================== 
 */

public class SelectionState extends State
{
	private int upper,lower;
	private int fill;
	
	public SelectionState()
    {   	
		// super();
		// bound = new int[2];
		initialize();
    }
    
	public void put(int index)
	{
		if(fill==0) { lower = index; on = true;  }
		else if(index>=lower)upper = index;
		else { upper = lower; lower=index; }
	   
		fill++ ;
	}
   
	public void setBounds(int low, int high)
	{
		// if(fill==0)
		lower = low; 
		upper = high;
		fill = 2 ;
		on = true;
	}
   
	public String range()
    {
    	return "selected range = [ "+Integer.toString(lower)+" , "+Integer.toString(upper)+" ]" ;
    }
   
    public void makeRange(int size)
    {
    	if(on && fill==1)
    	{
    		int span = Math.abs(lower) ;
    		 
    		if (lower>0){ lower = 1;  upper = span; }
    		else {  upper = size ;  lower = size - span + 1; }    	
    	}
    }
    
    public int currentFill()
    {
    	return fill;
    }
    
    public int lowerBound()
    {
    	return lower ;
    }
    
    public int upperBound()
    { 
    	return upper ;
    } 
    
    public void initialize()
    {
    	upper = 0;
		lower =0;
		fill = 0;
		on = false;
    }
   
}
