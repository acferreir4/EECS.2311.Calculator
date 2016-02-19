/**     
 *  =================================================== 
 *  author :  Yari Yousefian
 *  This class is the user interface   
 *  =================================================== 
 */

 import java.util.Stack;

public class Model  
{
	private final double PI = 3.14159 ;
	private Stack<Action<Double>> stack;
	private int  totalComputations;
	private DisplayFormat format;
	private SelectionState selection;
	private OprandProcess oprand;
	
	// private boolean processing;
	private  String[] errorMsg = { "Error : index out of bound !",     // 0
			                            "Invalid selection !",  //1
			                            "Error: operator range too small !", //2
			                            "Error: too many oprands !", //3
			                            "Error : missing oprand !",//4		                            
			                            "Error: missing or too many oprands !",   //5
			                            "Error : integer entry required !", //6
			                            "Selection out of bound !",  //7
			                            "Already selected !",  //8
			                            "Invalid selection !",  //9
			                            "Error: nothing to select !",  //10
			                            "Error: not yet selected !",  //11
			                            "Nothing to delete !"   //12
			                            };
	/**
	 * Creates a model with no user values and a calculated
	 * value of zero.
	 * 
	 */
	public Model()
	{
		stack = new  Stack<Action<Double>>();
		format = new DisplayFormat(0.00);
		selection = new SelectionState() ;
		oprand = new OprandProcess() ;
		
		totalComputations = 0;
	}

	public void insert(Double entry)
	{  
		System.out.println("inserting "+entry);
		stack.push( new Action<Double>(entry,'0') );
		oprand.update(1);
	}
	
	public String delete()
	{
		// String result = "removed";
		    
		if(weight()>0) 
		{
			Stack<Action<Double>> selected;  
				
			if (!selection.on()) // not in selection state, so delete the top item
			{
				if(oprand.on()) oprand.update(-1);
				else totalComputations-- ;
				selected =  pull(span(),span());
					    
			}
			else // selection state
			{
				selection.makeRange(span());
		    	int deletionSpan = 0 - (selection.upperBound()-selection.lowerBound()+1) ;
		    	    
			    selected = pull(selection.lowerBound(),selection.upperBound());
			    if(oprand.on()) oprand.update(deletionSpan);
			    else totalComputations += deletionSpan ;
			}
			  
			while( !selected.empty() ) selected.pop() ; // garbage collection	
	           
		}
		else return "nothing to undo !"; 
		
		selection.initialize();
		return expression();
	}
	
	
	/**
	 * Clears the user values and the calculated value.
	 */
	public void purge()
	{
		while(!stack.empty())stack.pop() ;
		
		totalComputations = 0;
		selection.initialize();
		if(oprand.on())oprand.flip();
	}
	
	/**
	 * Adds the calculated value by a user value.
	 * 
	 * @param userValue
	 *            The value to add to the current calculated value.
	 */
	public Double add()
	{		
		Stack<Double> selected, tmp  ;
		
		if(selection.on()){ selection.makeRange(span()); selected = pick(selection.lowerBound(),selection.upperBound()); }
		else if( oprand.on() )  // current computation
		{
			int collections = oprand.getTotalOprands();
			selected = new Stack<Double>();
			if(collections==1)  // add with the previous result
			{
				Action<Double> top = stack.pop();
				selected.push(top.getValue());
			    if(!stack.empty())selected.push(stack.peek().getValue());
			    stack.push(top);	
			}
			else selected = pick(1,collections); // oprands > 1, add em' up  //  CHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
			    
			    	
			    
		}
		else//  last 2 results  && not selected
		{
			selected = pick(span()-1,span());
		}
		 
		 
		 // values captured , now do calculations
		 
		tmp = new Stack<Double>();
		Double sum = 0.00;
		while( !selected.empty() ){ sum +=  selected.peek() ;  tmp.push(selected.pop()) ; } 
		 
		Action<Double> result = new Action<Double>(sum,'+') ; 
		Action<Double> newOprand = new Action<Double>(sum,'0') ; 
		// System.out.println("Oprand = "+oprand.getTotalOprands());
		 
		if( oprand.on() &&   oprand.getTotalOprands()>1 )
        {
			if(selection.on())stack.push(newOprand);  // adding a new oprand to current computation
	        else stack.push(result);  // current computation done
        }
		else    // insert new expression before inserting the result 
		{
			while( !tmp.empty() )selected.push(tmp.pop()) ;  
			while( !selected.empty() )
			{
				newOprand = new Action<Double>(selected.pop(),'0') ;
				stack.push(newOprand) ; 
			}
			stack.push(result);
		}
		 /*
		 if(selection.on())stack.push(new Action<Double>(sum,'0'));  // new oprand for current computation
		 else if(oprand.on() && oprand.getTotalOprands()>1) {System.out.println("result = "+result.getValue()); stack.push(result); }// result for current computation       
	     else // add last 2 actions, insert new expression  before inserting the result 
			{     
				 while(!tmp.empty())selected.push(tmp.pop()); 
				 while(!selected.empty())stack.push( new Action<Double>(selected.pop(),'0') );
				 stack.push(result);
			}  */
		 
		  
		if(oprand.on())oprand.flip();
		selection.initialize();
		totalComputations++;
		System.out.println("adding");
		
		return sum;
	}
	
