/**     
 *  author :  Yari Yousefian
 */
 public class Action<T>
{
	private T value;
	private char operator;
	
	public Action(T val, char op)
	{
		// range = span ;
		value = val;
		operator = op ;
	} 
	
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


