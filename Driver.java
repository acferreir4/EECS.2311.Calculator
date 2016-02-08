public class Driver
{
    public static void main(String[] args) 
    {
          GraphicalView view = new GraphicalView();
          Model model = new Model();
    	
    	  new VMC(model,view);
       
    }
}
