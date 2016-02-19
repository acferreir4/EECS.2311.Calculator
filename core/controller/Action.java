/**     
 *  =================================================== 
 *  author :  Yari Yousefian
 *  This class is the user interface   
 *  =================================================== 
 */
 public class Action<T>
{
	// private int range ;
	private T value;
	private char operator;
	
	
	public Action(T val, char op)
	{
		// range = span ;
		value = val;
		operator = op ;
	} 
	
	
	/*
	public int getRange()
	{
	  return range;
	} */
	
	public char getOperator()
	{
	  return operator;
	}
	
	public T getValue()
	{
	  return value ;
	}
	
	public void setValue(T val)
	{
	  if(operator=='0') value = val;
	}		
}


