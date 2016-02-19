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


public class GraphicalView extends View implements ActionListener
{
	
	   
    private Color background = new Color(102,108,63,200);    
    
    private final String EMPTY = "empty" ;
    
    private JPanel topPanel, bottomPanel, graphingPanel, innerPanel;

    private Curve curve;
    
    protected JButton[][] button;
    
    protected JButton dotButton ;
    protected VMC monitor;
    protected Append append;
    protected boolean dot;
    
    protected FlowLayout f1,f11,f2;
    
    
    private String[][] buttonTag = { 
            
                                        { "+", "-", "x",  "/",  "(-)", "!",  "pi"},
                                        { "4", "3", "2", "1", "0", "9", "8", "7","6","5"},
                                        {"CLEAR", "ENTER","select", "value", "undo"},                                                                               
                                        {"sin","cos","rad"}

                                   }; 
    
    GraphicalView() 
    {
        super("Postfix Calculator");
        
        
        setSize(327, 450);  //  size of the frame
        setResizable(false);  // frame cannot be resized
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // to exit the frame when closed
	     // setLayout(new BoxLayout( this, BoxLayout.Y_AXIS));
	    getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	    setStyle();
	    textfield = new JTextField(16);
	    append = new Append();
	    
	    
	    
        
	    
	    
	    
	    f1 = new FlowLayout(FlowLayout.CENTER,3,19);  // for top panel
	    f11 = new FlowLayout(FlowLayout.CENTER,3,0);  // for graphing panel
        f2 = new FlowLayout(FlowLayout.CENTER,2,0);  //  for bottom panel
        
        
       
        
	    characters = 0;
        dot = false;
        
        initialize();
        
        setVisible(true);
    }
    
    private void initialize()
    {
    	Dimension[] buttonDimension = new Dimension[4];
    	buttonDimension[2] = new Dimension(90, 50);  // instructions
        buttonDimension[1] = new Dimension(55, 47);  //  digit
        buttonDimension[0] = new Dimension(55, 47); //   op
        buttonDimension[3] = new Dimension(77, 47);  // trigonometric buttons
    	
    	createButtons(buttonTag,buttonDimension);
    	createTextField();
    	designLayout();
    	
    }
    
    protected void createButtons(String[][] ragged,Dimension[]  buttonDimension)
    {
    	// System.out.println("here ");
    	// System.out.println("passed len = "+ragged.length);
    	Font buttonFont =  new Font(Font.SANS_SERIF, Font.BOLD, 15) ; 
        button = new JButton[ragged.length][];
       // for(int b = 0; b < ragged.length; b++)button[b] = new JButton[ragged[b].length];  
        // System.out.println("hereAftr");
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
        
        
         
         dotButton.setText(".");
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
    	 Font fieldFont =  new Font(Font.SANS_SERIF, Font.BOLD, 20) ;
    	 // Color grn = ;   //  47,79,79
    		textfield.setBackground(new Color(  0,99,0  ));   //    color   pp  
    		textfield.setForeground(Color.GREEN);
    	        // Font fieldFont = new Font("Times new Roman", Font.BOLD, 19);
    	        // field.setFont(fieldFont);
    		textfield.setFont(fieldFont);
    	        textfield.setEditable(false);
    	         // textfield.setComponentOrientation(ComponentOrientation.CENTER);
    	        textfield.setPreferredSize(fieldDimension);   //  textfield.setPreferredSize(fieldDimension); 
    	        
    	        // textfield.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    	        Color c1 = new Color(86, 86, 86);
    	       // Color c2 = new Color(192, 192, 192);
    	        Color c3 = new Color(204, 204, 204);
    	        Border b1 = new BevelBorder(EtchedBorder.RAISED, c3, c3 );  //c1, c3
    	        Border b2 = new MatteBorder(3, 3, 3, 3, new Color(  204 , 198 , 108  ));  // base -   p 204 , 198 , 108
    	        Border b3 = new BevelBorder(EtchedBorder.LOWERED, c3, c1);
    	        
    	        textfield.setBorder(new CompoundBorder(new CompoundBorder(b1, b2), b3));
    }
    