	/**
	 * Subtracts the calculated value by a user value.
	 * 
	 * @param userValue
	 *            The value to subtract from the current calculated value by.
	 */
	public Double subtract()
	{
		Stack<Double> selected, tmp  ;
		
		 
		if(selection.on()){ selection.makeRange(span()); selected = pick(selection.lowerBound(),selection.upperBound()); }
		else if( oprand.on() )  // current computation
		{
			int collections = oprand.getTotalOprands();
			selected = new Stack<Double>();
			if(collections==1)  // add with the previous result
			{
				Action<Double> top = stack.pop();
			    selected.push(top.getValue());
			    if(!stack.empty())selected.push(stack.peek().getValue());
			    stack.push(top);
			}
			else selected = pick(1,collections); // oprands > 1, add em' up  //  CHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
			    
			    	
			    
		}
		else//  last 2 results  && not selected
		{
			selected = pick(span()-1,span());
		}
		
		 // values captured , now do calculations
		 
		tmp = new Stack<Double>();
		tmp.push(selected.pop()) ;
		Double sub = tmp.peek();
		
		while( !selected.empty() ){ sub -=  selected.peek() ;  tmp.push(selected.pop()) ; }
		 
		 
		Action<Double> result = new Action<Double>(sub,'-') ; 
		Action<Double> newOprand = new Action<Double>(sub,'0') ; 
		// System.out.println("Oprand = "+oprand.getTotalOprands());
		 
		if( oprand.on() &&   oprand.getTotalOprands()>1 )
        {
			if(selection.on())stack.push(newOprand);  // adding a new oprand to current computation
	        else stack.push(result);  // current computation done
        }
		else    // insert new expression  before inserting the result 
		{
			while( !tmp.empty() )selected.push(tmp.pop()) ;  
			while( !selected.empty() )
			{ 
				newOprand = new Action<Double>(selected.pop(),'0') ;
				stack.push(newOprand) ; 
			}
			stack.push(result);
		}
		
		if(oprand.on())oprand.flip();
		selection.initialize();
		totalComputations++;
		System.out.println("adding");
		
		return sub;
		
		/* Stack<Double> selected  ;
		int currentSize = weight();
		
		
		 selection.makeRange(currentSize);
		 if(selection.on()) selected = pick(selection.lowerBound(),selection.upperBound());
		 else if( oprand.on() )  // current computation
			 {  
			    int collections = oprand.getTotalOprands();
			    selected = new Stack<Double>();
			    if(collections==1)  // add with the previous result
			    {
			    	Action<Double> top = stack.pop();
			    	selected.push(top.getValue());
			    	if(!stack.empty())selected.push(stack.peek().getValue());
			    	stack.push(top);
			    	
			    }
			    else // oprands > 1, add em' up
			    {
			    	selection.setBounds(1,collections);
			    	selected = pick(selection.lowerBound(),selection.upperBound());
			    }
			    	
			    
			 }
		 else// add last 2 results
			 {
			    selected = new Stack<Double>();
			    Action<Double> top = stack.pop();
		    	selected.push(top.getValue());
		    	if(!stack.empty())selected.push(stack.peek().getValue());
		    	stack.push(top);
			 }
		 
		 
		 
		
		 Double sub = selected.pop();
		 while( !selected.empty() ) sub -= selected.pop() ;
		 
		 if(oprand.on())stack.push(new Action<Double>(sub,'0'));  // entry for current computation
		 else stack.push( new Action<Double>(sub,'-') ); 
		 
		 oprand.flip();
			selection.initialize();
			totalComputations++;
		 // format.reset(sub);
		 // result = format.format();  
		System.out.println("subtracting");
		return sub;  */

	}
	
