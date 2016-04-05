/**     
 *  =================================================== 
 *  author :  Yari Yousefian
 *  This class is model, the actual calculator   
 *  =================================================== 
 */

 import java.util.ArrayList;
 import java.util.Stack;
 import java.util.StringTokenizer;

public class Model  
{
	public static final Double PI = 3.14159 ;
	private final int TERM = 4;
	private final int MULTIPLICATION = 0;
	private final int PLUS = 1;
	private final int DIVIDE = 2;
	private final int MINUS = 3;
	
	
	private Stack<Expression> stack;
	private ArrayList<FavouriteItem> favourites;
	
	private DisplayFormat format;
	private SelectionState selection;
	private int id;
	// private int totalOprands;
	
	
	
	
	/**
	 * Creates a model with no user values and a calculated
	 * value of zero.
	 * 
	 */
	public Model()
	{
		stack = new  Stack<Expression>();
		format = new DisplayFormat(0.00);
		selection = new SelectionState() ;
		favourites = new  ArrayList<FavouriteItem>();
		 id = 0;
		 // totalOprands = 0;
		 
	}

	public void insert(Expression entry)
	{ 
		
		entry.setID(++id);
		stack.push( entry );
		
		
	}
	
	public void undo()  //  undo
	{
 		    if(size()>0) 
		    {
			   if (!selection.active())    // delete last item on stack
				{
					
				   Expression top = stack.pop();
				   
				   if(!(top.type()==TERM))				   
				    {
					 
					  stack.push(top.removeFirst());
					  stack.push(top.removeSecond()); 
				    }
					
				}
			  else   // delete selected items
				   {
					  Stack<Expression> selected = delete();
					  while( !selected.empty() ) selected.pop() ; // garbage collection	
				   }
			 
		    }
		    
         
          selection.reset();  
          
					
	}
	
	
	/**
	 * Clears the user values and the calculated value.
	 */
	public void purge()
	{
		while(!stack.empty())stack.pop() ;			
		selection.reset();
		
	}

	
	public void add()
	{		
	  if(size()>1)
	  {
		System.out.println("here in add");
		
		Expression result ;  // result item to be pushed on stack
		
		
		 if( selection.active() ) // add selected
		 { 
			 
			 Stack<Expression> selected = pick(); 
			 if(!selected.isEmpty())  // selection in valid range
			 {
			   result = selected.pop();			 
			   while( !selected.empty() )  result =  new Addition (selected.pop(),result) ;
			   stack.push(result);
			 }
			 selection.reset();		 
		 }
		 else   //  add last 2 
		 {			 
			    
			 Expression last = stack.pop();
			    result = new Addition(stack.pop(),last);
			    stack.push(result);
			   
		 }
  
	  } 
	}
	
	/**
	 * Subtracts the calculated value by a user value.
	 * 
	 * @param userValue
	 *            The value to subtract from the current calculated value by.
	 */
	public void subtract()
	{
		if(size()>1)
		  {
			
			
			Expression result ;  // result item to be pushed on stack
			
			
			 if( selection.active() ) // add selected
			 { 
				 
				 Stack<Expression> selected = pick(); 
				 if(!selected.isEmpty())  // selection in valid range
				 {
				   result = selected.pop();			 
				   while( !selected.empty() )  result =  new Subtraction (selected.pop(),result) ;
				   stack.push(result);
				 }
				 selection.reset();		 
			 }
			 else   //  add last 2 
			 {			 
				    Expression last = stack.pop();
				    result = new Subtraction(stack.pop(),last);
				    stack.push(result);
			 }
	  
		  } 
	}
	
	/**
	 * Multiplies the calculated value by a user value.
	 * 
	 * @param userValue
	 *            The value to multiply the current calculated value by.
	 */
	public void multiply()
	{
		if(size()>1)
		  {
			
			
			Expression result ;  // result item to be pushed on stack
			
			
			 if( selection.active() ) // add selected
			 { 
				 
				 Stack<Expression> selected = pick(); 
				 if(!selected.isEmpty())  // selection in valid range
				 {
				   result = selected.pop();			 
				   while( !selected.empty() )  result =  new Multiplication(selected.pop(),result) ;
				   stack.push(result);
				 }
				 selection.reset();		 
			 }
			 else   //  add last 2 
			 {			 
				    Expression last = stack.pop();
				    result = new Multiplication(stack.pop(),last);
				    stack.push(result);
			 }
	  
		  } 	
	}
	
