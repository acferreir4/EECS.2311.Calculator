/**
 * Represents a message from view to controller
 */
public class SimpleMessage extends Message 
{   
    private final String[] instruction = {"clear","select", "value", "undo","+", "-", "x",  "/","sin","cos","enter","rad","!","pi","sign"};
    public String reply,state;
    
    /**
     * The reply of the message
     * @param rep The reply
     */
    public void reply(String rep)
    {
    	reply = rep;
    }
    
    /**
     * Gets the message type
     * A message type is defined by the index
     * of the message in the "instruction" array
     * @return The message type
     */
	public int type()
	{
		int index = -1;
		
		for(index = 0; index < 15; index++) if(instruction[index].equalsIgnoreCase(source)) break ;
		
		return index;
	}
}