	/**
	 * Multiplies the calculated value by a user value.
	 * 
	 * @param userValue
	 *            The value to multiply the current calculated value by.
	 */
	public Double multiply()
	{
		Stack<Double> selected, tmp  ;
		
		if(selection.on()){ selection.makeRange(span()); selected = pick(selection.lowerBound(),selection.upperBound()); }
		else if( oprand.on() )  // current computation
		{
			int collections = oprand.getTotalOprands();
			selected = new Stack<Double>();
			if(collections==1)  // add with the previous result
			{
				Action<Double> top = stack.pop();
			    selected.push(top.getValue());
			    if(!stack.empty())selected.push(stack.peek().getValue());
			    stack.push(top);	
			}
			else selected = pick(1,collections); // oprands > 1, add em' up  //  CHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
	    
		}
		else//  last 2 results  && not selected
		{
			selected = pick(span()-1,span());
		}
		 
		 
		 // values captured , now do calculations
		 
		tmp = new Stack<Double>();
		Double mult = 1.00;
		while( !selected.empty() ){ mult *=  selected.peek() ;  tmp.push(selected.pop()) ; } 
		 
		Action<Double> result = new Action<Double>(mult,'x') ; 
		Action<Double> newOprand = new Action<Double>(mult,'0') ; 
		 // System.out.println("Oprand = "+oprand.getTotalOprands());
		 
		if( oprand.on() &&   oprand.getTotalOprands()>1 )
        {
	         if(selection.on())stack.push(newOprand);  // adding a new oprand to current computation
	         else stack.push(result);  // current computation done
        }
		else    // insert new expression  before inserting the result 
		{
			while( !tmp.empty() )selected.push(tmp.pop()) ;  
			while( !selected.empty() )
			{ 
				newOprand = new Action<Double>(selected.pop(),'0') ;
				stack.push(newOprand) ; 
			}
			stack.push(result);
		}
		 
		if(oprand.on())oprand.flip();
		selection.initialize();
		totalComputations++;
		System.out.println("adding");
		
		return mult;
		
		/*Stack<Double> selected  ;
		int currentSize = weight();
		
		
		 selection.makeRange(currentSize);
		 if(selection.on()) selected = pick(selection.lowerBound(),selection.upperBound());
		 else if( oprand.on() )  // current computation
			 {  
			    int collections = oprand.getTotalOprands();
			    selected = new Stack<Double>();
			    if(collections==1)  // add with the previous result
			    {
			    	Action<Double> top = stack.pop();
			    	selected.push(top.getValue());
			    	if(!stack.empty())selected.push(stack.peek().getValue());
			    	stack.push(top);
			    	
			    }
			    else // oprands > 1, add em' up
			    {
			    	selection.setBounds(1,collections);
			    	selected = pick(selection.lowerBound(),selection.upperBound());
			    }
			    	
			    
			 }
		 else// add last 2 results
			 {
			    selected = new Stack<Double>();
			    Action<Double> top = stack.pop();
		    	selected.push(top.getValue());
		    	if(!stack.empty())selected.push(stack.peek().getValue());
		    	stack.push(top);
			 }
		 
		 
		 // else if( totalOprands >= 0 && totalOprands<3)selected = pull((currentSize-1),currentSize) ; // <=2 -> last 2
		 // else if (totalOprands>2) selected = pull((currentSize-totalOprands+1),currentSize) ;
		
		 Double mult = 1.00;
		 while( !selected.empty() ) mult *= selected.pop() ;
		 if(oprand.on())stack.push(new Action<Double>(mult,'0'));  // entry for current computation
		 else stack.push( new Action<Double>(mult,'*') );     
		 format.reset(mult);
		 // result = format.format(); 
		   // System.out.println("adding");
		 oprand.flip();
			selection.initialize();
			totalComputations++;
		return mult; */
		
		
		
	}
	
