public class OprandProcess extends State
{
	
	private int totalOprands;
	
	/**
	 * Initializes the OperandProcess class 
	 */
	public OprandProcess()
    {
		totalOprands = 0;
    }
    
	/**
	 * Updates the number of operands
	 * @param num
	 */
	public void update(int num)
	{
		totalOprands += num ;
	} 
   
	/**
	 * Sets total operands to 0
	 * if total operands > 0
	 */
	public void flip()
	{
		if(totalOprands>0)  	totalOprands = 0;
	}
	
	/**
	 * Determines if operators
	 * have been entered
	 */
	public boolean on()
	{
		return (totalOprands>0);	
	}	
   
	/**
	 * Get the total amount of operators entered
	 * @return The amount
	 */
	public int getTotalOprands()
	{
		return totalOprands;	
	}
}