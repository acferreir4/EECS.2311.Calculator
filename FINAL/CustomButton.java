
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.Insets;

	import javax.swing.BorderFactory;
	import javax.swing.JButton;
	import javax.swing.SwingConstants;
	import java.awt.event.MouseListener ;
	import java.awt.event.MouseEvent;
	import javax.swing.JPanel;
	
	public abstract class CustomButton extends JPanel implements MouseListener
	{

		  /** The cardinal direction of the arrow */
		  private int direction;

		  /** height of the arrow-head point*/
		  protected int middle, shrinkFactor,tailProportion;

		  /** The arrow size. */
		  protected int arrowSize;
	      protected Color foreground, highlight;
	      protected boolean down ;
		  public CustomButton(int direction, int arrowSize, Color fg, Color bg,Color hg, int tailWidth) 
		  {
		   // setMargin(new Insets(0, 2, 0, 2));
		    setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
		    this.direction = direction;
		    // this.arrowCount = arrowCount;
		    this.arrowSize = arrowSize;
		    // background = bg;
		    foreground = fg;
		    highlight = hg;
		    down = false;
		    shrinkFactor = 0;
		    tailProportion = Math.max(tailWidth, 1) ;
		    
		    this.setBackground(bg);
		    setBorder(BorderFactory.createLineBorder(Color.yellow, 1));
		    this.addMouseListener(this);
		  }

		  protected abstract void paintArrow(Graphics g) ;
		  
		  /**
		   * Returns the cardinal direction of the arrow(s).
		   * 
		   * @see #setDirection(int)
		   */
		  public int getDirection() {
		    return direction;
		  }

		  /**
		   * Sets the cardinal direction of the arrow(s).
		   * 
		   * @param direction
		   *            the direction of the arrow(s), can be SwingConstants.NORTH,
		   *            SwingConstants.SOUTH, SwingConstants.WEST or
		   *            SwingConstants.EAST
		   * @see #getDirection()
		   */
		  public void setDirection(int direction) {
		    this.direction = direction;
		  }
		  
		  protected void pressed() 
		  {
	  	    down = true;
	  	    shrinkFactor = 2;
	  	  }
		  public void released() 
		  {
	  	    down = false;
	  	    shrinkFactor = 0;
	  	  }



		  /** Returns the arrow size. */
		  public int getArrowSize() {
		    return arrowSize;
		  }

		  /** Sets the arrow size. */
		  public void setArrowSize(int arrowSize) {
		    this.arrowSize = arrowSize;
		  }

		  public Dimension getPreferredSize() {
		    return getMinimumSize();
		  }

		  public Dimension getMinimumSize() {
		    return new Dimension(
		    		
		    		(arrowSize * (tailProportion+1)) + arrowSize 
	                  + getBorder().getBorderInsets(this).left
		              + getBorder().getBorderInsets(this).right - 6, 
		            arrowSize*3
		              + getBorder().getBorderInsets(this).top
		              + getBorder().getBorderInsets(this).bottom -5 - shrinkFactor );
		  }

		  public Dimension getMaximumSize() {
		    return getMinimumSize();
		  }

		  protected void paintComponent(Graphics g) 
		  {
		    // this will paint the background
		       super.paintComponent(g);

		    Color oldColor =  g.getColor();
		    // g.setColor(down ? foreground.brighter()  : foreground);
		     g.setColor(foreground);
		    
		    
		    // paint the arrows
		    int w = getSize().width;
		    int h = getSize().height;
		    
		    // origin at top-left
		   /*  int xc = (w - arrowSize * (direction == SwingConstants.EAST || direction == SwingConstants.WEST ? arrowCount  : 1)   )  // x decrease(move back) == total width -  width of total number of arrows 
		    		/ 2   // bring to half i.e bring it to just to the right/left of the middle ?
	          + arrowSize * (direction == SwingConstants.EAST   || direction == SwingConstants.WEST ? i : 0)  ;  // x increase(move front) == # of arrows  */
		   
		    
		   // xCoord,yCoord = location of arrow head
		   int xCoordinate = arrowSize/3; // (w - arrowSize) / 2 ; // + arrowSize ;
	       int yCoordinate =  (h - arrowSize) / 2 ;
	                 
		    
		    
	        arrowSize = Math.max(arrowSize, 2);  // arrowSize should not be too small
		    middle = (arrowSize / 2) ;  // height of the arrow head point
		    
		    if(!down) g.translate(xCoordinate, yCoordinate);
		    else 
		    	{
		    	  g.setColor(highlight);
		    	  g.translate(xCoordinate+1, yCoordinate+1);
		    	}
	        paintArrow(g);
	        
	        if(down) g.translate(-xCoordinate-1, -yCoordinate-1);
	        else   g.translate(-xCoordinate, -yCoordinate);
	        g.setColor(oldColor);
		 }

		
		  
		  
		  public void mousePressed(MouseEvent e)
		  {
			  pressed();
			  // setBackground(Color.blue);
			  this.revalidate();
			  this.repaint();
		  }
		  public void mouseReleased(MouseEvent e)
		  {
			  released();
			  this.revalidate();
		      this.repaint();
		  }
		  public void mouseClicked(MouseEvent e){ int dummy = 0; }
		  public void mouseEntered(MouseEvent e){ int dummy = 0 ;}
		  public void mouseExited(MouseEvent e){ int dummy = 0 ;}
	}