	/**
	 * Divides the calculated value by a user value.
	 * 
	 * @param userValue
	 *            The value to multiply the current calculated value by.
	 * @pre. userValue is not equivalent to zero.
	 */
	public boolean divide()
	{
		
		if(size()>1)
		  {
			
			
			Expression result ;  // result item to be pushed on stack
			
			
			 if( selection.active() ) // add selected
			 { 
				 
				 Stack<Expression> selected = pick(); 
				
				 if(!selected.isEmpty())  // selection in valid range
				 {
				   result = selected.pop();			 
				   while( !selected.empty() && !result.redFlag() )  result =  new Division(selected.pop(),result) ;
				   if(result.redFlag()) return true; // division by zero
				   stack.push(result);
				 }
				 selection.reset();		 
			 }
			 else   //  add last 2 
			 {			 
				    Expression last = stack.pop();
				    result = new Division(stack.pop(),last);
				    result.evaluate(1.00);
				    if(result.redFlag())   return true;
				    else stack.push(result);
			 }
	  
		  } 	
		
		return false;
		 
		
	}
	
	/*public double sin(int degree)
	{
		// expressionMode.off();
		return Math.sin(radian(degree)) ;
		 
	}
	
	public double cos(int degree)
	{
		// expressionMode.off();
		return Math.cos(radian(degree)) ;
		
	} */
	
	/*public static Long factorial(int num) 
	{
		// expressionMode.off();
		Integer i = num;
		Long n = i.longValue();
	    return  ((n <= 1) ? 1 : (int)(n*factorial(n-1)));
	}  */
	
	public Expression lastExpression()  // get the top of the stack
	{
		return stack.peek();
	}

	public void select(String inp)
	{		
		 if(occurance(inp,".")==0)
		    {
		    	int index = Integer.parseInt(inp);
		    	if(index<0)selection.add(size()+index+1,size()); // negative x = last x elements
		    	else selection.add(index,index);   // one single element
		    }
		    else
		    {	       
		       StringTokenizer tokens = new StringTokenizer(inp,".");
			   String lower = tokens.nextToken();
			   String higher = tokens.nextToken();
			   selection.add(Integer.parseInt(lower),Integer.parseInt(higher));		 
		    }	    
		selection.on();
	    // expressionMode.off();
    }
	

	private Stack<Expression> delete()  // extract desired elements from stack,an item is desired if its selected 
	{
		
		Stack<Expression> result = new  Stack<Expression>(); // selected
		Stack<Expression> tmp = new  Stack<Expression>(); 
		int range = size();
		
		while(range>0)
		{
			if(!selection.active())result.push(stack.pop());
			else if( selection.selected( range ) ) result.push(stack.pop());				 				
			else tmp.push(stack.pop());
			range--;
		}
		
		while(!tmp.empty())stack.push(tmp.pop());
		
			
		return result;
		
	}  
	
	
	
	 private Stack<Expression> pick()  // dont extract but return duplicate of desired items in stack , an item is desired either if its selected or its in specified range
		{
		 int range = size();
		 Stack<Expression> result = new Stack<Expression>();
	     Stack<Expression> tmp = new  Stack<Expression>(); 
	     
		
	     
			 while(range>0)
			   { 	
				 System.out.println("range = "+range);
				   tmp.push(stack.pop());
				       
					     	    	   
					       if(selection.selected( range )) result.push(tmp.peek().clone());
				    	   range--;
				          
					    			  	    		    
	    	   }
		 
		 
		 while(!tmp.empty()) stack.push(tmp.pop());
		 return result; 
		 	
		}
	  
	 
	 public void negate()  // can negate only from current oprands 
	 {
		    Expression top = stack.pop();
		    stack.push( new Multiplication( top, new Term(0,-1.00, false) ) );
			  
	 }  
	 
	 
	 public String[] display()
		{
			
			if(size()==0)
			{
				String[] res = { "", "Start new calculation"} ;
				return res;
			}
			
			String value;
			if(stack.peek().isConstant())  // no variable
			{
				 
				format.reset(stack.peek().evaluate(1.00));
				value = format.format();
				
			}
			else value = stack.peek().toString();
			
			String result = "" ;
			Stack<Expression> temp = new  Stack<Expression>();
				  
				   
				// stack.push(top);   
				  
				while(!stack.empty())  
				{
					 Expression top = stack.pop() ;
					 
					 result = topExpression(top) + result ;
					 temp.push(top);
					
			    }
				
				while(!temp.empty())stack.push(temp.pop());
				String[] res = { result, value};
			  return  res ;
			
		}
	 private String topExpression(Expression exp)
	 {
		 String result;
		 result =  exp.toString();
		  
		  
		  if(!stack.empty()) result = "," + result;
		  
		  return result ;
	 }
	 
