import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class ForwardButton extends CustomButton 
{
	public ForwardButton(int direction, int arrowSize, Color fg, Color bg,Color hg,int tailSize)
	{
		super(direction, arrowSize, fg, bg, hg,tailSize);
	}
	
	protected void paintArrow(Graphics g)  
	  {
	     
	      int tailHeight = middle/2 + 1;
	      int tailWidth = arrowSize*tailProportion ;
	      
	      for (int i = 0; i < tailWidth; i++) g.drawLine(i, middle - tailHeight, i, middle + tailHeight);  // draw tail
	      
	      g.translate(tailWidth, 0);
	     // for (int i = 0; i < arrowSize; i++) g.drawLine(i, middle - i, i, middle + i);

	      int  j = 0;
	      for (int i = arrowSize-1; i >= 0; i--) 
	      {
	        g.drawLine(j, middle - i, j, middle + i);
	        j++;
	      } 
	    
	      g.translate(-tailWidth, 0);
	   
	  }
}

