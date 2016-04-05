import java.awt.*;

import javax.swing.*;
//import javax.swing.text.AbstractDocument;

import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.text.AbstractDocument;


public class CalculatorView extends JPanel implements ActionListener
{
       
	  
	    
	    public static final Font BUTTON_FONT =  new Font(Font.SANS_SERIF, Font.BOLD ,12) ; 
	    private final String EMPTY = "empty" ;
	    private final String PI = "\u03C0";
	    public static final String[] TRIGONOMETRY = {"Sin(" , "Cos("};
	    private ForwardButton forward;
	    private final String[][] LABELS = { 
                {"7", "4","1", "0"},
                { "8", "5","2","."},
                { "9", "6", "3",  "+/-" },
                { "/", "*", "-", "+"},
                { "sin", "cos",PI, "!","UNDO", "ERASE", "EVAL","X" },
                {"CE", "Enter","Graph"}
                
              } ;
	    
	    /*private final String[][] LABELS = { 
                {"CLEAR", "ENTER","UNDO", "erase", "select", "eval"},
                { "5", "6", "7",  "8", "9"},
                { "0", "1", "2",  "3", "4"},
                { "+", "-", ".", "*",  "/"},
                { "+/-", "!", PI, "sin", "cos"},
                {"X"}
              } ; */
	    
		private Dimension[] buttonDimension ;
		
	    
	    
	    private JButton[][] buttons;
	    /* private JButton[] operatorButton ;
	    private JButton[] digitButton ;
	    private JButton[] instructionButton ; */
	    private JButton xButton ;
	   
	    private int characters;
	    private boolean dot;
	    private int variable;
	    private AppendState append;
	    
	    
	    
	    // private VMC monitor;
	    // private FieldMode fieldmode;
	    /* private final String[] instruction = {"CLEAR", "ENTER","select", "value", "erase"};
	    private final String[] number = { "0", "1", "2", "3", "4", "5", "6", "7","8","9"};
	    private final String[] operator = { "+", "-", "*",  "/"}; */
	    
	    
	    
		protected CalculatorViewController monitor ;
		protected View parentView;
		private JTextField  historyField ;
		private JTextField  valueField ;
		CalculatorView(View parent) 
		{		
			parentView = parent;
			setStyle();
			setBackground(View.BACKGROUND);
			dot = false;
	        variable = 0;
		     // setLayout(new BoxLayout( this, BoxLayout.Y_AXIS));
		    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		    append = new AppendState();
		    append.off();
			buildComponents();
			observe();
			setVisible(true);
		}
		
		
		
