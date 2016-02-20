/**
 * Main class
 * Gets everything going when the 
 * application is launched
 *
 */
public class Driver
{
	/**
	 * Main method
	 * Creates a new view, model and controller
	 * @param args Command line arguments
	 */
    public static void main(String[] args) 
    {
          GraphicalView view = new GraphicalView();
          Model model = new Model();
    	
    	  new VMC(model,view);
       
    }
}
