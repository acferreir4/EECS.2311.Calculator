import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;


public class FavouritesView extends JPanel // implements ActionListener
{
	 private String ff;
	 private int t;
	 private View parentView;
	 private FavouritesViewController monitor;
	 private SubViewButton graphButton;
	 private SubViewButton deleteButton;
	 private FavouriteItem[] items;
	 private ListBox listBox;
	 // private JList list;
	 public FavouritesView(View parent)
	 {
		    parentView = parent;
			setStyle();
			setBackground(View.BACKGROUND);
			
		     // setLayout(new BoxLayout( this, BoxLayout.Y_AXIS));
		   setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		    listBox = new ListBox(this);
		    
		   //FlowLayout f2 = new FlowLayout(FlowLayout.CENTER,3,0);  
	       // this.setLayout(f2); 
	     	    
	     	   this.setBorder(new CompoundBorder(  new EmptyBorder(10,16,11,16), new CustomBorder(1,1)));
		    
			buildComponents();
			// observe();
			setVisible(true);
	 }
	 
	 public  void buildComponents()
	 {  
		 Box holder = Box.createVerticalBox(); 
		 holder.setBackground(View.BACKGROUND);
         FlowLayout f2 = new FlowLayout(FlowLayout.CENTER,3,0);  
           JPanel  listHolder = new JPanel();
           //listHolder.setPreferredSize(new Dimension(200, 240));
           listHolder.setBackground(View.BACKGROUND);
           listHolder.setBorder(BorderFactory.createEmptyBorder(23,0,0,0) );
     	    // bottomPanel.setLayout(f2); 
     	    
           
		 
		 
		 listBox.setPreferredSize(new Dimension(200, 240));
		 // list = listBox.getList(); 
		 CustomScrollPane sp = new CustomScrollPane( listBox );
		 //listBox.add(sp);
           //listBox.setPreferredSize(new Dimension(200, 240));
		listHolder.add(sp);
		holder.add(listHolder);
		 graphButton =  new SubViewButton(SwingConstants.WEST, 8,View.GREENISH,Color.black,  View.BRIGHT_GREEN,4,"Graph");
	        
	        graphButton.addMouseListener
	        (   new MouseAdapter()
	          {
	          
	           
	           @Override
	           public void mouseReleased(MouseEvent e) 
	           {
	                graph();
	           } 
	          }                              
	        );
	        
            deleteButton =  new SubViewButton(SwingConstants.WEST, 8,View.GREENISH,Color.black,  View.BRIGHT_GREEN,5,"remove");
	        
	        deleteButton.addMouseListener
	        (   new MouseAdapter()
	          {
	          
	           
	           @Override
	           public void mouseReleased(MouseEvent e) 
	           {
	                remove();
	           } 
	          }                              
	        );
	        
	        // Box navigation = Box.createHorizontalBox();
	        // navigation.add(back);
	        Box navigation =  Box.createHorizontalBox();
	        navigation.setBackground(View.BACKGROUND);
	        navigation.add(graphButton);
	        navigation.add(Box.createRigidArea(new Dimension(104, 0)));
	        navigation.add(deleteButton);
	       JPanel lastRow = new JPanel(new GridLayout(1,1, 0,0) );// FlowLayout(FlowLayout.LEFT,17,0));  // the panel to hold xButton and forward button
	         lastRow.setBackground(View.BACKGROUND);
	         lastRow.setBorder(BorderFactory.createEmptyBorder(0,33,5,0) );
	         lastRow.add(navigation); 
	        
	        
	        
	        add(holder);
	        add(lastRow);
	 }
	 private void graph() 
	    {
	    	Message msg = new Message();
	    	msg.setContent("graph") ;
	        
	        msg.setSource("graph") ;
	        
	        monitor.inform(msg);    
	        	
	        
	    }
	 
	 
	 
	   
	    protected final void setStyle()
	    {
	        try {
	                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
	        } catch(Exception exc) { 
	        	                    System.out.println(" Could not plug look and feel ! daaamn !");  
	        	                    System.out.println(exc.getMessage());
	 	        	                System.out.println(  exc.getLocalizedMessage()  );
	                             }
	    }
	    public void setMonitor(FavouritesViewController ctrl) 
	    {
	    	monitor = ctrl;
	    	
	    }
	    
	    public void setList(FavouriteItem[] items)
	    {
	    	listBox.setList(items);
	    	//list = listBox.getList();
	    }
	    
	    
	    public void updateListBox()  // deleting in listbox
	    {
	    	listBox.removeSelected();
	    }
	    
	    /* public void select(int id)
	    {
	    	
	    	monitor.select(id);
	    	
	    	/*Message msg = new Message();
	    	msg.setContent("sel") ;
	        
	        msg.setSource("listbox") ;
	        
	        monitor.inform(msg);*/    
	   // } */
	    public void remove()
	    {
	    	Message msg = new Message();
	    	msg.setContent("remove") ;
	        
	        msg.setSource("remove") ;
	        
	        monitor.inform(msg);    
	    }
	    
	    /*public void actionPerformed(ActionEvent evnt)
	    {
	    	  	 
	    }*/
	    

}
