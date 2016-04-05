/**     
 *  =================================================== 
 *  author :  Yari Yousefian
 *  This class is the Controller   
 *  =================================================== 
 */

import java.util.StringTokenizer;

public class CalculatorViewController
{
	
	protected CalculatorView view;
	
	private DisplayFormat format;
	
	
	protected Model model;
	
	protected Controller monitor;
	   
	   
	public CalculatorViewController(Controller ctrl,Model mdl)
	{
		  monitor = ctrl ;
		  model = mdl;
		  format = new DisplayFormat(1.00);
	}
	
	
	public void setView(CalculatorView v)
	{
		view = v;
	}
	public void inform(Message msg)
	{  
		
		
		String reply;
		
		if(msg.getSource().equalsIgnoreCase("clear"))  
		{ 
		     model.purge() ; 
			 view.clear();
			 
			 
		}  
		
		
	    
		else if(msg.getSource().equalsIgnoreCase("undo")) // undo
		     {
			   		   
		           if(view.append()) 
		        	{ 
		        		
		        		view.backspace(); 
		        		view.setFieldState(true);
		        	}
		           else
		           {
		        	     model.undo();
		        	     String[] snd = model.display();
					     view.display(snd[0],true,snd[1],true); 
		           }
		     
		
		        
			 }
			  
		else if(msg.getSource().equalsIgnoreCase("+")) 
		    {
			    if(model.size()==0)
			    	{
			    	   
			    	   view.display("",false,"Nothing to add !",true);
			    	}
			    else
			    {
			    	 System.out.println("+ called");
			      String cp = msg.getContent() ;			      
			      if(!msg.empty() && isNumber(cp)) model.insert(parse(cp,false));  // + without enter
			      model.add();
			      String[] snd = model.display();
				  view.display(snd[0],true,snd[1],true); 
			    }
			    
			    
		     }
		
		else if(msg.getSource().equalsIgnoreCase("-")) 
		 {
			if(model.size()==0)
	    	{
				view.display("",false,"Nothing to subtract !",true);
	    	   
	    	}
			    else
			    {
			      String cp = msg.getContent() ;			      
			      if(!msg.empty() && isNumber(cp)) model.insert(parse(cp,false));
			      model.subtract();
			      String[] snd = model.display();
				     view.display(snd[0],true,snd[1],true); 
			    }
			    
		 }  
		
		else if(msg.getSource().equalsIgnoreCase("*")) // x
		     {
			   if(model.size()==0)
	    	  {
				   view.display("",false,"Nothing to multiply !",true);
	    	  }
			    else
			    {
			      String cp = msg.getContent() ;			      
			      if(!msg.empty() && isNumber(cp)) model.insert(parse(cp,false));
			      model.multiply();
			      String[] snd = model.display();
				     view.display(snd[0],true,snd[1],true); 
			    }
			    
		     }  
		else if(msg.getSource().equalsIgnoreCase("/"))  //  div
		     {
			  if(model.size()==0)
	    	  {
				  view.display("",false,"Nothing to divide !",true);
	    	  }
			    else
			    {
			      String cp = msg.getContent() ;			      
			      if(!msg.empty() && isNumber(cp)) model.insert(parse(cp,false));
			      boolean flag = model.divide();
			      if(flag) view.display("",false,"Division by zero !!!",true);
			      else
			    	  {
			    	     String[] snd = model.display();
					     view.display(snd[0],true,snd[1],true); 
			    	  }
			    }
			    
		     }
		else if(msg.getSource().equalsIgnoreCase("sin"))  
	     {
			view.sin();
			
		     
	     }
		else if(msg.getSource().equalsIgnoreCase("cos"))  //  cos
	     {
			 view.cos();
	     
		    
	     }
		else if(msg.getSource().equalsIgnoreCase("enter"))// enter
		{
			  System.out.println("here1");
		    model.insert(  parse(msg.getContent(),true)  );
		    String[] snd = model.display();
		     view.display(snd[0],true,snd[1],true); 
			/*if ( msg.getContent().equalsIgnoreCase("empty") )view.display("No value entered !");
			else if(  !valid( msg.getContent() ) )view.display("Incomplete entry !");
			else if(prefix>0) // sin or cos button pressed
			{
				 String inp = msg.getContent() ;
				 // Expression exp = parse(msg.getContent());
				 if(!inp.contains("x") && !(inp.contains("Cos")) && !(inp.contains("Sin")) )  // constant term
				 {
					
					Double val =  (prefix == 1)? Math.sin(exp.evaluate(1.00)) : Math.cos(exp.evaluate(1.00)) ;
					DisplayFormat df = new DisplayFormat(val);
					view.display(df.format());
				 }
				 
				 
				prefix=0;
			}	*/		
			
			  
		}
		
		else if(msg.getSource().equalsIgnoreCase("Pi")) 
	     {
			DisplayFormat df = new DisplayFormat(model.PI);
			view.display("",false,df.format(),true);
			
				 		     
	     }
		else if(msg.getSource().equalsIgnoreCase("!"))  //  fact
	     {
			  String inp = msg.getContent() ;
			 // Expression exp = parse(msg.getContent());
			 if(!inp.contains("x") && !(inp.contains("Cos")) && !(inp.contains("Sin")) )  // constant term
			 {
				 Double value = Double.parseDouble(msg.getContent()) ;
				  format.reset(value);
				  if(format.hasFraction())view.display("",false,"Integer value required !",true); 
				  else if(value<0)view.display("",false,"non-negative value required",true);
				  else 
					 {
					    int input = Integer.parseInt(msg.getContent()); 
					    Integer i = input;
					    Long output = FactorialExpression.factorial(i.longValue());
					    view.display("",false,output.toString() ,true);
					    
					    
					 }
				
			 }
			 else view.factorial();
			 
			     
	     }
			
		else if(msg.getSource().equalsIgnoreCase("sign"))  //  +/-
	     {
			if(view.append())view.negate();
			else { model.negate(); String[] snd = model.display();
		     view.display(snd[0],true,snd[1],true);  }
			
			 
	     }
		else if(msg.getSource().equalsIgnoreCase("eval"))
			{
			  String val = model.evaluate(Double.parseDouble(msg.getContent()) );
			  view.display("",false,val ,true);
			
			}
		else if(msg.getSource().equalsIgnoreCase("swap"))
		{ System.out.println("n ctrl");
		  monitor.swap(2);
		}
		else if(msg.getSource().equalsIgnoreCase("erase"))
		{
		  view.clear();
		}
		
	}
	
