/*
 *   represents a JPanel with a label inside it
 */



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LabelPanel 
{
	
	       
	      private JLabel label;
	      private JPanel panel;
	      private String text ;
	    		 
	      
	      public LabelPanel(String txt) 
	      {
	          text = txt;
	          
	    	  setLabel(txt);
	          makePanel();
	         
	      }
	       
	      
	      public JPanel getPanel()  // get the result panel
	      {
	    	  return panel;
	      }
	      
	      public void changeLabel(String lbl)
	      {
	    	  setLabel(lbl);
	    	  makePanel();
	      }
	      
	      private void setLabel(String lbl)
	      {
	    	  label = new JLabel(text);
	    	  label.setForeground(Color.orange);
	      }
	     
	      private void makePanel()  
	      {
	    	  panel = new JPanel();
	          panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	          panel.setBackground(Color.black);
	          // panel.setSize(new Dimension(100, 200)); 
	          
	          panel.add(label); // , BorderLayout.CENTER);
	    	  
	    	 
	      }
	  
}
