
public class Trigonometric extends Expression 
{
	private final String[] TRIGONOMETRY = {"Sin(" , "Cos("};
	private Expression base;
	private boolean cosine,negative,isFirst;
	
	
	public Trigonometric (Expression b,  boolean cos, boolean sign,boolean fst)
    {
       this.base = b;
       this.cosine = cos;
       if(cos)operator = 5;
       else operator = 6;
       this.negative = sign;
       
       isFirst = fst ;
       
    }
    public String toString()  // the term without the sign
    {
      String sign = negative ? "-" : "";
      int type = cosine ? 1 : 0 ;
  	  return sign +  TRIGONOMETRY[type]+base.toString()+")" ;
      	        
    }
    
    @Override
    public Double evaluate(Double value)
    {
        int sign = negative ? -1 : 1;
    	if(cosine)return (Math.cos(base.evaluate(value)) * sign );
    	else return (Math.sin(base.evaluate(value)) * sign );
       
       
    }
    
    @Override
    public boolean isConstant()
    {
  	  
 	   return base.isConstant() ;
    }
    protected Double calculate()
	   {
		  return 0.00; 
	   }
   
    public Expression clone()
    {
       return new Trigonometric(base.clone(),cosine,negative,isFirst);

    }
}