	private int occurance(String word, String character )
	{
		return  word.length() - word.replace(character, "").length();
		
	}
	
	
	/* private boolean parsse(String str)
	{
		boolean result = true;
		if( occurance(str,".") > 0) return false ;
		if( str.startsWith(",") || str.endsWith(",") || str.startsWith("-") || str.endsWith("-")) return false;
		if( str.indexOf(",")<0  && occurance(str, "-" )>1  )  return false;  // single selection yet too many -
		
		
		int coma=str.indexOf(','),to=str.indexOf('-') ;
		int last = Math.min(coma,to);
		for(int i = last+1; i < str.length(); i++)  // searching for consecutive , or -
		   {
			    if( str.charAt(i)=='-' || str.charAt(i)==',' )
			    {
			    	if( (i-last) == 1 )
			    	{
			    		result = false; 
			    		break;
			    	}
			    	else last = i;
			    }
			}
		if(result && str.indexOf(",")>0)  // searching for more than one - within two coma
		{
			StringTokenizer tokens = new StringTokenizer(str,",");
			while(tokens.hasMoreTokens())
			{
				if( occurance(tokens.nextToken(),"-")>1)
				{
					result = false;
					break;
				}
			}
		}
		
		return result;
	} */
	private Expression parse(String input, boolean first)
    {
	     
	     boolean fact = false;
	     if(input.startsWith("!")){input = input.substring(1,input.length());fact = true;}
	     Expression base = parseBase(input, first);
	     if(fact) return new FactorialExpression(base,first);
	     // else if(base.type()==5)return new FactorialExpression(base,first);
	     else return base;
	     
	 }
	 private Expression parseBase(String input, boolean first)
	 {
	   int trig = trigonometric(input);
	   if(trig>=0)
		 {
			 System.out.println("in trig");
			Expression base = parse( input.substring(input.indexOf("(")+1,input.indexOf(")") ),first ) ;
			return (new Trigonometric(base,  ( (trig==0) ? true : false )   ,input.startsWith("-") ,first) );
		 }
		else
		{
		 System.out.println("outside trig");
		 
		 
		 if(!input.contains("x"))return new Term(0,Double.parseDouble(input)  , first); // constant
	     else // has x variable
	     {
	    	String split = input.contains("^") ? "x^" : "x" ;
	    	StringTokenizer tokens = new StringTokenizer(input,split);
	    	
	    	// get coefficient
	    	if(!tokens.hasMoreTokens())return  new Term(1, 1, first);
	    	String number = tokens.nextToken();
	    	Double sign = input.startsWith("-") ? -1.00 : 1.00 ;
	    	Double coeff = (input.indexOf("x")>0) ? Double.parseDouble(number) : sign ; // eg : x^4 form
	    	
	    	// get exponent
	    	int expr = input.contains("^") ? ( (input.indexOf("x")>0) ? Integer.parseInt(tokens.nextToken()) : Integer.parseInt(number)  ) : 1 ;
	    	
	    	
	    	return new Term(expr, coeff, first);
	     }
	   }
	}
	 
	/* private void parse(String inp)
    {
	    if(Controller.occurance(inp,",")==0) // 1 range
	    {
	    	if(Controller.occurance(inp,"-")==0)
	    	{
	    		int index = Integer.parseInt(inp);
	    		selection.add(index,index);
	    	}
	    	else 
	    	{
	    		int[] boundary = range(inp);
	    		selection.add(boundary[0],boundary[1]);
	    	}
	    }
	    else // several ranges
	    {
	    	StringTokenizer tokens = new StringTokenizer(inp,",");
	    	while(tokens.hasMoreTokens())
	    	{
	    		String indices = tokens.nextToken();
	    		if(Controller.occurance(indices,"-")==0)
		    	{
		    		int index = Integer.parseInt(inp);
		    		selection.add(index,index);
		    	}
		    	else 
		    	{
		    		int[] boundary = range(indices);
		    		selection.add(boundary[0],boundary[1]);
		    	}
	    	}
	    }
	 } */

	
	private boolean valid(String str)
	{				   
	  return ( ! (    str.charAt(str.length()-1) =='^'   )  )	 ;				    	
	}
	
	
	private int trigonometric(String input)
	{
		if( input.contains("Cos") ) return 0;
		else if( input.contains("Sin") ) return 1 ;
		else return -1;
	}
	
	private boolean isNumber(String str)
	{
		boolean result = true;
		// Character valid = {'c' };
		for(int i = 0; i < str.length(); i++)  
		   {
				 Character ch = str.charAt(i);	    
			     if( !( Character.isDigit(ch) || ch=='.' ) )
					    {
			    		  result = false; 
			    		  break;
					    }
			    
			}

		return result;
	} 
	  
}
