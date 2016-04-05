
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.util.ArrayList;
import javax.swing.JLabel;
// import GraphicalView.Curve;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class GraphicalView extends JPanel // implements ActionListener
{
    
  
    protected GraphicalViewController monitor;
    
    
    
    
    private View parentView;
    private LabelPanel label;
    // private JButton favouriteButton ;
    private ForwardButton forward;
    private BackButton back;
    private GraphPanel graph; 
    private SubViewButton calcButton;
	 private SubViewButton favButton;
	 private SubViewButton saveButton;
	 
   
    
    public GraphicalView(View parent) 
    {
    	
   	 
   		    parentView = parent;
   			setStyle();
   			setBackground(View.BACKGROUND);
   			
   		     // setLayout(new BoxLayout( this, BoxLayout.Y_AXIS));
   		    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
   		
   			buildComponents();
   			//observe()
   			setVisible(true);
   	 
        
        
        
    }
    public void setMonitor(GraphicalViewController ctrl) 
    {
    	monitor = ctrl;
    	
    }
    public void resetGraph(ArrayList<Expression> exp,boolean draw) 
    {
    	
    	graph.reset(exp,draw);
    	
    	
    }
    public void setLabel(String lbl) 
    {
    	label.changeLabel(lbl);
    	
    }
    
    private  void buildComponents()
    { 
    	// add(new JLabel("Graphical view !"));
    	label = new LabelPanel("Graphical View");
    	add(label.getPanel());
    	graph = new GraphPanel(true,this,24,240);
    	graph.setPreferredSize(new Dimension(240, 240));
    	Margin ruler = new Margin(graph,24,240,this);
    	ruler.setBackground(View.BACKGROUND);
    	// this.setBorder(BorderFactory.createLineBorder(Color.orange, 1));
    	// ruler.setBorder(new CompoundBorder(  new EmptyBorder(11,13,9,13), BorderFactory.createLineBorder(Color.orange, 1)));
    	add(ruler);
    	addButtons();
    	
    }
    
    private void addButtons()
    {
    	Dimension buttonDimension = new Dimension(112, 27);
    	
    	/*favouriteButton = new JButton("favourite");
        favouriteButton.setFont(CalculatorView.BUTTON_FONT);
        
        
	    
        favouriteButton.setMinimumSize(buttonDimension); 
        favouriteButton.setMaximumSize(buttonDimension);
	        		        
	        
        favouriteButton.setForeground(new Color(0,0,0));  //Set JButton text color
        favouriteButton.setBackground(View.BRONZE);	
        favouriteButton.addActionListener(this); */
    	
        
    	calcButton =  new SubViewButton(SwingConstants.WEST, 8,View.GREENISH,Color.black,  View.BRIGHT_GREEN,7,"calculator");
        
        calcButton.addMouseListener
        (   new MouseAdapter()
          {
          
           
           @Override
           public void mouseReleased(MouseEvent e) 
           {
        	   goPreviousView() ;
           } 
          }                              
        );
        
        favButton =  new SubViewButton(SwingConstants.WEST, 8,View.GREENISH,Color.black,  View.BRIGHT_GREEN,6,"favourite");
        
        favButton.addMouseListener
        (   new MouseAdapter()
          {
          
           
           @Override
           public void mouseReleased(MouseEvent e) 
           {
        	   goNextView() ;
           } 
          }                              
        );
        
        saveButton =  new SubViewButton(SwingConstants.WEST, 8,View.GREENISH,Color.black,  View.BRIGHT_GREEN,3,"save");
        
        saveButton.addMouseListener
        (   new MouseAdapter()
          {
          
           
           @Override
           public void mouseReleased(MouseEvent e) 
           {
        	   addFavourite();
           } 
          }                              
        ); 
        
    	// adding xButton and forward button 
        Box box =  Box.createHorizontalBox();
        box.setBackground(View.BACKGROUND);
         
	     // box.setBorder(BorderFactory.createEmptyBorder(1,3,1,3) );
        
         
        // adding <--
        box.add(calcButton); 
        box.add(Box.createRigidArea(new Dimension(44, 0)));  // add gap between <- and fav
        
        box.add(saveButton); 
        box.add(Box.createRigidArea(new Dimension(57, 0))); 
        
        // adding favButton
        // box.add(favouriteButton); 
       // box.add(Box.createRigidArea(new Dimension(34, 0)));  // add gap between fav and -->
        
        
        
        box.add(  favButton  );  // box.add(Box.createRigidArea(new Dimension(19, 12)));
        
       
         JPanel lastRow = new JPanel(new GridLayout(1,1, 0,0) );// FlowLayout(FlowLayout.LEFT,17,0));  // the panel to hold xButton and forward button
         lastRow.setBackground(View.BACKGROUND);
         lastRow.setBorder(BorderFactory.createEmptyBorder(0,18,5,0) );
         lastRow.add(box); 
         add(lastRow);
    }
    private void goNextView() 
    {
    	Message msg = new Message();
    	msg.setContent("forward") ;
        
        msg.setSource("forward") ;
        
        monitor.inform(msg);    
        
    }
    private void goPreviousView() 
    {
    	Message msg = new Message();
    	msg.setContent("back") ;
        
        msg.setSource("back") ;
        
        monitor.inform(msg);    
        	
        
    }
    private final void setStyle()
    {
        try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
        } catch(Exception exc) { 
        	                    System.out.println(" Could not plug look and feel ! daaamn !");  
        	                    System.out.println(exc.getMessage());
 	        	                System.out.println(  exc.getLocalizedMessage()  );
                             }
    }
   
    public void addFavourite()
    {
    	System.out.println("in gv");
    	Message msg = new Message();
    	msg.setContent("addfavourite") ;
        
        msg.setSource("favouriteButton") ;
        
        monitor.inform(msg);
    }
    /*public void actionPerformed(ActionEvent evnt)
    {
    	if(evnt.getSource() == favouriteButton)  // fav button
        { 
    		Message msg = new Message();
        	msg.setContent("favourite") ;
            
            msg.setSource("favourite") ;
            
            monitor.inform(msg);    
        }
    }*/
  
}
