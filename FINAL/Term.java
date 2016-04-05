

/*****   a single term of an expression      ********************************/

   public class Term extends Expression
   {
	  
      
      private DisplayFormat precision; 
       
      private int exponent;  
      
      private Double coefficient;
      private boolean isFirst;
      // Term next;
      
      
      
      public Term(int exp, double coeff, boolean fst)
      {
         this.exponent = exp;
         this.coefficient = coeff;
         operator = 4;
         // this.next = next;
         
         isFirst = fst ;
         precision = new DisplayFormat(0.00);
      }
     /*public String toString()  // the term without the sign
      {
        String base = base();
    	return isTrigonometric() ?  TRIGONOMETRY[trigonometric]+base+")" : base ;
        	        
      } */
      public String toString()
      {
    	  // if(zero()) return "0";
    	  if(isConstant())
     	  {
    		   DisplayFormat df = new DisplayFormat(coefficient);
    		   
     	    return df.format();
     	  }
    	  
    	  precision.reset( Math.abs(coefficient) ) ;
     	  String coeff = (coefficient==1) ? "" : precision.format();  // the coeff . if 1 then coeff should not be shown

          if(exponent == 0) return sign(isFirst)+coeff ;  // constant
          else if(exponent == 1) return sign(isFirst)+coeff + "x";
          else return sign(isFirst)+coeff +"x^" + exponent;  
      }
      public boolean equals(Term mono)
      {
         return exponent == mono.exponent && coefficient == mono.coefficient;
      }
      
      public String sign(boolean first)  // the sign of the term , if the term is the first term in an equation and its positive sign then dont return any sign
      {
        
    	  String result = " + ";
         if(coefficient < 0) result = " - ";
         else if(first || isZero(coefficient)) result = "";
    	  return result;
      }
      
      
      
      public Double getCoefficient()
      {
    	  return coefficient;
      }
      public int getExponent()
      {
    	  return exponent;
      }
      
     @Override
      public Double evaluate(Double value)
      { // System.out.println("eval in term");
         
         return coefficient * Math.pow(value, exponent);
         
         
      }
      protected Double calculate()
	   {
		  return 0.0; //(coefficient * Math.pow(evalValue, exponent)); 
	   }
      public String simplify()
      {
    	  return toString();
      }
      public Expression clone()
      {
         return new Term(exponent,coefficient,isFirst);

      }
     
      @Override
      public boolean isConstant()
      {
    	  return ( (exponent==0) || (coefficient==0) )  ; 
      }

   }
   