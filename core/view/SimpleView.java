/**     
 *  =================================================== 
 *  author :  Yari Yousefian
 *  This class is the user interface   
 *  =================================================== 
 */
 import java.awt.*;

 import javax.swing.*;
// import javax.swing.text.AbstractDocument;

import java.awt.event.*;
import javax.swing.border.*;


public class SimpleView extends View implements ActionListener 
{
	// private static final long serialVersionUID = 1L;
	   
    private final Color background = new Color(102,108,63,200);    
    private final String dot = "." ;
    private final String empty = "empty" ;
    

    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel innerPanel;
    
    protected JButton[][] button;
    /* private JButton[] operatorButton ;
    private JButton[] digitButton ;
    private JButton[] instructionButton ; */
    protected JButton dotButton ;
    protected VMC monitor;
    protected Append append;
    protected boolean fraction;
    
    protected FlowLayout f1,f2;
    
    protected Font buttonFont, fieldFont;
    /* private final String[] instruction = {"CLEAR", "ENTER","select", "value", "erase"};
    private final String[] number = { "0", "1", "2", "3", "4", "5", "6", "7","8","9"};
    private final String[] operator = { "+", "-", "*",  "/"}; */
    
    private String[][] buttonTag = { 
                                     { "+", "-", "x",  "/"},
                                     { "0", "5", "1", "6", "2", "7", "3", "8","4","9"},
                                     {"CLEAR", "ENTER","select", "value", "undo"}
                                   }; 
    
    SimpleView() 
    {
        super("Postfix Calculator");
        
        setSize(327, 377);  //  size of the frame
        setResizable(false);  // frame cannot be resized
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // to exit the frame when closed
	     // setLayout(new BoxLayout( this, BoxLayout.Y_AXIS));
	    getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	    setStyle();
	    textfield = new JTextField(16);
	    append = new Append();

	    
	    f1 = new FlowLayout(FlowLayout.CENTER,0,19);  // for top panel
        f2 = new FlowLayout(FlowLayout.CENTER,7,3);  //  for bottom panel
        
        buttonFont =  new Font(Font.SANS_SERIF, Font.BOLD, 15) ; 
        fieldFont =  new Font(Font.SANS_SERIF, Font.BOLD, 20) ;
        
	    characters = 0;
        fraction = false;
        
        initialize();
        
        setVisible(true);
    }
    
    private void initialize()
    {
    	Dimension[] buttonDimension = new Dimension[3];
    	buttonDimension[2] = new Dimension(90, 50);  // inst
        buttonDimension[1] = new Dimension(55, 47);  //  digit
        buttonDimension[0] = new Dimension(55, 47);  //   op
    	
    	createButtons(buttonTag,buttonDimension);
    	createTextField();
    	addPanels();
    	
    }
    
    protected void createButtons(String[][] ragged,Dimension[]  buttonDimension)
    {
    	System.out.println("here ");
    	System.out.println("passed len = "+ragged.length);
        
        button = new JButton[ragged.length][];
       // for(int b = 0; b < ragged.length; b++)button[b] = new JButton[ragged[b].length];  
        System.out.println("hereAftr");
        dotButton = new JButton();
        
        for(int b = 0; b < ragged.length; b++)
        {
           button[b] = new JButton[ragged[b].length];
          for(int i = 0; i <ragged[b].length; i++) 
          {
        	  
        	  
        	 button[b][i] = new JButton();
            (button[b][i]).setText(buttonTag[b][i]);
            (button[b][i]).setFont(buttonFont);
            // if (b==2)(button[b][i]).setBackground(new Color( 169,219,128 ));
            (button[b][i]).setBackground(myBronze);
	    
	        (button[b][i]).setMinimumSize(buttonDimension[b]); 
	        (button[b][i]).setMaximumSize(buttonDimension[b]);
	        
	        
	       
	       
	        //Set JButton text color
	         button[b][i].setForeground(new Color(0,0,0));  
	        
	    	    
            (button[b][i]).addActionListener(this);
          }
         }
        
        
         
         dotButton.setText(dot);
         dotButton.setFont(buttonFont);
         dotButton.setBackground(myOrange);
         // dotButon.setForeground(Color.GREEN);
    
         dotButton.setMinimumSize(buttonDimension[0]); 
         dotButton.setMaximumSize(buttonDimension[0]);
         // Color color=new Color(204,204,0); // 204,204,0
	        
	        //Set JButton text color
	        dotButton.setForeground(myGreenish);  
    	    
         dotButton.addActionListener(this);
    }
    
