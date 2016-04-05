
/**************************************************************************
 *                           Polynomial Algebra
 *
 * This application manipulates univariate polynomials over reals.
 * Created with atleast one term in the polynomial
 **************************************************************************/

import java.util.*;
import java.text.*;

public abstract class Expression
{
	protected final int SIN = 0;
	protected final int COS = 1;
    
    protected final Double TOLERANCE = 0.00000001;
    String[] LABEL = {"*","+","/","-"};
    
   // protected int trigonometric; // sin/cos ? 
    protected Expression[] expressions = new Expression[2];
    protected Double previousValue,lastValue ;
    // protected Expression last;
    protected int operator;
    protected boolean flag = false;
    protected int id=0;
   // protected Double evalValue;
    // private Term head;
   
   
 
   /*public Expression(int exp, double coeff)
   {
	   if( !(Math.abs(coeff) < TOLERANCE) ) head = new Term(exp, coeff, null);
   }*/

 /**
   *  Adds a new term into the polynomial, and make sure the order of the polinomial terms remains from largest to smallest .
   */
  /* public void addTerm(int exp, double coeff)
   {
    if( !(Math.abs(coeff) < TOLERANCE) )  // ignore if coeff=0 or approach 0
    {

      if( exp > head.exponent )  // if its bigger than head, then put before head
      {
         head = new Term(exp, coeff, head);
         
      }
      else // if its <= head
      {
        Term cur = head;
        Term prev = null;

        while( cur != null && exp < cur.exponent)
        {
         prev = cur;
         cur = cur.next;
        }
        if( cur == null || exp != cur.exponent )prev.next = new Term(exp, coeff, prev.next);  // new.next = curr and then prev.next = new
         else  // curr.exp = new.exp, so simplify  
         {
            cur.coefficient += coeff;
            if( Math.abs(cur.coefficient) < TOLERANCE ) // if simplification result == 0, then remove the zero node
             {  // cur.exp = 0;
               if(prev != null) // atleast two terms
               {prev.next = cur.next; cur.next = null;}
               else  // only one node in polynomial and adding that node gives zero coeff
               	{
            	   head = head.next; cur.next = null;
            	}
             }
         }
      }
    }
  } */

   
  //  public abstract String simplify();
   protected abstract Double calculate();
   public abstract Expression clone() ;
   
   public Double evaluate(Double value)   
   {   
	   // evalValue = value;
	   System.out.println("eval in exp");
	   
	  //  else System.out.println("expr0 not null");
	   previousValue = expressions[0].evaluate(value);
	   System.out.println("pprev after leftcall = "+previousValue);
	   if(expressions[0].redFlag()) { flag = true;expressions[1].flagReset();return previousValue ;}
	   lastValue = expressions[1].evaluate(value);
	   System.out.println("lvalue after Rightcall = "+lastValue);
	   if(expressions[1].redFlag()) { flag = true;expressions[0].flagReset();return lastValue ;}
	   
	   return calculate();
   } 
   
   public String toString() 
   {
	    String rightExpression = expressions[1].toString();
	    String leftExpression = expressions[0].toString();
		int rightExpressionOperator = expressions[1].getOperator() ;
		int leftExpressionOperator = expressions[0].getOperator() ;
		
		String localExpression,result;
		
        if(  (weight(leftExpressionOperator)< weight(operator))   ) leftExpression = "("+ leftExpression + ")" ; 
		
        if  (  (weight(rightExpressionOperator)<weight(operator)) ||  (     ((rightExpressionOperator==2)&&(operator==2)) || ((rightExpressionOperator==3)&&(operator==3))   ) )rightExpression = "("+ rightExpression + ")" ;
        
		
		return leftExpression + LABEL[operator] + rightExpression ;
		
		
   }
   /*{
      /* StringBuffer sb = new StringBuffer();
      
      
      for(Monomial tmp = head.next; tmp != null; tmp = tmp.next)
        
    	  if(tmp.coeff < 0 )
           sb.append(" - " + tmp.toString());
        else
           sb.append(" + " + tmp.toString());

      return sb.toString();   */
	   
	   
	 //  String result = head.sign(true)+head.toString();
	//   for(Term tmp = head.next; tmp != null; tmp = tmp.next) result += tmp.sign(false)+tmp.toString();
	   
	 //  return result;
  // } */

 /**
   *  mathematically adds two polynomials , not add a new term
   *  The method does not change the original polynomial.
   */
  
  /* public boolean equals(Expression poly)
   {
      Term tmp1 = head;
      Term tmp2 = poly.head;

      while(tmp1 != null && tmp2 != null)
      {
         if( !tmp1.equals(tmp2) ) return false;
         tmp1 = tmp1.next;
         tmp2 = tmp2.next;
      }
      return true;
   } */


 /**
   *  Computes the polynomial at x = value
   */
   
   
   
   /*{
      double res = 0;

      for(Term tmp = head; tmp != null; tmp = tmp.next)
      {
         res += tmp.coefficient * Math.pow(value, tmp.exponent);
      }

      return res;
   }*/

  
   public boolean redFlag()
   {
 	  return flag;
   }
   
   /*public boolean isTrigonometric()
   {
 	  return ( (trigonometric==SIN) || (trigonometric==COS) );
   } 
   
   
   
   public void setTrigonometry(int trigono)
   {
 	  trigonometric =  trigono;
   }
   public int getTrigonometry()
   {
 	  return trigonometric ;
   }  */
   
   public int getOperator()
   {
 	  return operator ;
   }
   public int getID()
   {
 	  return id ;
   }
   public int setID(int identity)
   {
 	  return id = identity;
   }
   public Expression removeFirst()
   {
 	   Expression temp = expressions[0];
 	   expressions[0] = null;
	   return temp ;
   }
   public Expression removeSecond()
   {
 	   Expression temp = expressions[1];
 	   expressions[1] = null;
	   return temp ;
   }
   
   public Expression[] getExpressions()
   {
 	  return expressions ;
   }
   public void setExpressions(Expression prev, Expression last)
   {
 	  expressions[0] = prev;
 	  expressions[1] = last;
   }
   
   protected int weight(int operator)
	{
		return   ((operator+1) % 2) ;
	}
   protected int type()
	{
		return operator ;
	}
   public boolean isZero(Double value)
   {
 	  return (Math.abs(value) < TOLERANCE) ; 
   }
   
   public boolean isConstant()
   {
 	   boolean first = expressions[0].isConstant();
 	   boolean second = true;
	   if( expressions[1]!=null) second = expressions[1].isConstant() ; 
	   return (first && second) ;
   }
   public void flagReset()
   {
 	  flag = false ; 
   }

}

