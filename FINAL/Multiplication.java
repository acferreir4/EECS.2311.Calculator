public class Multiplication extends Expression
{
	   
	   public Multiplication(Expression first, Expression second)
	   {
		   expressions[0] = first;
		   expressions[1] = second;
		   operator = 0;
	   }
	
	   
	   @Override
	   protected Double calculate()
	   {		   
		   
		   return previousValue * lastValue;
	   }
	   public Expression clone()
	   {
	         return new Multiplication( expressions[0].clone(),expressions[1].clone() );
	   }
}





