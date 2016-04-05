public class Subtraction extends Expression
{
	   
	   public Subtraction(Expression first, Expression second)
	   {
		   expressions[0] = first;
		   expressions[1] = second;
		   operator = 3 ;
	   }
	
	   
	   @Override
	   protected Double calculate()
	   {
		   return previousValue - lastValue;
	   }
	   public Expression clone()
	   {
	         return new Subtraction( expressions[0].clone(),expressions[1].clone() );
	   }

}


