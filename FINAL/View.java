
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame ;
import javax.swing.JPanel;
import javax.swing.UIManager;




public class View extends JFrame  
{
	
	public static final Color BACKGROUND = new Color(102,108,63,200);
    public static final Color ORANGE = new Color(255,69,0);
    public static final Color BRONZE = new Color(201,99,51);
    public static final Color GREENISH = new Color(169,219,128);
    public static final Color YELLOW_GREEN = new Color(153,204,51);
    public static final Color BRIGHT_GREEN = new Color(170,255,187);
    public static final Color  BRIGHT_BRONZE = new Color(255,204,153) ;
    public static final Color  ADAPTIVE = new Color( 255 , 156 , 0 );
    public static final Color  BLUEWISH = new Color(170,255,187) ;
    public static final Color  COPPER_BRONZE = new Color(255,204,153) ;
   // private SubView mode;
   // private SubView[] subviews;
    private CalculatorView calculatorView;
    private GraphicalView graphicalView;
    private FavouritesView favouritesView;
    private int active ;
  
    public View() 
    {
        super("YU-82 Calculator");
        // subviews = new SubView[3];
        /*   */
        calculatorView =  new CalculatorView(this);
        graphicalView =  new GraphicalView(this) ;
        favouritesView = new FavouritesView(this) ;
        
        
        // setStyle();
        setSize(311, 427);  //  size of the frame  (311, 377)
        setResizable(false);  // frame cannot be resized
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // to exit the frame when closed
	     // setLayout(new BoxLayout( this, BoxLayout.Y_AXIS));
	   // getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	    
        // setBackground(BACKGROUND);
        
        active = 1;
    }
    
    
   /* private final void setStyle()
    {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
        } catch(Exception exc) { 
        	                    System.out.println(" Could not plug look and feel ! daaamn !");  
        	                    System.out.println(exc.getMessage());
 	        	                System.out.println(  exc.getLocalizedMessage()  );
                             }
    }  */
    
    
   
    public void activate(int replacement) 
    {
    	deactivate();
    	if(replacement==1)add(calculatorView);
    	else if(replacement==3) add(favouritesView);  
    	active = replacement;
        revalidate();
        repaint();
            	
        
    }
    public void activateGraph(ArrayList<Expression> exp,boolean draw) 
    {
    	deactivate();
    	
    	
    	
    	graphicalView.resetGraph(exp,draw);
    	add(graphicalView); 
    	active = 2;
        revalidate();
        repaint();
            	
        
    }
    public void activateFavourite(FavouriteItem[] items) 
    {
    	deactivate();
    	
    	favouritesView.setList(items);
    	add(favouritesView); 
    	active = 3;
        revalidate();
        repaint();
            	
        
    }
    
    public void deactivate() 
    {
    	if(active==1)remove(calculatorView);
    	else if(active==2)remove(graphicalView);
    	else remove(favouritesView);   	
    }
	
    public void attachSubControllers(CalculatorViewController calc, GraphicalViewController graph, FavouritesViewController fav) 
    {
    	 calculatorView.setMonitor(calc);
     	 graphicalView.setMonitor(graph);
     	 favouritesView.setMonitor(fav);
            	
        calc.setView(calculatorView);
        graph.setView(graphicalView);
        fav.setView(favouritesView);
        
        add(calculatorView);
        setVisible(true);
    }
    
   
    
	
	/*
    protected int characters;
    // protected Controller monitor;
     // protected boolean append;
    // mode 
     protected JTextField textfield ;
    View(String lbl) 
    {
        super(lbl);       
        //replace();
       // append();
    } 
    
    protected void clear() 
    {System.out.println("clearing textfield");
    	  textfield.setText("") ;
		  characters = 0;
		 // append();
    }
    protected void backspace() 
    {
    	
    			AbstractDocument doc = (AbstractDocument) textfield.getDocument();
    			  int length = doc.getLength() ;
    			  if(length>0)
    			  {
    		        int position = length - 1;
    		        try {  
    		        	  doc.remove(position, 1);
    		            } catch (Exception exc) 
    		                    {
    		            	      System.out.println("Calculator could not backspace !");
   	        	                  System.out.println(exc.getMessage());
   	        	                  System.out.println(  exc.getLocalizedMessage()  );
    		                    }
    		        characters--;
    		        // append();
    			  }
    	
    }
    
    
    
    
    
    
    
    protected void setMonitor(Controller controller) 
    {
    	
    	
    }*/
}
