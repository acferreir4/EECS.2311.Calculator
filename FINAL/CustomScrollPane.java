import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;



public class CustomScrollPane extends JPanel
{
	protected JScrollBar m_vertSB;
	protected JScrollBar m_horzSB;
	protected CustomViewport m_viewport;
	protected JComponent m_comp;
	private final Color BACKGROUND = new Color(102,108,63,200);
	

	public CustomScrollPane(JComponent comp) 
	{
		if (comp == null)throw new IllegalArgumentException("Component cannot be null");

		setLayout(null);
		setBackground(BACKGROUND);
		m_viewport = new CustomViewport();
		m_viewport.setLayout(null);
		add(m_viewport);
		m_comp = comp;
		m_viewport.add(m_comp);

		// m_vertSB = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 0);
		m_vertSB = new JScrollBar(Adjustable.VERTICAL) 
        {
          @Override public void updateUI() {
            //super.updateUI();
            setUI(new CustomScrollBarUI());
          }
          @Override public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            d.width = 5;
            return d;
          }
        };
		m_vertSB.setUnitIncrement(0);
		add(m_vertSB);

		
		
		
		// m_horzSB = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 0);
		m_horzSB = new JScrollBar(Adjustable.HORIZONTAL) 
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
		
		
		m_horzSB.setUnitIncrement(0);
    	add(m_horzSB);

		AdjustmentListener lst = new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				m_viewport.doLayout();
			}
		};
		m_vertSB.addAdjustmentListener(lst);
		m_horzSB.addAdjustmentListener(lst);		
  }
	
	
	
	public void doLayout() {
		Dimension d = getSize();
		Dimension d0 = m_comp.getPreferredSize();
		Dimension d1 = m_vertSB.getPreferredSize();
		Dimension d2 = m_horzSB.getPreferredSize();

		int w = Math.max(d.width - d1.width-1, 0);
		int h = Math.max(d.height - d2.height-1, 0);
		m_viewport.setBounds(0, 0, w, h);

		int btW = d1.width;
		int btH = d2.height;
		
		m_vertSB.setBounds(w+2, 0, btW, h);
		m_horzSB.setBounds(0, h+2, w, btH);
		

		int xs = Math.max(d0.width - w, 0);
		m_horzSB.setMaximum(d0.width);
		// m_horzSB.setMaximum(xs);
		m_horzSB.setBlockIncrement(0);  // disabling block jump
		//m_horzSB.setBlockIncrement(xs/5);  // if clicked in paging area, then thumb will move 1/5 th of the track
		// m_horzSB.setEnabled(xs > 0);
		m_horzSB.setVisible(xs > 0);
		
		int ys = Math.max(d0.height - h, 0);
		m_vertSB.setMaximum(d0.height);
		m_vertSB.setBlockIncrement(0);
		// m_vertSB.setEnabled(ys > 0);
		m_vertSB.setVisible(ys > 0);
		
		m_horzSB.setVisibleAmount(w);
        //m_horzSB.setVisibleAmount(m_horzSB.getBlockIncrement());
        m_vertSB.setVisibleAmount(h);
	}

	public Dimension getPreferredSize() {
		Dimension d0 = m_comp.getPreferredSize();
		Dimension d1 = m_vertSB.getPreferredSize();
		Dimension d2 = m_horzSB.getPreferredSize();
		Dimension d = new Dimension(d0.width+d1.width,
			d0.height+d2.height);
		return d;
	}

	class CustomViewport
		extends JPanel {

		public void doLayout() {
			Dimension d0 = m_comp.getPreferredSize();
			int x = m_horzSB.getValue();
			int y = m_vertSB.getValue();
			m_comp.setBounds(-x, -y, d0.width, d0.height);
		}
	}
	
}