	 private Expression get(int id)  // get the expression with specified id
	 {
		 Stack<Expression> temp = new  Stack<Expression>();
		  // boolean found = false;
		   
			// stack.push(top);   
			 Expression result = new Term(0,0,true); 
			while(!stack.empty() )  
			{
				 Expression top = stack.peek() ;
				 if(top.getID()==id){ result = top;  break; }  // use clone ?
				
				 temp.push(stack.pop());
				
		    }
			
			while(!temp.empty())stack.push(temp.pop()); 
			
			return result;
		  
	 }
	 
	 public ArrayList<Expression> getSelected()  // get the expression with specified id
	 {
		 
		 ArrayList<Expression>  list = new  ArrayList<Expression>();
		 for(int i = 0; i < favourites.size(); i++) if( favourites.get(i).isSelected() )list.add(get(favourites.get(i).getID()))  ;
				 
		return list;	 
		 
		  
	 }
	 public void deleteSelected()  // get the expression with specified id
	 {
		 
		 System.out.println("size b4 = " + favourites.size());
		 for(int i = (favourites.size()-1); i >=0 ; i--) 
		 {
		   if( favourites.get(i).isSelected() )
			 {
			    updateHistory(favourites.get(i).getID());
			    favourites.remove(i)  ;
			 }
		 }
		 System.out.println("size b4 = " + favourites.size());
		 
	 }
	 
	 
	 
	 public String evaluate(Double value)
     {
        
        Double result = stack.peek().evaluate(value);
        if(stack.peek().redFlag()){stack.peek().flagReset(); return "Ooops , division by zero !!!" ; }
        
        format.reset(result);
        return format.format();
     }
	
	 
	 private int occurance(String word, String character )
	 {
	 		return  word.length() - word.replace(character, "").length();		
	 }
	 
	 public void addFavourite()
	 {
		   Expression exp = stack.peek() ;
		if(!inList(exp.getID()))  
			{
			 int newId = ++id;
			 exp.setID(newId);
			 favourites.add(new FavouriteItem(exp.toString(),newId))  ;
			}
	 }
	 
	 public FavouriteItem[] getFavourites()
	 {
		 FavouriteItem[] items = new FavouriteItem[favourites.size()];
		 
		 for(int i = 0; i < favourites.size(); i++)
		 items[i] = favourites.get(i) ;
		 
		 return   items  ;
	 }
	 	
	 public int size()
     {
		return stack.size();	
	 }
	 public void updateFavourite()
     {
		 for(int i = 0; i < favourites.size(); i++)
		 {
			 if( favourites.get(i).isRemove() )
				 {
				    
				    updateHistory(favourites.get(i).getID());
				    favourites.remove(i);
				 }
			 
		 }
	 }
	 public void updateHistory(int searchId)
     {
		 Stack<Expression> temp = new  Stack<Expression>();
		  boolean found = false;
		   
			// stack.push(top);   
			  
			while(!stack.empty() )  
			{
				 Expression top = stack.pop() ;
				 if(top.getID()==searchId){ top.setID(0); found = true; break; }
				
				 temp.push(top);
				
		    }
			
			while(!temp.empty())stack.push(temp.pop()); 
     }
	 private boolean inList(int id)
	 {
		 for(int i = 0; i < favourites.size(); i++)
		 {
			if(id ==   favourites.get(i).getID()) return true;
		 }
		 return false;
	 }
	 
	 private boolean inHistory(int id)
	 {
		 Stack<Expression> temp = new  Stack<Expression>();
		  boolean found = false;
		   
			// stack.push(top);   
			  
			while(!stack.empty() )  
			{
				 Expression top = stack.pop() ;
				 if(top.getID()==id){  return true; }
				
				 temp.push(top);
				
		    }
			
			while(!temp.empty())stack.push(temp.pop());
			return found;
	 }
	
	 
}
	