	/**
	 * Divides the calculated value by a user value.
	 * 
	 * @param userValue
	 *            The value to multiply the current calculated value by.
	 * @pre. userValue is not equivalent to zero.
	 */
	public String divide()
	{
		Stack<Double> selected, tmp  ;
		
		if(selection.on()){ selection.makeRange(span()); selected = pick(selection.lowerBound(),selection.upperBound()); }
		else if( oprand.on() )  // current computation
		{
			int collections = oprand.getTotalOprands();
			selected = new Stack<Double>();
			if(collections==1)  // add with the previous result
			{
				Action<Double> top = stack.pop();
			    selected.push(top.getValue());
			    if(!stack.empty())selected.push(stack.peek().getValue());
			    stack.push(top);
			}
			else selected = pick(1,collections); // oprands > 1, add em' up  //  CHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
			    
		}
		else//  last 2 results  && not selected
		{
			selected = pick(span()-1,span());
		}
		 
		 
		 // values captured , now do calculations
		 
		tmp = new Stack<Double>();
		tmp.push(selected.pop()) ;
		Double divident = tmp.peek();
		Double divisor ;
		while( !selected.empty() )
		{
			divisor = selected.peek() ; tmp.push( selected.pop() ) ;
			if (divisor == 0.00) return "oops ! division by zero !!!"; 
			else divident /= divisor; 
		}
			
		Action<Double> result = new Action<Double>(divident,'/') ; 
		Action<Double> newOprand = new Action<Double>(divident,'0') ; 
		// System.out.println("Oprand = "+oprand.getTotalOprands());
		 
		 if( oprand.on() &&   oprand.getTotalOprands()>1 )
         {
	         if(selection.on())stack.push(newOprand);  // adding a new oprand to current computation
	         else stack.push(result);  // current computation done
         }
		 else    // insert new expression  before inserting the result 
		 {
			 while( !tmp.empty() )selected.push(tmp.pop()) ;  
			 while( !selected.empty() )
			 { 
				 newOprand = new Action<Double>(selected.pop(),'0') ;
				 stack.push(newOprand) ; 
			 }
			 stack.push(result);
		 }
		 
		 
		  
		 format.reset(divident);
		 if(oprand.on())oprand.flip();
		 selection.initialize();
		 totalComputations++;
		 
		 return format.format(); 
		
		
		
		/* Stack<Double> selected  ;
		int currentSize = weight();
		
		
		 selection.makeRange(currentSize);
		 if(selection.on()) selected = pick(selection.lowerBound(),selection.upperBound());
		 else if( oprand.on() )  // current computation
			 {  
			    int collections = oprand.getTotalOprands();
			    selected = new Stack<Double>();
			    if(collections==1)  // add with the previous result
			    {
			    	Action<Double> top = stack.pop();
			    	selected.push(top.getValue());
			    	if(!stack.empty())selected.push(stack.peek().getValue());
			    	stack.push(top);
			    	
			    }
			    else // oprands > 1, add em' up
			    {
			    	selection.setBounds(1,collections);
			    	selected = pick(selection.lowerBound(),selection.upperBound());
			    }
			    	
			    
			 }
		 else// add last 2 results
			 {
			    selected = new Stack<Double>();
			    Action<Double> top = stack.pop();
		    	selected.push(top.getValue());
		    	if(!stack.empty())selected.push(stack.peek().getValue());
		    	stack.push(top);
			 }
		 
		 
		 Double divident = selected.pop();
		 Double divisor ;
		 while( !selected.empty() )
			 { divisor = selected.pop() ;
			   if (divisor == 0.00) return "oops ! division by zero !!!"; 
			   else divident /= divisor;  // ignores zero divisor
			 }
			 
		 if(oprand.on())stack.push(new Action<Double>(divident,'0'));  // entry for current computation
		 else    stack.push(new Action<Double>(divident,'/'));     
		 format.reset(divident);
		 if(oprand.on())oprand.flip();
			selection.initialize();
			totalComputations++;
		 return format.format();  */		 
		
	}
	
	public double sin(int degree)
	{
		return Math.sin(radian(degree)) ;
	}
	
	public double cos(int degree)
	{
		return Math.cos(radian(degree)) ;
	}
	
	public static int factorial(int n) 
	{
	    return (n <= 1) ? 1 : n*factorial(n-1);
	}
	
	public double radian(int deg)
    {
        return ((2*Math.PI)/360.0) * deg;
    }
	
