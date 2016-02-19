/**     
 *  =================================================== 
 *  author :  Yari Yousefian
 *  This class is the user interface   
 *  =================================================== 
 */

import java.awt.Color;

import javax.swing.JFrame ;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

public class View extends JFrame  
{
	// protected JTextField textfield ;
    protected int characters;
    // protected Controller monitor;
    // protected boolean append;
    // mode 
    protected JTextField textfield ;
    protected final Color myOrange = new Color(255,69,0);
    protected final Color myBronze = new Color(201,99,51);
    protected final Color myGreenish = new Color(169,219,128);
    protected final Color myYellow = new Color(204,204,0);
     
    View(String lbl) 
    {
        super(lbl);       
        //replace();
       // append();
    } 
    
    
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
    
    /* protected void clear() 
    {
    	AbstractDocument doc = (AbstractDocument) textfield.getDocument();
		  int length = doc.getLength() ;
		  if(length>0)
		  {
	        int position = length - 1;
	        
	          try {
	                doc.remove(position, length);
	              } catch (Exception exc) 
	                   { 
	        	         System.out.println("Calculator could not reset !");
	        	         System.out.println(exc.getMessage());
	        	         System.out.println(  exc.getLocalizedMessage()  );
	                   } 
		  }
		  characters = 0;
    } */
   
    
    
    
    /*protected void setMonitor(Controller controller) 
    {
    	
    	
    }*/
}
