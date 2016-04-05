
 

public class GraphicalViewController 
{   
	private GraphicalView view;
	protected Controller monitor;
	protected Model model;
	   
	   
	
	public GraphicalViewController (Controller ctrl,Model mdl)
	{
		 monitor = ctrl ;
		  model = mdl;
	}
      
	public void inform(Message msg)
	{  
		
		if(msg.getSource().equalsIgnoreCase("forward"))	monitor.swap(3);    
		else if(msg.getSource().equalsIgnoreCase("back"))	monitor.swap(1);
		else if(msg.getContent().equalsIgnoreCase("addFavourite"))	model.addFavourite();
		else if(msg.getContent().equalsIgnoreCase("forward")) monitor.swap(3);	// go to fav view		
		/*{
			Message message = new Message();
			message.setContent("favourite") ;
	        
			message.setSource("graphicalViewController") ;
	        
	        monitor.inform(msg);    
		}*/
		
	}
	public void setView(GraphicalView v)
	{
		view = v;
	}
	
  /*
	private boolean valid(String str)
	{
		boolean result = true;
		
		for(int i = 0; i < str.length(); i++)  
		   {
					    if( !( Character.isDigit(str.charAt(i)) || str.charAt(i)=='.' ) )
					    {
			    		  result = false; 
			    		  break;
					    }
			    
			}

		return result;
	} */
	 
}

	
	

