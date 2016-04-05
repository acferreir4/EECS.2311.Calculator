
public class Addition extends Expression
{
	   
	   public Addition(Expression first, Expression second)
	   {
		   expressions[0] = first;
		   expressions[1] = second;
		   operator = 1;
	   }
	
	   /*public Double evaluate(Double value)	   
	   {   
		   evalValue = value;
		   System.out.println("eval in exp");
		   
		  //  else System.out.println("expr0 not null");
		   Double previousValue = expressions[0].evaluate(value);
		   System.out.println("pprev after leftcall = "+previousValue);
		   if(expressions[0].redFlag()) { flag = true;expressions[1].flagReset();return previousValue ;}
		   Double lastValue = expressions[1].evaluate(value);
		   System.out.println("lvalue after Rightcall = "+lastValue);
		   if(expressions[1].redFlag()) { flag = true;expressions[0].flagReset();return lastValue ;}
		   
		   return calculate();
	   } */
	   
	   protected Double calculate()
	   {		  
		  System.out.println("pvUnder="+previousValue);
		  System.out.println("lvUnder="+lastValue);
		   return previousValue + lastValue;
	   }
	   
	   public Expression clone()
	   {
	         return new Addition( expressions[0].clone(),expressions[1].clone() );
	   }

	   
}
