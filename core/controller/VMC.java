/**     
 *  =================================================== 
 *  author :  Yari Yousefian
 *  This class is the user interface   
 *  =================================================== 
 */
public class VMC extends Controller
{
	private GraphicalView view;
	// private final String emptyMsg = "Record empty" ;
	private DisplayFormat format;
	private int prefix;
	public VMC(Model m, GraphicalView v)
	{
		model =   m;
		view =  v ;
		view.setMonitor(this);
		format = new DisplayFormat(0.00);
		prefix = 0;
	}
      
	public void inform(SimpleMessage msg)
	{  
		int cmd = msg.type();
		
		String reply;
		
		if(cmd==0)  // clear
		{ 
		     model.purge() ; 
			 view.clear(); view.setFieldState(true);
		} 
		
		else if(cmd==1)  // select
		{
			view.clear();
			if((msg.content).equalsIgnoreCase("empty"))  // select all
			{
				// if(model.weight()>0) 
			
				reply = model.select(0) ;
			    if(!reply.equalsIgnoreCase("selected"))  view.display(reply);
			
			// else view.display("Error: Nothing to select");
			}
			else  
			{
				boolean inputError = true;
			  
				// if (valid(msg.state))
				// { 
			    format.reset( Double.parseDouble(msg.content) );
			    //if(format.hasFraction()) view.display("Error: integer value required !");
			    // else
			    // {
			    int index = Integer.parseInt(format.format());
			    
			    if(index==0)view.display( "Error:index out of bound!");
			    else
			    {
			    	reply = model.select(index) ;
					if(!reply.equalsIgnoreCase("selected"))  view.display(reply);
					else inputError = false;
			    }
			     //}
			    
			    
			  // }
		     //else view.display("Error: Invalid selection !");  
			  
			    if(inputError) view.setFieldState(false);			  
			    else view.setFieldState(true);
			}
		}
		
		else if(cmd==2)   // value
		{
			if(model.weight()>0)
			{
				// if(append.on()) ;
				// if((msg.state).equalsIgnoreCase("empty"))  
				view.display(model.check());	// either selected or show the last item's value		  
				// else  view.display("unknown action !");	// must use select button to select	
			}
			else view.display("Nothing to check !");
		}
		
		else if(cmd==3) // undo
		{
			if((msg.content).equalsIgnoreCase("empty"))
			{
				reply = model.delete();
			    view.display(reply);			  
			}
		    else  // backspace 
		    {
		    	if(  (msg.content).equalsIgnoreCase("expr")  ) {view.display(model.expression());view.setFieldState(false);}
		    	else { view.backspace(); view.setFieldState(true);}
		    }
		}
			  
		else if(cmd==4) // +
		{
			if(model.weight()>0)
			{
				String cp = msg.content ;
			    if( (cp.charAt(cp.length()-1)=='!') || (cp.indexOf('|')>=0) )  msg.content = "empty"; // some msg at display
			    if(!msg.content.equalsIgnoreCase("empty") && msg.state.equalsIgnoreCase("append")) model.insert(Double.parseDouble(msg.content));	
				     
			    format.reset(model.add());
			     
			    view.display(format.format()); 
			}
			else view.display("Nothing to add !");
		}
		
		else if(cmd==5) // -
		{
			if(model.weight()>0)
		    {
				if(!msg.content.equalsIgnoreCase("empty")&& msg.state.equalsIgnoreCase("append")) model.insert(Double.parseDouble(msg.content));	
		     
				format.reset(model.subtract());
				view.display(format.format());
		    }
			else view.display("Nothing to subtract !");
		}  
		
		else if(cmd==6) // x
		{
			if(model.weight()>0)
		    {
				if(!msg.content.equalsIgnoreCase("empty")&& msg.state.equalsIgnoreCase("append")) model.insert(Double.parseDouble(msg.content));	
		     
			    format.reset(model.multiply());
			     
			    view.display(format.format()); 
		    }
			else view.display("Nothing to multiply !");
		}  
		
		else if(cmd==7)  //  div
		{
			if(model.weight()>0)
		    {
				if(!msg.content.equalsIgnoreCase("empty")&& msg.state.equalsIgnoreCase("append")) model.insert(Double.parseDouble(msg.content));	
		    	
		        view.display(model.divide()); 
		    } 
			else  view.display("Nothing to divide !");
		}
		
		else if(cmd==8)  //  sin
	    {
			prefix = 1;
	    }
		
		else if(cmd==9)  //  cos
	    {
			prefix = 2;
	    }
		
		else if(cmd==10)// enter
		{
			if ( msg.content.equalsIgnoreCase("empty") )view.display("No value entered !");
			else if(prefix>0) // sin or cos button pressed
			{
				Double input = Double.parseDouble(msg.content) ;
				System.out.println(" doubled = "+input);
				format.reset(input);
				if(format.hasFraction()) view.display("Integer value required !");
				else 
				{
					int degree = Integer.parseInt(format.getIntegerPart());
					    
				    double r ;
				    
				    if (prefix==1)
				    {
				    	r  = model.sin(degree);
					    view.sin(degree,r);
				    }
				    else 
				    {
				    	r = model.cos(degree);
					    view.cos(degree,r);
				    }
																				
				}
				prefix=0;
			}
			else  model.insert(Double.parseDouble(msg.content)); // enter     
		}
		
		else if(cmd==11)  //  rad
	    {
			Double degree = Double.parseDouble(msg.content) ;
			format.reset(degree);
			if(format.hasFraction()) view.display("Integer value required !");
			else
			{
				int rad = Integer.parseInt(msg.content);
				format.reset(model.radian(rad));
				view.display(format.format());
			}
	    }
		
		else if(cmd==12)  //  fact
	    {
			Double degree = Double.parseDouble(msg.content) ;
			format.reset(degree);
			if(format.hasFraction()) view.display("Integer value required !");
			else
			{
				int input = Integer.parseInt(msg.content);  // test
				int output = model.factorial(input);
				    
				view.display(Integer.toString(output));
			}
	    }
		
		else if(cmd==13)  //  pi
	    {
			format.reset(model.pi());	  
			view.display(format.format());	 		     
	    }
			
		else if(cmd==14)  //  +/-
	    {
			if (msg.content.equalsIgnoreCase("empty"))
			{
				reply = model.negate();
				if ( !reply.equalsIgnoreCase("changed") ) view.display(reply);
			}
			else
			{
				Double negative = Double.parseDouble(msg.content) * (-1);
				// model.insert(newEntry) ;
				format.reset(negative);
				view.display(format.format());
				view.setFieldState(true);
			}
	
	    }     
		
	}
	
	/*
	private boolean valid(String str)
	{
		boolean result = true;
		int dots = occurance(str,".") ;
		if ( dots>1 || (dots==1 && str.endsWith(".") ) ) result = false;
		
		return result;
	}*/
	  
}
