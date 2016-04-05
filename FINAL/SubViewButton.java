import java.awt.Color;
import java.awt.Graphics;

public class SubViewButton extends CustomButton 
{
	private String label;
	public SubViewButton(int direction, int arrowSize, Color fg, Color bg,Color hg, int tailWidth,String lbl)
	{
		super(direction, arrowSize, fg, bg, hg, tailWidth);
		label = lbl;
	}
	
	protected void paintArrow(Graphics g)  
	  {		  
		g.drawString(label, 3, 7);		 
	  }
}


