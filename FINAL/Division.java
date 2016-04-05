public class Division extends Expression
{
	   
	   public Division(Expression first, Expression second)
	   {
		   expressions[0] = first;
		   expressions[1] = second;
		   operator = 2;
	   }
	
	   
	   @Override
	   public Double evaluate(Double value)
	   {
		   Double divisor = expressions[1].evaluate(value);
		   if(expressions[1].redFlag()){ expressions[1].flagReset(); flag=true; return divisor;}
		   if(isZero(divisor)|| expressions[1].redFlag()) { flag = true;return divisor ;}
		   
		   return expressions[0].evaluate(value) / divisor ;
		    
	   }
	   public Expression clone()
	   {
	         return new Division( expressions[0].clone(),expressions[1].clone() );
	   }
	   
	   protected Double calculate()
	   {
		  return 0.00; 
	   }
}