    private void designLayout()
    {
    	 topPanel = new JPanel(); 
	     topPanel.setBackground(background);  
	     topPanel.setLayout(f1);
	     topPanel.add(textfield);
	     add(topPanel);
	     
	     graphingPanel = new JPanel(); 
	     graphingPanel.setBackground(background);  
	     graphingPanel.setLayout(f11);
	     curve = new Curve(true);
	     Dimension graphDimension = new Dimension(279, 50);
	     curve.setPreferredSize(graphDimension);
	     
	     Color c1 = new Color(86, 86, 86);
	     Color c2 = new Color(192, 192, 192);
	     Color c3 = new Color(204, 204, 204);
	     Border b1 = new BevelBorder(EtchedBorder.RAISED, c3, c1);
	     Border b2 = new MatteBorder(2, 2, 2, 2, Color.blue);
	     Border b3 = new BevelBorder(EtchedBorder.LOWERED, c3, c1);
	    
	     curve.setBorder(     new CompoundBorder(new CompoundBorder(b1, b2), b3)    );
	    
	      graphingPanel.add(curve);
	    
	     add(graphingPanel);
	     
	     
        
	     
	     
	     
	     bottomPanel = new JPanel(); 
    	 bottomPanel.setBackground(background);  
    	 bottomPanel.setLayout(f2); 
    	    
    	 Box holder = Box.createVerticalBox();
	     
	     Box[] box = new Box[3];
	     
	     box[0] = Box.createHorizontalBox(); // for op buttons
	     box[0].setBorder(BorderFactory.createEmptyBorder(3,0,3,0) );
	     for(int i = 0; i < 4; i++)  {  box[0].add(button[0][i]);  box[0].add(Box.createRigidArea(new Dimension(0, 4)));}
	     // box[0].setBorder(new CompoundBorder(  new EmptyBorder(3,4,3,4), new CustomBorder(5,5)));
	     
	     box[1] = Box.createHorizontalBox(); // for 3rd group butttons
	     box[1].setBorder(BorderFactory.createEmptyBorder(3,0,3,0) );
	     for(int i = 4; i < 6; i++)  {  box[1].add(button[0][i]);  box[0].add(Box.createRigidArea(new Dimension(0, 4)));}
	     box[1].add(dotButton);
	     // box[1].setBorder(new CompoundBorder(  new EmptyBorder(3,4,3,4), new CustomBorder(5,5)));
	    
	     box[2] = Box.createVerticalBox();   // for instruction buttons
	     box[2].setBorder(BorderFactory.createEmptyBorder(3,0,3,0) );
	     for(int i = 0; i < 5; i++) {   box[2].add(button[2][i]); box[1].add(Box.createRigidArea(new Dimension(0, 4)));}
	     // box[2].setBorder(new CompoundBorder(  new EmptyBorder(3,4,3,4), new CustomBorder(5,5)));
	     
	   
	  // adding op buttonss       
	
	// box[0].add(dotButton);  box[0].add(Box.createRigidArea(new Dimension(0, 4)));
	
	// adding instruction buttons       
	
	
	     innerPanel = new JPanel(); //  for box2
	     innerPanel.setBackground(background); 
		 GridLayout grid = new GridLayout(2,5);  
	     innerPanel.setLayout(grid);
	     for(int i = 0; i < 10; i++)   innerPanel.add(button[1][i]);  // adding number buttonss
	     innerPanel.setBorder(new CompoundBorder(  new EmptyBorder(3,4,3,4), new CustomBorder(7,7)));
	           
	      
	     holder.add(innerPanel); // holder.add(Box.createRigidArea(new Dimension(0, 4)));
	     holder.add(box[0]);  // holder.add(Box.createRigidArea(new Dimension(0, 4)));
	     holder.add(box[1]);
	     
	     
        
    	bottomPanel.add(holder);
        // bottomPanel.add(innerPanel);
        bottomPanel.add(box[2]);
        
       // bottomPanel.setBorder(new CompoundBorder(  new EmptyBorder(10,16,11,16), new CustomBorder(10,10)));
       
	     add(bottomPanel);
	     
	     JPanel trig = new JPanel(); 
    	 trig.setBackground(background);  
    	 trig.setLayout(f11); 
    	 // trig.setBorder( new CustomBorder(10,10));
    	 // trig.setBorder(new CompoundBorder(  new EmptyBorder(3,4,3,4), new CustomBorder(10,10)));
    	 trig.add(button[0][6]);
    	 for(int i = 0; i < 3; i++)   trig.add(button[3][i]);
    	 
    	 add(trig);
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
    
    
    
    
    public void actionPerformed(ActionEvent evnt)
    {
    	SimpleMessage msg = new SimpleMessage();
        boolean found = false;
        if(evnt.getSource() == dotButton)  // dot button
            { 
        	 if(!dot)  // view ignores too many dots
        	 {
        		  
              if(append.on())textfield.setText(textfield.getText()+".");
 			  else 
 			      { 
 				    clear(); 
 				    textfield.setText("."); 
 				    if(!append.on())append.flip();
 				  }
              characters++;
              dot = true;
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
               			 
                          if( characters>0 )   msg.content = textfield.getText() ;
                          else  msg.content = EMPTY ; 
                        	 
                          msg.source = buttonTag[0][0] ;
                          if (append.on()) msg.state = "append";
                          else msg.state = "replace";
                          monitor.inform(msg);
                          
               		 }
               		 
               		 else if (evnt.getSource() == button[0][1])  // -
              		 {
              			 
               			if( characters>0 )   msg.content = textfield.getText() ;
                        else  msg.content = EMPTY ; 
                      	 
                        msg.source = buttonTag[0][1] ;
                        if (append.on()) msg.state = "append";
                        else msg.state = "replace";
                        monitor.inform(msg);
                           
              		 }
               		
               		 else if (evnt.getSource() == button[0][2])   // *
              		 {
              			 
               			if( characters>0 )   msg.content = textfield.getText() ;
                        else  msg.content = EMPTY ; 
                      	 
                        msg.source = buttonTag[0][2] ;
                        if (append.on()) msg.state = "append";
                        else msg.state = "replace";
                        monitor.inform(msg);
                         
                         // clear();  
              		 }
               		 else if ( evnt.getSource() == button[0][3])   // divide
              		 {
              			 
               			if( characters>0 )   msg.content = textfield.getText() ;
                        else  msg.content = EMPTY ; 
                      	 
                        msg.source = buttonTag[0][3] ;
                        if (append.on()) msg.state = "append";
                        else msg.state = "replace";
                        monitor.inform(msg);
                           
              		 }
               		 else if (evnt.getSource() == button[2][0])   // clear  
              		 {              			                         
                       
               			if( characters>0 )   msg.content = textfield.getText() ;
                        else  msg.content = EMPTY ; 
                      	 
                        msg.source = buttonTag[2][0] ;
                        if (append.on()) msg.state = "append";
                        else msg.state = "replace";
                        monitor.inform(msg);
                           
              		 }
               		 else if (evnt.getSource() == button[2][1])   // enter  
             		 {   //  processing = false; //System.out.println("here1"); 
             		
             		           			                         
             		   if( characters>0 )   msg.content = textfield.getText() ;
                       else  msg.content = EMPTY ; 
                  	 
                        msg.source = buttonTag[2][1] ;
                       clear();
                       if (append.on()) msg.state = "append";
                       else msg.state = "replace";
                       monitor.inform(msg);
                         
             		 }
               		 else if (evnt.getSource() == button[2][2])   // select 
             		 {              			                         
                      
               			if( characters>0 )   msg.content = textfield.getText() ;
                        else  msg.content = EMPTY ; 
                      	 
                        msg.source = buttonTag[2][2] ;
                        if (append.on()) msg.state = "append";
                        else msg.state = "replace";
                        monitor.inform(msg);
                          
             		 }
               		 else if (evnt.getSource() == button[2][3])   // value 
            		 {              			                         
                     
               			if( characters>0 )   msg.content = textfield.getText() ;
                        else  msg.content = EMPTY ; 
                      	 
                        msg.source = buttonTag[2][3] ;
                        if (append.on()) msg.state = "append";
                        else msg.state = "replace";
                        monitor.inform(msg);
                       
                         
            		 }
               		else if (evnt.getSource() == button[2][4])   // erase 
            		 {              			                                           	   
               			if( characters==1 ) msg.content = "expr" ;
               			else if( characters>0 )   msg.content = textfield.getText() ;
                        else  msg.content = EMPTY ; 
                      	 
                        msg.source = buttonTag[2][4] ;
                        if (append.on()) msg.state = "append";
                        else msg.state = "replace";
                        monitor.inform(msg);
            		 }
               		else if (evnt.getSource() == button[3][0])   // sin 
           		    {              			                                           	   
              			if( characters>0 )   msg.content = textfield.getText() ;
                       else  msg.content = EMPTY ; 
                     	 
                       msg.source = buttonTag[3][0] ;
                       if (append.on()) msg.state = "append";
                       else msg.state = "replace";
                       monitor.inform(msg);
           		    }
               		else if (evnt.getSource() == button[3][1])   // cos 
           		    {              			                                           	   
              			if( characters>0 )   msg.content = textfield.getText() ;
                       else  msg.content = EMPTY ; 
                     	 
                       msg.source = buttonTag[3][1] ;
                       if (append.on()) msg.state = "append";
                       else msg.state = "replace";
                       monitor.inform(msg);
           		    }
               		else if (evnt.getSource() == button[3][2])   // radian
           		    {              			                                           	   
              			if( characters>0 )   msg.content = textfield.getText() ;
                       else  msg.content = EMPTY ; 
                     	 
                       msg.source = buttonTag[3][2] ;
                       if (append.on()) msg.state = "append";
                       else msg.state = "replace";
                       monitor.inform(msg);
           		    }
               		else if (evnt.getSource() == button[0][6])   // pi
           		    {              			                                           	   
              			if( characters>0 )   msg.content = textfield.getText() ;
                        else  msg.content = EMPTY ; 
                     	 
                        msg.source = buttonTag[0][6] ;
                        if (append.on()) msg.state = "append";
                        else msg.state = "replace";
                        monitor.inform(msg);
           		    }
               		else if (evnt.getSource() == button[0][5])   // factorial
           		    {              			                                           	   
              			if( characters>0 )   msg.content = textfield.getText() ;
                       else  msg.content = EMPTY ; 
                     	 
                       msg.source = buttonTag[0][5] ;
                       if (append.on()) msg.state = "append";
                       else msg.state = "replace";
                       monitor.inform(msg);
           		    }
               		else if (evnt.getSource() == button[0][4])   // +/- 
           		    {              			                         
                    
              			if( characters>0 )   msg.content = textfield.getText() ;
                        else  msg.content = EMPTY ; 
                     	 
                       msg.source = "sign" ;
                       if (append.on()) msg.state = "append";
                       else msg.state = "replace";
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
    	characters = outp.length() ;
    	if(append.on())append.flip();
    	dot = false;
    	// textfield.repaint();
    	// processing = false;
    	
    }
    protected void clear() 
    {System.out.println("clearing textfield");
    	  textfield.setText("") ;
		  characters = 0;
		 dot = false;
		 // append();
    }
    public void setFieldState(boolean currentState)
    {
    	if(currentState)if(!append.on())append.flip();
    	else if(append.on())append.flip();
    }
    
    public void sin(int x, double y)
    {  
    	curve.input(x,y,"sin");
    	 // curve.revalidate();
    	curve.repaint();
    	// graphingPanel.setVisible(true);
    	DisplayFormat f = new DisplayFormat(y);
    	display(f.format());
    }
    
    public void cos(int x, double y)
    {
    	curve.input(x,y,"cos");
    	curve.repaint();
    	 // curve.revalidate();
    	
    	DisplayFormat f = new DisplayFormat(y);
    	display(f.format());
    }
    
   

/****************************************************************************
 * This class plots a sine/cosine curve between -2*PI and 2*PI.
 ********************************************************************/

    private class Curve extends JPanel
    {
    	private final int FOUR_PI = 720 ;
    	private boolean connect;  // whether to draw  line between dots
    	private boolean draw;
    	private String curve;
        private int lastY ;
        private int degree;
 
        
        public Curve( boolean cnct)
        {
        	super(true);
        	
        	// Color myGreenish = new Color(169,219,128);
        	Color test = new Color(0,0,0);  // try out diferent colors to test
        	this.setBackground(test);
        	connect = cnct;
        	
        	lastY = 0;
        	draw = false;
        	
        }
        
        
        /*
        public Curve(String trig, boolean cnct,double x , double y,)
        {
        	// Color myGreenish = new Color(169,219,128);
        	super(true);
        	Color test = new Color(0,0,0);  // try out diferent colors to test
        	this.setBackground(test);
        	connect = cnct;
        	curve = trig;
        	lastY = 0;
        	pointX = rad;
        } */
        
        public void setConnection(boolean b)
        {
        	connect = b;       
        }
        double radian (int deg)
        {
            return ((2*Math.PI)/360.0) * deg;
        }
 
        int convert (int i, int width)  // from viewport to 4pi
        {
            return (int) ((i/(double)width)*FOUR_PI);  
        }
        
        int revert(int i, int width)  // from 4pi to viewport
        {
            return (int) ((i/(double)FOUR_PI)*width);  
        }
        
        public void input(int x , double y, String curvetype)
        {
        	curve = curvetype ;
        	degree = x ;
        	// pointX = (int) Math.round(x);
        	// pointY = (int) Math.round(y);
        	
        	draw = true;
        }
        
        public void paintComponent (Graphics g)
        {
           
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            System.out.println("height = "+height+" width = "+width);
            if(draw)
            {
              g.setColor( Color.cyan);
              for (int i=0; i<width; i++)  // draw the curve
              {
               int y;
               if(curve.equalsIgnoreCase("sin"))  y = (int) Math.round( ( - Math.sin(  radian( convert(i,width)  ) )+1)*(height)/2.0 );  // sin curve
               else  y = (int) Math.round((-Math.cos(radian(convert(i,width) ))+1)*(height)/2.0);  // cos curve
               
               if (connect && i>0) g.drawLine(i-1, lastY, i, y);
               else  g.drawLine(i, y, i, y);
                
               lastY = y;
              }
            }
            
            g.setColor( Color.orange);
            int  halfX = (int) (width/2.0);
            int  halfY = (int) (height/2.0);
            int gap = 5;
            for (int i=0; i<width; i=i+gap) g.drawLine(i, halfY, i, halfY);  // draw x axis
            for (int i=0; i<height; i=i+gap) g.drawLine(halfX, i, halfX, i);  // draw y axis
             
            g.setColor( Color.red);
            halfX /=  2;
            
            gap = 10;
            
            for (int i=0; i<height; i=i+gap) g.drawLine(halfX, i, halfX, i);  // draw -pi    
            halfX *= 3;
            for (int i=0; i<height; i=i+gap) g.drawLine(halfX, i, halfX, i);  // draw pi  
            
            if(draw) // highlight the point location 
            {
               g.setColor( new Color( 255 , 156 , 0 ));  //  153 , 204 , 51 pp   255, 111 , 0  pp 255 , 111 , 96 pp 255 G: 156 B: 0
               
               int x = revert((360 + degree), width);
               
               int y;
               if(curve.equalsIgnoreCase("sin"))  y = (int) Math.round( ( - Math.sin(  radian( degree  ) )+1)*(height)/2.0 );  // sin curve
               else  y = (int) Math.round((-Math.cos(radian( degree ))+1)*(height)/2.0);
           	
               g.drawOval(x-2,  y-2,   4,  4) ;
               
            }
            draw = false;
        }
        
        
        
    }
    
    

    
    
    
}











 
 




