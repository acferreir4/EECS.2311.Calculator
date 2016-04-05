
public class FactorialExpression extends Expression 
{
	
	private Expression base;
	private boolean isFirst;
	
	
	public FactorialExpression (Expression b, boolean fst)
    {
       this.base = b;
       
       operator = 5;
       
       
       isFirst = fst ;
       
    }
    public String toString()  // the term without the sign
    {
      
  	  return "!" +base.toString() ;
      	        
    }
    
    @Override
    public Double evaluate(Double value)
    {
        Double zero = 0.00;
       long result =  factorial( Math.round(base.evaluate(value) ) ) ;
       return result+zero;
       
    }
    public static long factorial(long n) 
	{
		// expressionMode.off();
		
	    return  ((n <= 1) ? 1 : n*factorial(n-1));
	}
    
    protected Double calculate()
	   {
		  return 0.00; 
	   }
   
    public Expression clone()
    {
       return new FactorialExpression(base.clone(),isFirst);

    }
}