		public void setMonitor(CalculatorViewController ctrl) 
	    {
	    	monitor = ctrl;
	    	
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
	    
	   
	    
	    protected void buildComponents()
	    {
	    	createDisplayBox(16);
	    	createButtons();
		    addButtons();
		    
	    	
	    }
	    
	   /* private final String[][] LABELS = { 
                {"7", "4","1", "0"},
                { "8", "5","2","."},
                { "9", "6", "3",  "+/-" },
                { "/", "*", "-", "+"},
                { "sin", "cos",PI, "!","undo", "erase", "eval","X" },
                {"clear", "enter","graph"}
                
              } */
	    
	    private void createButtons()
	    {
	        
	        
	        buttonDimension = new Dimension[2];
	        buttons = new JButton[6][];
	        for(int i = 0; i < 4; i++)buttons[i] = new JButton[4]; // setting the width of eah row
	        buttons[4] = new JButton[8];  // 5th row
	        buttons[5] = new JButton[3];  // last row
	       
	        /* button[0]=new JButton[4];  // op 
	        button[1]=new JButton[10];  // digit
	        button[2]=new JButton[5];  // inst */
	        
	       // xButton = new JButton();
		
	       
		// Color myGreenish = new Color(169,219,128);
		// Color myYellow = new Color(204,204,0);
		
				
	        		
	        buttonDimension[0] = new Dimension(47, 53);  // wide buttons
	        
	        buttonDimension[1] = new Dimension(63, 53);  //   reg buttons
	        // buttonDimension[2] = new Dimension(112, 53);  //   x button 154, 47
	        // int buttonQuantity = 5;
	        // System.out.println("tg="+buttonTags[1][0]);
	        
	        // creating buttons
	        for(int b = 0; b < 5; b++)createRowButtons(buttons[b], buttonDimension[1], b);  // otrher than lat row
	        createRowButtons(buttons[5], buttonDimension[0], 5); // last row
	       
	         
        
	    }
	    
	    private void createRowButtons(JButton[] groupButtons, Dimension buttonDimension, int row)
	    {
	    	
	        
	    	
	    	
	    	
	          for(int i = 0; i <groupButtons.length; i++) 
	          {
	        	   
	        	  groupButtons[i] = new JButton(LABELS[row][i]);
	            // (groupButtons[i]).setText();
	            (groupButtons[i]).setFont(BUTTON_FONT);
	            // if (b==2)(button[b][i]).setBackground(new Color( 169,219,128 ));
	            
		    
		        (groupButtons[i]).setMinimumSize(buttonDimension); 
		        (groupButtons[i]).setMaximumSize(buttonDimension);
		        		        
		        
		        groupButtons[i].setForeground(new Color(0,0,0));  //Set JButton text color
		       (groupButtons[i]).setBackground(View.BRONZE);	
		       
		        if(row==4 && i==7){ (groupButtons[i]).setBackground(View.ORANGE);	// x button
		        }  // x-button
	            // (groupButtons[i]).addActionListener(this);
	          }
	         
	    }
	    
	    private void addButtons()
	    {
	    	
	    	FlowLayout f2 = new FlowLayout(FlowLayout.CENTER,3,0);
	    	FlowLayout f1 = new FlowLayout(FlowLayout.LEFT,3,0);
	    	// adding last row 
	        Box box =  Box.createHorizontalBox();
	        box.setBackground(View.BACKGROUND);
	         
		     box.setBorder(BorderFactory.createEmptyBorder(1,3,1,3) );
	        
	        // adding x button
	        box.add(buttons[4][7]); 
	        box.add(Box.createRigidArea(new Dimension(27, 0)));  // add gap between x and graph
	        
	        
	        
	        // adding gr button
	        box.add(buttons[5][2]); 
	        box.add(Box.createRigidArea(new Dimension(25, 0)));  // add gap between x and arrow
	        
	        // enter
	        box.add(  buttons[5][1]  );  // box.add(Box.createRigidArea(new Dimension(19, 12)));
	        
	       
	         JPanel lastRow = new JPanel( f1);// FlowLayout(FlowLayout.LEFT,17,0));  // new GridLayout(1,1, 0,0)
	         lastRow.setBackground(View.BACKGROUND);
	         lastRow.setBorder(BorderFactory.createEmptyBorder(0,26,5,0) );
	         lastRow.add(box); //, BorderLayout.NORTH */
	        
	        // first row
	       
	         Box box1 =  Box.createHorizontalBox();
		        box1.setBackground(View.BACKGROUND);
		         
			      box1.setBorder(BorderFactory.createEmptyBorder(1,3,1,3) );
		        
		        // adding x button
		        for(int i = 4; i < 7; i++)
		        {
		        box1.add(buttons[4][i]); 
		        box1.add(Box.createRigidArea(new Dimension(3, 0)));  // add gap between x and graph
		        }
		        box1.add(buttons[5][0]); 
		        
		      
		        
		       
		         JPanel firstRow = new JPanel( );// FlowLayout(FlowLayout.LEFT,17,0));  // new GridLayout(1,1, 0,0)
		         firstRow.setBackground(View.BACKGROUND);
		         firstRow.setBorder(BorderFactory.createEmptyBorder(0,16,1,0) );
		         firstRow.add(box1); //, BorderLayout.NORTH */
	         
	         
	         
	         
	         // add(buttonholder) middlepanel;
	         Box buttonHolder = Box.createVerticalBox(); 
	         
	          
	           JPanel  middlePanel = new JPanel(f2); 
	     	    middlePanel.setBackground(View.BACKGROUND);
	     	   middlePanel.setBorder(BorderFactory.createEmptyBorder(8,0,0,0) );
	     	    // bottomPanel.setLayout(f2); 
	     	    
	     	    
		     Box[] boxes = new Box[5];  // 5 columns of buttons
		     for(int i = 0; i < 5; i++) boxes[i] = Box.createVerticalBox(); 
		      
		     
		     
		     // adding the buttons in the 5 columns
		     for(int j = 0; j < 5; j++)for(int i = 0; i < 4; i++)  {  boxes[j].add(buttons[j][i]);  boxes[j].add(Box.createRigidArea(new Dimension(0, 3)));}
		     // adding 5 columns to the bottomPanel
		     for(int i = 0; i < 5; i++)	middlePanel.add(boxes[i]);
		     buttonHolder.add(firstRow);
		     buttonHolder.add(middlePanel);
		     buttonHolder.add(lastRow);
		     
		      buttonHolder.setBorder(new CompoundBorder(  new EmptyBorder(10,16,11,16), new CustomBorder(1,1)));
	        
		     add(buttonHolder);
	    }
	    private void released() 
	    {
	    	Message msg = new Message();
	    	msg.setContent(valueField .getText()) ;
            
            msg.setSource("swap") ;
            if (append.active()) msg.setState("append");
            else msg.setState("replace");
            monitor.inform(msg);    
	        
	    }
	    
	    protected  void observe()
	    {
	    	int[] rowSize = { 4, 4,4,4,8,3 } ;
	    	for(int row = 0; row < 6; row++) for(int column = 0; column <rowSize[row]; column++) (buttons[row][column]).addActionListener(this);
	    		
	    }
	    /* private final String[][] LABELS = { 
               0 {"7", "4","1", "0"},
               1 { "8", "5","2","."},
               2 { "9", "6", "3","+/-" },
               3 { "/", "*", "-", "+"},
               4 { "sin", "cos",PI, "!","undo", "erase", "eval","X" },
               5 {"clear", "enter","graph"}
                
              } */
	    public void actionPerformed(ActionEvent evnt) 
	    {
	    	
	    	Message msg = new Message();
	        
	        if(evnt.getSource() == buttons[1][3])  // dot button
	            { 
	        	 if(!dot)  // view ignores too many dots
	        	 {
	        		  
	              if(append.active())valueField .setText(valueField .getText()+".");
	 			  else 
	 			      { 
	 				    
	 				 valueField .setText("."); 
	 				    append.on();
	 				  }
	              characters++;
	              dot = true;
	              
	        	 }
	            }
	        else if(evnt.getSource() == buttons[4][7])  // X button
            { 
        	 if(variable<=0)  // no x yet
        	 {
        		  
               if(append.active()) valueField .setText(valueField .getText()+"x");            			  
 			   else 
 			      { 
 				    
 				  valueField .setText("x"); 
 				    append.on();
 				  }
               characters++;
               variable++;
               
        	 }
        	 else // atleast one x
        	  {
        		 // String expSign = (variable==1) ? "^" : "" ; // add ^ only when at one x
        		 String text = valueField .getText();
        		 int split = text.indexOf("x");
        		 text = text.substring(0,split+1);        		 
        		 
        		 valueField .setText(text+"^"+(++variable));
        		 
        		 // text = (variable==1) ? text : 
        		// textfield.setText(text.substring(0,(text.length()-1))+expSign+(++variable));
        		 
        	  }
        	 
            }
	        else 
	          {
	        	   boolean done = false;
	        	   for(int row = 0; row < 3; row++)  //  digit buttons
	        	   {
	        		   for(int column = 0; column <4 ; column++)  
		        	   {   
	        		     
	        			 if (evnt.getSource() == buttons[row][column] && !(evnt.getSource() == buttons[1][3]) && !(evnt.getSource() == buttons[2][3])   )
	        		     {        			 
	        			   if(append.active())valueField .setText(valueField .getText()+LABELS[row][column]);
	        			    else {        				    
	        			    	valueField .setText(LABELS[row][column]); 
	        				    append.on();
	        				  }
	                       characters++;
	                       done = true;
	                        break ;
	        		     }
		        	   }
	               }
	        	   
	        	   
	               if(!done)
	               {
	        		     //  other buttons
	               	                 		   
	               		 if( evnt.getSource() == buttons[3][3] )   // +
	               		 {
	               			 
	                          if( characters>0 )   msg.setContent(valueField .getText()) ;
	                          else  msg.setContent(EMPTY) ; 
	                        	 
	                          msg.setSource(LABELS[3][3]) ;
	                          if (append.active()) msg.setState("append");
	                          else msg.setState("replace");
	                          monitor.inform(msg);
	                          
	               		 }
	               		 
	               		 else if (evnt.getSource() == buttons[3][2])  // -
	              		 {
	              			 
	               			if( characters>0 )   msg.setContent(valueField .getText()) ;
	                        else  msg.setContent(EMPTY) ; 
	                      	 
	                        msg.setSource(LABELS[3][2]) ;
	                        if (append.active()) msg.setState("append");
	                        else msg.setState("replace");
	                        monitor.inform(msg);
	                           
	              		 }
	               		
	               		 else if (evnt.getSource() == buttons[3][1])   // *
	              		 {
	              			 
	               			if( characters>0 )   msg.setContent(valueField .getText()) ;
	                        else  msg.setContent(EMPTY) ; 
	                      	 
	                        msg.setSource(LABELS[3][1]) ;
	                        if (append.active()) msg.setState("append");
	                        else msg.setState("replace");
	                        monitor.inform(msg);
	                         
	                         
	              		 }
	               		 else if ( evnt.getSource() == buttons[3][0])   // divide
	              		 {
	              			 
	               			if( characters>0 )   msg.setContent(valueField .getText()) ;
	                        else  msg.setContent(EMPTY) ; 
	                      	 
	                        msg.setSource(LABELS[3][0]) ;
	                        if (append.active()) msg.setState("append");
	                        else msg.setState("replace");
	                        monitor.inform(msg);
	                           
	              		 }
	               		 else if (evnt.getSource() == buttons[5][0])   // clear  
	              		 {              			                         
	                       
	               			if( characters>0 )   msg.setContent(valueField .getText()) ;
	                        else  msg.setContent(EMPTY) ; 
	                      	 
	                        msg.setSource(LABELS[5][0]) ;
	                        if (append.active()) msg.setState("append");
	                        else msg.setState("replace");
	                        monitor.inform(msg);
	                           
	              		 }
	               		 else if (evnt.getSource() == buttons[5][1])   // enter  
	             		 {    
	             		    variable = 0;
	             		      System.out.println("enter");     			                         
	             		   if( characters>0 )   msg.setContent(valueField .getText()) ;
	                       else  msg.setContent(EMPTY) ; 
	                  	 
	                        msg.setSource(LABELS[5][1]) ;
	                         
	                       if (append.active()) msg.setState("append");
	                       else msg.setState("replace");
	                       monitor.inform(msg);
	                       
	                         
	             		 }
	               		else if (evnt.getSource() == buttons[4][6])   // eval  
	             		 {    
	             		
	             		           			                         
	             		   if( characters>0 )   msg.setContent(valueField .getText()) ;
	                       else  msg.setContent(EMPTY) ; 
	                  	 
	                        msg.setSource("eval") ;
	                         
	                       if (append.active()) msg.setState("append");
	                       else msg.setState("replace");
	                       monitor.inform(msg);
	                         
	             		 }
	               		 
	               		else if (evnt.getSource() == buttons[4][5])   // erase 
	           		    {              			                                           	   
	              			
	              			if( characters>0 )   msg.setContent(valueField .getText()) ;
	                        else  msg.setContent(EMPTY) ; 
	                     	 
	                        msg.setSource("erase") ;
	                        if (append.active()) msg.setState("append");
	                        else msg.setState("replace");
	                        monitor.inform(msg);
	           		    }
	               		else if (evnt.getSource() == buttons[4][4])   // undo 
	            		 {              			                                           	   
	               			if( characters==1 ) msg.setContent("expr") ;
	               			else if( characters>0 )   msg.setContent(valueField .getText()) ;
	                        else  msg.setContent(EMPTY) ; 
	                      	 
	                        msg.setSource(LABELS[4][4]) ;
	                        if (append.active()) msg.setState("append");
	                        else msg.setState("replace");
	                        monitor.inform(msg);
	            		 }
	               		else if (evnt.getSource() == buttons[4][0])   // sin 
	           		    {              			                                           	   
	              			if( characters>0 )   msg.setContent(valueField .getText()) ;
	                        else  msg.setContent(EMPTY) ; 
	                     	 
	                        msg.setSource(LABELS[4][0]) ;
	                        if (append.active()) msg.setState("append");
	                        else msg.setState("replace");
	                        monitor.inform(msg);
	           		    }
	               		else if (evnt.getSource() == buttons[4][1])   // cos 
	           		    {              			                                           	   
	              			if( characters>0 )   msg.setContent(valueField .getText()) ;
	                        else  msg.setContent(EMPTY) ; 
	                     	 
	                        msg.setSource(LABELS[4][1]) ;
	                        if (append.active()) msg.setState("append");
	                        else msg.setState("replace");
	                        monitor.inform(msg);
	           		    }
	               		
	               		else if (evnt.getSource() == buttons[4][2])   // pi
	           		    {              			                                           	   
	               			if( characters>0 )   msg.setContent(valueField .getText()) ;
	                        else  msg.setContent(EMPTY) ; 
	                     	 
	                        msg.setSource("Pi") ;
	                        if (append.active()) msg.setState("append");
	                        else msg.setState("replace");
	                        monitor.inform(msg);
	           		    }
	               		else if (evnt.getSource() == buttons[4][3])   // factorial
	           		    {              			                                           	   
	               			if( characters>0 )   msg.setContent(valueField .getText()) ;
	                        else  msg.setContent(EMPTY) ; 
	                     	 
	                        msg.setSource(LABELS[4][3]) ;
	                        if (append.active()) msg.setState("append");
	                        else msg.setState("replace");
	                        monitor.inform(msg);
	           		    }
	               		else if (evnt.getSource() == buttons[2][3])   // +/- 
	           		    {              			                         
	               		  if( characters>0 ) 
	               		  {
	               				
	               		   msg.setContent(valueField .getText()) ;
	                        
	                       msg.setSource("sign") ;
	                       if (append.active()) msg.setState("append");
	                       else msg.setState("replace");
	                       monitor.inform(msg);
	               		  }
	                        
	           		    }
	               		else if (evnt.getSource() == buttons[5][2])   // +/- 
	           		    {              			                         
	               		  released();
	               		  
	                        
	           		    }
	               		
	               }		 
	            
	          }
	            
	    }
	    
	   
	    
	    /*	    
	    public void setFieldMode(boolean currentMode)
	    {
	    	if(currentMode)fieldmode.append();
	    	else fieldmode.replace();
	    } */
	    
	    private void createDisplayBox(int size)
	    {
	    	
		     historyField  = new JTextField(17);
		     historyField .setBackground(new Color(0,0,0));     
	    	 historyField .setForeground(View.COPPER_BRONZE);
	    		
	    	 historyField .setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
	    	 historyField .setEditable(false);
		    
		     
		     historyField.setPreferredSize(new Dimension(279, 50));
		     
		     
		     
		     Color c11 = new Color(86, 86, 86);
		     Color c22 = new Color(192, 192, 192);
		     Color c33 = new Color(204, 204, 204);
		     Border b11 = new BevelBorder(EtchedBorder.RAISED, c33, c11);
		     Border b12 = new MatteBorder(2, 2, 2, 2, Color.blue);
		     Border b13 = new BevelBorder(EtchedBorder.LOWERED, c33, c11);
		     
		     historyField.setBorder(     new CompoundBorder(new CompoundBorder(b11, b12), b13)    );
		     
		     JScrollBar scrollerOne = new JScrollBar(Adjustable.HORIZONTAL) 
 	        {
 	          @Override public void updateUI() {
 	            //super.updateUI();
 	            setUI(new CustomScrollBarUI());
 	          }
 	          @Override public Dimension getPreferredSize() {
 	            Dimension d = super.getPreferredSize();
 	            d.height = 5;
 	            return d;
 	          }
 	        };
 	        
 	        scrollerOne.setModel(historyField .getHorizontalVisibility());
 	        Box box = Box.createVerticalBox();
 	        
 	        box.add(historyField );
 	        box.add(Box.createVerticalStrut(2));
 	        box.add(scrollerOne);
 	        box.add(Box.createVerticalGlue());

 	        
 	        JPanel topPanel = new JPanel(new BorderLayout());  // CHANGED HRER
 	        topPanel.setBorder(BorderFactory.createEmptyBorder(14, 17, 0, 17));  
 	        topPanel.setBackground(View.BACKGROUND);
 	        topPanel.add(box, BorderLayout.NORTH);
 	        add(topPanel);
	    	
	    	
	    	// now add valueField
	    	
	    	
	    	Dimension fieldDimension = new Dimension(279, 55);  // Dimension(279, 70);
	    	 Font fieldFont =  new Font(Font.SANS_SERIF, Font.BOLD, 20) ;
	    	 
	    	 valueField  = new JTextField(size);
	    	 
	    	 valueField .setBackground(new Color(  0,99,0  ));     
	    	 valueField .setForeground(Color.GREEN);
	    		
	    	 valueField .setFont(fieldFont);
	    	 valueField .setEditable(false);
	    	         
	    	 valueField .setPreferredSize(fieldDimension);    
	    	 valueField .setText("Start new calculation");
	    	        
	    	        Color c1 = new Color(86, 86, 86);
	    	       
	    	        Color c3 = new Color(204, 204, 204);
	    	        Border b1 = new BevelBorder(EtchedBorder.RAISED, c3, c3 );  //c1, c3
	    	        Border b2 = new MatteBorder(4, 4, 4, 4, View.GREENISH); // GREENISH / YELLOW_GREEN base -  new Color(204 , 198 , 108)
	    	        Border b3 = new BevelBorder(EtchedBorder.LOWERED, c3, c1);
	    	        
	    	       
	    	        valueField .setBorder(new CompoundBorder(new CompoundBorder( new CompoundBorder(b1, b2), b3),new EmptyBorder (0,3,0,0)  ));
	    	        
	    	        JScrollBar scroller = new JScrollBar(Adjustable.HORIZONTAL) 
	    	        {
	    	          @Override public void updateUI() {
	    	            //super.updateUI();
	    	            setUI(new CustomScrollBarUI());
	    	          }
	    	          @Override public Dimension getPreferredSize() {
	    	            Dimension d = super.getPreferredSize();
	    	            d.height = 5;
	    	            return d;
	    	          }
	    	        };
	    	        
	    	        scroller.setModel(valueField .getHorizontalVisibility());
	    	        Box box2 = Box.createVerticalBox();
	    	        
	    	        box2.add(valueField );
	    	        box2.add(Box.createVerticalStrut(2));
	    	        box2.add(scroller);
	    	        box2.add(Box.createVerticalGlue());

	    	        
	    	        JPanel vPanel = new JPanel(new BorderLayout());  // CHANGED HRER
	    	        vPanel.setBorder(BorderFactory.createEmptyBorder(1, 17, 0, 17));  
	    	        vPanel.setBackground(View.BACKGROUND);
	    	        vPanel.add(box2, BorderLayout.NORTH);
	    	        add(vPanel);  
	    	        
	    	        
	    }
	    public void display(String hs, boolean hb, String vs, boolean vb ) 
	    {
	    	
	    	if(hb)historyField .setText(hs);
	    	if(vb)valueField .setText(vs);	    	
	    	characters = vs.length() ;
	    	append.off();
	    	dot = false;
	    	
	    }
	    
	    
	    
	    public void negate()
	    {  
	    	valueField .setText("-"+valueField .getText());append.off();
	    }
	    public void sin()
	    {  
	    	valueField .setText(TRIGONOMETRY[0]+valueField .getText()+")");append.off();
	    }
	    
	    public void cos()
	    {
	    	valueField .setText(TRIGONOMETRY[1]+valueField .getText()+")");append.off();
	    	
	    }
	    public void factorial()
	    {
	    	valueField .setText("!"+valueField .getText());append.off();
	    	
	    }
	    protected void backspace() 
	    {
	    	
	    			AbstractDocument doc = (AbstractDocument) valueField .getDocument();
	    			  int length = doc.getLength() ;
	    			  if(length>0)
	    			  {
	    		        int position = length - 1;
	    		        if(   (valueField .getText().charAt(position)=='.') ) dot = false;
	    		        if(   (valueField .getText().charAt(position)=='x') ) variable=0;
	    		        try {  
	    		        	  doc.remove(position, 1);
	    		            } catch (Exception exc) 
	    		                    {
	    		            	      System.out.println("Calculator could not backspace !");
	   	        	                  System.out.println(exc.getMessage());
	   	        	                  System.out.println(  exc.getLocalizedMessage()  );
	    		                    }
	    		        characters--;
	    		        
	    			  }
	    	
	    }
	    
	    public void clear() 
	    {
	    	valueField .setText("") ;
	    	historyField .setText("") ;
			  characters = 0;
			  variable = 0;
			 dot = false;
			 append.on();
			 
	    }
	    public void setFieldState(boolean isAppend)
	    {
	    	if(isAppend)append.on();
	    	else append.off();
	    }
	    public boolean append()
	    {
	    	return append.active();
	    	
	    }
	    
	    
	}