	public String expression()
	{
		String result = "" ;
		if(weight()==0) return "Start new computation | value = 0" ;
		else
			{
			   // boolean complete;
			   Stack<Action<Double>> tmp = new  Stack<Action<Double>>();
			   String  val , op;  
			   Action<Double> top =  stack.pop() ;
			   
			   format.reset( top.getValue() ); 
			   val = format.format();
			   //format.reset( tmp.peek().getValue() ); val = format.format();
			   // result = tmp.peek().
			   if(top.getOperator()=='0') op=",";
			   else   op=""+top.getOperator();
			   
			   while ( !stack.empty() && (stack.peek().getOperator()=='0') )tmp.push(stack.pop());
			   while(!tmp.empty())
				   { 
				      format.reset( tmp.peek().getValue() ); result += format.format(); 
				      
				      stack.push(tmp.pop());
				      if(  !(tmp.empty() && !op.equalsIgnoreCase(","))  ) result+=op;
				     
				   }
			   if(top.getOperator()=='0') result =  "[ " +result +val + " ]" ;
			   else result = "( " + result + " )" ;
			   stack.push(top);
			   result += "  | value = "+val;
			}
		
		return result;
		
	}
	
	/**
	 * Get the current calculated value.
	 * 
	 * @return The current calculated value.
	 
	public void setCommunicator(Controller control)
	{
		communicator = control;
	}*/
	
	/* public String negate()
	{
	    if(selection.on() && !oprand.on()) return "Must not negate past results !";
		 // if (!mode.isDefaultMode()) return "Error: not selected!" ;
		 mode.evaluate(weight());
		String  result = modify(mode.lowerBound(),mode.upperBound(),false);
		
		return result ;    
		  
	}  */
	
	public String select(int bound)
	{
	  if(weight()==0)return errorMsg[10]  ;
	  if(selection.on())
	  {
	    if(Math.abs(bound)>weight()) 
	                    return errorMsg[7] ; 
	    else if(selection.currentFill()==2)
                        return errorMsg[8]  ;
        else if(selection.lowerBound()<0 ) // dont allow -ve in double bound selection
        return errorMsg[9]  ; 
                                            
	  }
      if (bound == 0 && !oprand.on())selection.setBounds(1,totalComputations); // pick all results
      else if(bound == 0 && oprand.on())selection.setBounds(1,oprand.getTotalOprands());  // pick all from current computation
	  else selection.put(bound);
	  
      return "selected" ;
	  
     }
	
	
	public String check()  // can check the value of a chosen elemet in stack
	{
		
		String phase;
		  int index;
		  String value ;
		if( selection.on()  )  // can only check past results since you can see expression of current computation
		{		   		   
		   // int index = -1;
		   selection.makeRange(span());
		   // Double  value ;
		   
	       if ( (selection.currentFill()==2)&& (selection.upperBound()!=selection.lowerBound()) ) return errorMsg[1];
	    	   
	      /* else   // fill==1
	       {System.out.println("here2 = ");
	    	   if (mode.upperBound()==size)  index = mode.lowerBound()  ;
	           else if (mode.lowerBound()==1)  index = mode.upperBound();
	       } */
	       // System.out.println(" value selected = "+index);
	       index = selection.lowerBound();
	       format.reset( (pick(index,index)).peek()  );
	       value = format.format();
	       
	       
		}
		
		else // show last one
			{
			  
			  if(oprand.on()) index=oprand.getTotalOprands();
			  else index = totalComputations; 
			  format.reset( stack.peek().getValue()  );
		      value = format.format();
			}
		if (oprand.on())phase = "current" ;
	       else phase = "history" ;
		selection.initialize();	
		return value+" @ "+index+" in "+phase;
	}  
	
	
	protected Stack<Action<Double>> pull(int bottom, int top)  // extract selected elements from stack
	{
		
		
		
		Stack<Action<Double>> result = new  Stack<Action<Double>>(); // selected
		Stack<Action<Double>> tmp = new  Stack<Action<Double>>(); // selected
		int distance = span();
		System.out.println("pull --> bottom = "+bottom+" top = "+top+" distancs = "+distance);
		 if(oprand.on())  // pick from  current computation	    	
		    {
			  
			   while(distance>=bottom)
			   { 
				   
				   if(distance>=bottom && distance<=top){System.out.println("pulling :  "+stack.peek().getValue()); result.push(stack.pop()); }
				   else tmp.push(stack.pop());
				   
	   
				   distance-- ;
	    		    
	    	   }
			 
			  
		    }
		    else // pick from previous results
		    {
		    	 ++distance ;   
				   while(distance>=bottom && !stack.empty())
				   { 
		    		    
					   tmp.push(stack.pop());
					   
					   
					   if(tmp.peek().getOperator()!='0')  --distance;
						   
					   
					   if(distance>=bottom && distance<=top) result.push(tmp.pop());
	
		    	   }
		
		    }
		 while(!tmp.empty()) stack.push(tmp.pop());
			return result;
			
		
		
	}  
	
	
	
