/** 
 *    
 *  =================================================== 
 *  curved corner border   
 *  =================================================== 
 * 
 */ 

import java.awt.*;

import javax.swing.border.*;

public class CustomBorder implements Border 
{
  protected int m_w=6;
  protected int m_h=6;
  protected Color m_topColor = Color.white;
  protected Color m_bottomColor = Color.white;

  /**
   * Constructs a border
   * with default values of 6 
   * for width and height
   */
  public CustomBorder() 
  {
    m_w=6;
    m_h=6;
  }

  /**
   * Constructs a border with
   * given width and height
   * @param w The width
   * @param h The height
   */
  public CustomBorder(int w, int h) 
  {
    m_w=w;
    m_h=h;
  }

  /**
   * Constructs a border with given width,
   * height, and color
   * @param w The width
   * @param h The height
   * @param topColor The color at the top border
   * @param bottomColor The color at the bottom of the border
   */
  public CustomBorder(int w, int h, Color topColor,  Color bottomColor) 
  {
    m_w=w;
    m_h=h;
    m_topColor = topColor;
    m_bottomColor = bottomColor;
  }

  public Insets getBorderInsets(Component c) 
  {
    return new Insets(m_h, m_w, m_h, m_w);
  }

  /**
   * Determine if border is opaque or not
   */
  public boolean isBorderOpaque() { return true; }

  /**
   * Paints the border to the drawable area
   */
  public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) 
  {
    w--;
    h--;
    g.setColor(m_topColor);
    g.drawLine(x, y+h-m_h, x, y+m_h);
    g.drawArc(x, y, 2*m_w, 2*m_h, 180, -90);
    g.drawLine(x+m_w, y, x+w-m_w, y);
    g.drawArc(x+w-2*m_w, y, 2*m_w, 2*m_h, 90, -90);

    g.setColor(m_bottomColor);
    g.drawLine(x+w, y+m_h, x+w, y+h-m_h);
    g.drawArc(x+w-2*m_w, y+h-2*m_h, 2*m_w, 2*m_h, 0, -90);
    g.drawLine(x+m_w, y+h, x+w-m_w, y+h);
    g.drawArc(x, y+h-2*m_h, 2*m_w, 2*m_h, -90, -90);
  }

  /*  This part was only for testing by Yari
  public static void main(String[] args) {
    JFrame frame = new JFrame("Custom Border: OvalBorder");
    JLabel label = new JLabel("OvalBorder");
    ((JPanel) frame.getContentPane()).setBorder(new CompoundBorder(
      new EmptyBorder(10,10,10,10), new OvalBorder(10,10)));
    frame.getContentPane().add(label);
    frame.setBounds(0,0,300,150);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 1.3  radial 
    frame.setVisible(true);
  } */
}
