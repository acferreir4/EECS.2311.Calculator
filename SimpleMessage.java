
public class SimpleMessage extends Message 
{   
    private final String[] instruction = {"clear","select", "value", "undo","+", "-", "x",  "/","sin","cos","enter","rad","!","pi","sign"};
    public String reply,state;
	// public int showing = 0;
	
	/* public void edit(String newMsg)
	{
		content = newMsg;
	}*/
	
    
    public void reply(String rep)
    {
  	  
  	  reply = rep;
    }
    
    
	public int type()
	{
		int index = -1;
		
		for(index = 0; index < 15; index++) if(instruction[index].equalsIgnoreCase(source)) break ;
		
		return index;
	}
}

