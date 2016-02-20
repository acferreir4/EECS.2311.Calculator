import java.awt.Color;

import javax.swing.JFrame ;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

/**
 * A class meant to be extended by all
 * views that will be shown to the user
 * @author Yari Yousefian
 *
 */
public class View extends JFrame  
{
    protected int characters;
    protected JTextField textfield ;
    protected final Color myOrange = new Color(255,69,0);
    protected final Color myBronze = new Color(201,99,51);
    protected final Color myGreenish = new Color(169,219,128);
    protected final Color myYellow = new Color(204,204,0);
     
    View(String lbl) 
    {
        super(lbl);       
    } 
    
    /**
     * Defines the default backspace operation
     */
    protected void backspace() 
    {
    	AbstractDocument doc = (AbstractDocument) textfield.getDocument();
    	int length = doc.getLength() ;
    	if(length>0)
    	{
    		int position = length - 1;
    		try
    		{
    			doc.remove(position, 1);
    		} 
    		catch (Exception exc)
    		{
    			System.out.println("Calculator could not backspace !");
   	        	System.out.println(exc.getMessage());
   	        	System.out.println(  exc.getLocalizedMessage()  );
    		}
    		characters--;
    		// append();
    	}
    }
}
