public class State
{
	protected boolean on;
	protected void flip()
	{
		if(on)  	on = false;  
		else on = true;
	}
		 
	protected boolean on()
	{
		return on;	
	}	
}