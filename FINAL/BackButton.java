
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class BackButton extends CustomButton 
{
	public BackButton(int direction, int arrowSize, Color fg, Color bg,Color hg, int tailWidth)
	{
		super(direction, arrowSize, fg, bg, hg, tailWidth);
	}
	
	protected void paintArrow(Graphics g)  
	  {
		  int tailHeight = middle/2 + 1;
	      int tailWidth = arrowSize*tailProportion ;
	      
	      for (int i = 0; i < arrowSize; i++) g.drawLine(i, middle - i, i, middle + i);  // head
	      g.translate(arrowSize, 0);
	     // for (int i = 0; i < arrowSize; i++) g.drawLine(i, middle - i, i, middle + i);

	      
	      for (int i = 0; i < tailWidth; i++) g.drawLine(i, middle - tailHeight, i, middle + tailHeight);  // draw tail
	      g.translate(-arrowSize, 0);
		
      
	     
	    
	  }
}
