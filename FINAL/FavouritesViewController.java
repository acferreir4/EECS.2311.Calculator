
import java.util.ArrayList;

public class FavouritesViewController 
{
	private FavouritesView view;
	 
	protected Controller monitor;
	protected Model model;
	   
	  
	
	public FavouritesViewController (Controller ctrl, Model modl)
	{
		monitor = ctrl ;
		  model = modl;
	}
      
	public void inform(Message msg)
	{  
		
		    
		if(msg.getSource().equalsIgnoreCase("graph")) select(); 
		// else if(msg.getContent().equalsIgnoreCase("sel"))model.updateFavourite();
	    else if(msg.getContent().equalsIgnoreCase("remove"))delete();	
	    	
		
	}
	public void select()
	{
		ArrayList<Expression> exprs = model.getSelected();
		monitor.displayGraph(exprs);
	}
	
	public void delete()
	{
		model.deleteSelected();
		view.updateListBox();
		// monitor.displayGraph(model.getSelected());
	}
  
	public void setView(FavouritesView v)
	{
		view = v;
	}
	
	
	

	/*private boolean valid(String str)
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
	}  */
	 
}

	
	