    protected void createTextField()
    {
    	 Dimension fieldDimension = new Dimension(279, 70);
    	 // Color grn = ;   //  47,79,79
    		textfield.setBackground(new Color(0,99,0));   //    color 139,0,0
    		textfield.setForeground(Color.GREEN);
    	        // Font fieldFont = new Font("Times new Roman", Font.BOLD, 19);
    	        // field.setFont(fieldFont);
    		textfield.setFont(fieldFont);
    	        textfield.setEditable(false);
    	         // textfield.setComponentOrientation(ComponentOrientation.CENTER);
    	        textfield.setPreferredSize(fieldDimension);   //  textfield.setPreferredSize(fieldDimension); 
    	        // textfield.setBorder(new GraniteBorder(0));
    	        
    	        Color c1 = new Color(86, 86, 86);
    	       // Color c2 = new Color(192, 192, 192);
    	        Color c3 = new Color(204, 204, 204);
    	        Border b1 = new BevelBorder(EtchedBorder.RAISED, c3, c3 );  //c1, c3
    	        Border b2 = new MatteBorder(3, 3, 3, 3, myYellow);  // base
    	        Border b3 = new BevelBorder(EtchedBorder.LOWERED, c3, c1);
    	        
    	        textfield.setBorder(new CompoundBorder(new CompoundBorder(b1, b2), b3));
    }
    
    private void addPanels()
    {
    	topPanel = new JPanel(); 
	     topPanel.setBackground(background);  
	     topPanel.setLayout(f1);
	     
	     
	     
	      topPanel.add(textfield);
	      // topPanel.setBorder(new GraniteBorder(1));
             add(topPanel);
	     
	     
            bottomPanel = new JPanel(); 
    	     bottomPanel.setBackground(background);  
    	     bottomPanel.setLayout(f2); 
    	    
	     
	     Box[] box = new Box[2];
	     box[0] = Box.createVerticalBox(); 
	     box[0].setBorder(BorderFactory.createEmptyBorder(3,0,3,19) );
	     box[1] = Box.createVerticalBox();
	     box[1].setBorder(BorderFactory.createEmptyBorder(3,19,3,0) );
	     
	    
	     
	     
	     
	    
       
       
      
	
	//  adding buttonss to the 4 boxes  
    //   for(int i = 4; i < 10; i++)  { box[2].add(button[i]); box[2].add(Box.createRigidArea(new Dimension(0, 4)));  }
	
	for(int i = 0; i < 4; i++)  {  box[0].add(button[0][i]);  box[0].add(Box.createRigidArea(new Dimension(0, 4)));}
	box[0].add(dotButton);  box[0].add(Box.createRigidArea(new Dimension(0, 4)));
	
	
	for(int i = 0; i < 5; i++) {   box[1].add(button[2][i]); box[1].add(Box.createRigidArea(new Dimension(0, 4)));}
	
	     innerPanel = new JPanel(); //  for box2
	     innerPanel.setBackground(background); 
		 GridLayout grid = new GridLayout(5,2);  
	     innerPanel.setLayout(grid);
	
	     for(int i = 0; i < 10; i++)   innerPanel.add(button[1][i]); 
	     
	  
	     
        bottomPanel.add(box[0]);
        bottomPanel.add(innerPanel);
        bottomPanel.add(box[1]);
        
        bottomPanel.setBorder(new CompoundBorder(  new EmptyBorder(10,16,11,16), new CustomBorder(10,10)));
       
	     add(bottomPanel);
    	
    }
    
    /* private void initialize()
    {
        
        
        
	/*  how components are to be placed/dropped */
         
	
	   //  color for each row --> calc base color   / 68,34,0 /102,108,63 /5,5,5
        
	     
        
        //  setting up buttons
	
	
	
	// new Font("Times new Roman", Font.BOLD, 14);  // font for button text
	
	///  dimensions of the components in frame eg button component  
       
      
        
	//  setting up textfield
	
	//  setting up panels  */
	
             
	
	
        
     
    
    
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
    
    
    public void actionPerformed(ActionEvent evnt) 
    {
    	basicHandler(evnt);
            
    }
    
