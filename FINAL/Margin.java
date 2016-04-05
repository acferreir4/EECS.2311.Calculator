

/**  
 *  =================================================== 
 *  Make  margin for a component  
 *  
 *  ===================================================  
 */ 

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Margin extends JPanel 
{
  private JComponent component;
  private int cartesianWidth ; 
  private int cartesianHeight;
  private GraphicalView client;
  private FavouriteButton button;
  public Margin(JComponent comp, int cw, int ch, GraphicalView gv ) 
  {
    
    // ImageIcon ii = new ImageIcon("earth.jpg");
   component = comp;
   cartesianHeight = ch;
   cartesianWidth = cw;
   client = gv;
   button = new FavouriteButton(SwingConstants.EAST, 2,View.GREENISH,Color.black,  View.BRIGHT_GREEN,2);
   buildComponents();
    
    // setSize(400,300);
  }
  
  public void buildComponents()
  {
	  JScrollPane jsp = new JScrollPane(component);
	    jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    //jsp.getHorizontalScrollBar().setVisible(false);
	    //jsp.getVerticalScrollBar().setVisible(false);
	    JLabel[] corners = new JLabel[4]; 
	   
	    
	    for(int i=0;i<4;i++) 
	    {
	      // Jbutton fav = new JButton("FV");
	    	corners[i] = new JLabel();
	      corners[i].setOpaque(true);
	      corners[i].setBorder(BorderFactory.createLineBorder(Color.red, 1));
	      corners[i].setBackground(Color.orange);
	      /* corners[i].setBorder(BorderFactory.createCompoundBorder(
	    	         BorderFactory.createEmptyBorder(2,2,2,2),
	    	        BorderFactory.createLineBorder(Color.red, 1))); */
	    }

	    JLabel rowheader = new JLabel() 
	    {
	      Font f = new Font("Serif",Font.ITALIC | Font.BOLD,10);
	      public void paintComponent(Graphics g) 
	      {
	        super.paintComponent(g);
	        Rectangle r = g.getClipBounds();
	        g.setFont(f);
	        g.setColor(Color.yellow);
	        for (int i = 30-(r.y % 30);i<=r.height;i+=30) 
	        {
	          g.drawLine(24, r.y + i, 26, r.y + i);
	          g.drawString("" + ((r.height/2)-(r.y + i)), 4, r.y + i + 3);
	        }
	      }

	       public Dimension getPreferredSize() // setting the preferred size for the JLabel
	       {
	        return new Dimension(27,(int)component.getPreferredSize().getHeight());
	       } 
	    };
	    rowheader.setBackground(View.ADAPTIVE);
	    rowheader.setOpaque(true);
	    

	    JLabel columnheader = new JLabel() {
	      Font f = new Font("Serif",Font.ITALIC | Font.BOLD,10);
	      public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Rectangle r = g.getClipBounds();
	        g.setFont(f);
	        g.setColor(View.BRIGHT_GREEN);
	        for (int i = 30-(r.x % 30);i<=r.width;i+=30)
	        {
	          g.drawLine(r.x + i, 0, r.x + i, 3);
	          g.drawString("" +( ((r.x + i)/10)-(cartesianWidth/2)), r.x + i - 3, 16); //g.drawString("" + (r.x + i)/10, r.x + i - 10, 16);
	        }
	      }

	      public Dimension getPreferredSize() { // setting the preferred size for the JLabel
	        return new Dimension((int)component.getPreferredSize().getWidth(),23);  // the first argument is the width that the ScrollPaneLayout would have used for the column-header
	      } 
	    };
	    columnheader.setBackground(View.ADAPTIVE);
	    columnheader.setOpaque(true);
	    button.addMouseListener
        (   new MouseAdapter()
          {
          
           
           @Override
           public void mouseReleased(MouseEvent e) 
           {
                clicked();
           } 
          }                              
        );

	    jsp.setRowHeaderView(rowheader);
	    jsp.setColumnHeaderView(columnheader);
	    jsp.setCorner(JScrollPane.LOWER_LEFT_CORNER, corners[0]);
	    jsp.setCorner(JScrollPane.LOWER_RIGHT_CORNER, corners[1]);
	    jsp.setCorner(JScrollPane.UPPER_LEFT_CORNER,button );
	    jsp.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corners[3]);

	    add(jsp); 
  }
  private void clicked()
  {  System.out.println("clicked");
	  client.addFavourite();
  }
  
}