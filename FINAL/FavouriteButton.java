import java.awt.Color;
import java.awt.Graphics;

public class FavouriteButton extends CustomButton 
{
	public FavouriteButton(int direction, int arrowSize, Color fg, Color bg,Color hg, int tailWidth)
	{
		super(direction, arrowSize, fg, bg, hg, tailWidth);
	}
	
	protected void paintArrow(Graphics g)  
	  {		  
		g.drawString("FV", 5, 5);		 
	  }
}