    protected void basicHandler(ActionEvent evnt)
    {
    	SimpleMessage msg = new SimpleMessage();
        boolean found = false;
        if(evnt.getSource() == dotButton)  // dot button
            { 
        	 if(!fraction)  // view ignores too many dots
        	 {
        		  
              if(append.on())textfield.setText(textfield.getText()+dot);
 			  else 
 			      { 
 				    clear(); 
 				    textfield.setText(dot); 
 				    if(!append.on())append.flip();
 				  }
              characters++;
              fraction = true;
              found = true; 
        	 }
            }
        if(!found) 
          {
        	   for(int i = 0; i < 10; i++)  //  number buttons
        	   {
        		   
        		 if (evnt.getSource() == button[1][i])
        		 {        			 
        			 if(append.on())textfield.setText(textfield.getText()+buttonTag[1][i]);
        			 else {        				    
        				    textfield.setText(buttonTag[1][i]); 
        				    if(!append.on())append.flip();
        				  }
                     characters++;
                     found = true;
                     break ;
        		 }
               }
        	   
        	   
               if(!found)
               {
        		     //  other buttons
               	                 		   
               		 if( evnt.getSource() == button[0][0] )   // +
               		 {
               			 
                          if( characters>0 )   msg.state = textfield.getText() ;
                          else  msg.state = empty ; 
                        	 
                          msg.source = buttonTag[0][0] ;
                          monitor.inform(msg);
                          
               		 }
               		 
               		 else if (evnt.getSource() == button[0][1])  // -
              		 {
              			 
               			if( characters>0 )   msg.state = textfield.getText() ;
                        else  msg.state = empty ; 
                      	 
                        msg.source = buttonTag[0][1] ;
                        monitor.inform(msg);
                           
              		 }
               		
               		 else if (evnt.getSource() == button[0][2])   // *
              		 {
              			 
               			if( characters>0 )   msg.state = textfield.getText() ;
                        else  msg.state = empty ; 
                      	 
                        msg.source = buttonTag[0][2] ;
                        monitor.inform(msg);
                         
                         // clear();  
              		 }
               		 else if ( evnt.getSource() == button[0][3])   // divide
              		 {
              			 
               			if( characters>0 )   msg.state = textfield.getText() ;
                        else  msg.state = empty ; 
                      	 
                        msg.source = buttonTag[0][3] ;
                        monitor.inform(msg);
                           
              		 }
               		 else if (evnt.getSource() == button[2][0])   // clear  
              		 {              			                         
                       
               			if( characters>0 )   msg.state = textfield.getText() ;
                        else  msg.state = empty ; 
                      	 
                        msg.source = buttonTag[2][0] ;
                        monitor.inform(msg);
                           
              		 }
               		 else if (evnt.getSource() == button[2][1])   // enter  
             		 {   //  processing = false; //System.out.println("here1"); 
             		
             		           			                         
             		   if( characters>0 )   msg.state = textfield.getText() ;
                       else  msg.state = empty ; 
                  	 
                        msg.source = buttonTag[2][1] ;
                       clear();
                       monitor.inform(msg);
                         
             		 }
               		 else if (evnt.getSource() == button[2][2])   // select 
             		 {              			                         
                      
               			if( characters>0 )   msg.state = textfield.getText() ;
                        else  msg.state = empty ; 
                      	 
                        msg.source = buttonTag[2][2] ;
                        monitor.inform(msg);
                          
             		 }
               		 else if (evnt.getSource() == button[2][3])   // value 
            		 {              			                         
                     
               			if( characters>0 )   msg.state = textfield.getText() ;
                        else  msg.state = empty ; 
                      	 
                        msg.source = buttonTag[2][3] ;
                        monitor.inform(msg);
                       
                         
            		 }
               		else if (evnt.getSource() == button[2][4])   // erase 
            		 {              			                                           	   
               			if( characters>0 )   msg.state = textfield.getText() ;
                        else  msg.state = empty ; 
                      	 
                        msg.source = buttonTag[2][4] ;
                        monitor.inform(msg);
            		 }
               }		 
            
          }
    }
    
    
    public void setMonitor(VMC vmc) 
    {
    	monitor = vmc;
    	
    }
    
    public void display(String outp) 
    {
    	
    	System.out.println("display output = "+outp);
    	
    	textfield.setText(outp);
    	if(append.on())append.flip();
    	// textfield.repaint();
    	// processing = false;
    	
    }
    protected void clear() 
    {System.out.println("clearing textfield");
    	  textfield.setText("") ;
		  characters = 0;
		  fraction = false;
		 // append();
    }
    public void setFieldMode(boolean currentMode)
    {
    	if(currentMode)if(!append.on())append.flip();
    	else if(append.on())append.flip();
    }
    
}