	 protected Stack<Double> pick(int bottom, int top)  // return the values of selected items in stack 
		{
			System.out.println("pick --> bottom = "+bottom+" top = "+top);
		    Stack<Double> result = new Stack<Double>();
			
			
			Stack<Action<Double>> tmp = new  Stack<Action<Double>>(); // selected
			int distance = span();
			 if(oprand.on())  // pick from  current computation	    	
			    {
				  
				   while(distance>=bottom)
				   { 
		    		    
					   tmp.push(stack.pop());
					   
				       if(distance>=bottom && distance<=top) result.push(tmp.peek().getValue());
						    
					   distance-- ;
		    		    
		    	   }
				 
				  
			    }
			    else // pick from previous results
			    {
			    	   while(distance>=bottom)
					   { 
			    		    
						   tmp.push(stack.pop());
						  
						   if(tmp.peek().getOperator()!='0') 
						   { 
							  
							   if(distance>=bottom && distance<=top) result.push(tmp.peek().getValue());
							   distance--;
							   
						   }
			    		 
			    	   }
					   
					   
					
					   
			    }
			 while(!tmp.empty()) stack.push(tmp.pop());
			return result;
				
		}
	 
	 public String negate()
	 {
			 String result = "changed";
		      
		     if (!selection.on()) return "Not selected !" ;
		     
			 if(!oprand.on()) return "May not modify history !" ;
			 
		     selection.makeRange( oprand.getTotalOprands() );
		     
		     Stack<Action<Double>> change = pull(selection.lowerBound(),selection.upperBound());
		     Stack<Action<Double>> temp = new  Stack<Action<Double>>();
		     Stack<Action<Double>> tmp = new  Stack<Action<Double>>();
		     
		     int traverse = span();
		     int top = selection.upperBound(); 
		     while (traverse > top) { temp.push(stack.pop()); traverse--; }
		     while( !change.empty() )  tmp.push( new Action<Double>( (change.pop().getValue() * (-1)) , '0') );
		     while( !tmp.empty() )  temp.push( tmp.pop() );
		     while( !temp.empty() )  stack.push( temp.pop() );
	    	  	     	  
		     selection.initialize();	 
		     
			return result ;    
			  
	 }
	 
	/* private void modify(int bottom, int top, int operator)  // modify elements from current 
		{   System.out.println("modify --> bottom = "+bottom+" top = "+top);
		     Stack<Action<Double>> tmp = new  Stack<Action<Double>>();
			
			
			
			
			//System.out.println("modifying2");
			// System.out.println("weight() = "+ weight()+" (bot, top) = ["+bottom+","+top+"]");
			while(weight()>top) tmp.push(stack.pop());
			// System.out.println("modifying3");
			
			
			// if (bottom==top) {chosen = stack.pop()* (-1); stack.push(chosen); }
			while(weight()>=bottom)
			{ 
				Double oldValue = stack.peek().getValue();
				stack.peek().setValue(oldValue*(-1));   
				tmp.push( stack.pop() );
		    }
					         
			while( !tmp.empty() )  stack.push( tmp.pop() );
			
			
	        selection.initialize() ;
	        
			
		}  */
	 
		
	 public int weight()
     {
		return stack.size();	
	 }
	 
	 public double pi()
     {
	    return PI ;		
	 }
	 
	 private int span()  // how far the operator to be applied, within current computation or accross history-list
     {
		if(oprand.on()) return oprand.getTotalOprands();
		
		return totalComputations;
	 }
	 
	 
	 
	 private void shuffle(Double val,char op)
	 {
		Action<Double> top = stack.pop();
		stack.push(new Action<Double>(val,op));
		stack.push(top);
	 } 
	
	
}
	


