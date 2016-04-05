import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.BoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;



public class CustomScrollBarUI extends BasicScrollBarUI
{
	 private static final Color DEFAULT_COLOR  =   new Color(220, 100, 100);
	 private static final Color DRAGGING_COLOR = new Color(200, 100, 100);
	 private static final Color ROLLOVER_COLOR =  new Color(255, 120, 100);
	  
	 @Override protected JButton createDecreaseButton(int orientation) 
	 {
	    return new DefaultButton();
	 }
	 
	 @Override protected JButton createIncreaseButton(int orientation) 
	 {
	    return new DefaultButton();
	 }
	  
	 @Override protected void paintTrack(Graphics g, JComponent c, Rectangle r) 
	 {
	    Graphics2D graphics = (Graphics2D) g.create();
	    graphics.setPaint(new Color(102,108,63,200));  
	    graphics.fillRect(r.x, r.y, r.width , r.height );
	    graphics.dispose();
	  }
	  @Override protected void paintThumb(Graphics g, JComponent c, Rectangle r) 
	  {
	    JScrollBar sb = (JScrollBar) c;
	    if (!sb.isEnabled()) return;
	  
	    BoundedRangeModel m = sb.getModel();
	    int iv = m.getMaximum() - m.getMinimum() - m.getExtent() - 1; 
	    if (iv > 0) 
	    {
	      Graphics2D g2 = (Graphics2D) g.create();
	      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	      Color color;
	      if (isDragging) {
	        color = DRAGGING_COLOR;
	      } else if (isThumbRollover()) {
	        color = ROLLOVER_COLOR;
	      } else {
	        color = DEFAULT_COLOR;
	      }
	      g2.setPaint(color);
	      g2.fillRect(r.x, r.y, r.width - 1, r.height - 1);
	      g2.dispose();
	    }
	  }
	  
	  private class DefaultButton extends JButton 
	  {
	    private final Dimension DEFAULT_SIZE = new Dimension();
	    @Override public Dimension getPreferredSize() {
	      return DEFAULT_SIZE;
	    }
	  }
}
