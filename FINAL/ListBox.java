/**
	CheckBoxList
*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
// import javax.swing.plaf.metal.*;
public class ListBox  extends JPanel 
{

	protected JList<FavouriteItem>  list;
	
    public static final Color  GREEN = new Color(170,255,187) ;
    public static final Color  BRONZE = new Color(255,204,153) ;
    // private ArrayList<FavouriteItem> options;
    protected FavouritesView client;
    // private CustomScrollPane sp;
    private DefaultListModel listModel;

    private ArrayList<Integer> selectedIndices;  // = new  ArrayList<int>();

   
    public ListBox(FavouritesView v)
    {
    	// super();
    	client = v;
    	
    	 // FavouriteItem[] initial = new FavouriteItem[1]; 
    	// FavouriteItem[] initial= {new FavouriteItem("public static final Color  GREEN = new Color(170,255,187) ;public static final Color  BRONZE = new Color(255,204,153)",0) };
    	// setListBox( initial,true ) ;
    	// init = true;
    	setBackground(Color.black);
    	listModel = new DefaultListModel();
    	list = new JList(listModel);
    	list.setBackground(Color.black);
		list.setForeground(GREEN);
		// list.setPreferredSize(new Dimension(getWidth(),getHeight()));
		CheckListCellRenderer renderer = new CheckListCellRenderer();
		list.setCellRenderer(renderer);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		CheckListener lst = new CheckListener(this);
		list.addMouseListener(lst);
		selectedIndices = new  ArrayList<Integer>();
        add(list);
    	//pack();
    	 setVisible(true);
    }
	public void setList(FavouriteItem[] options) 
	{
		
		listModel.clear();
		for(int i = 0; i < options.length; i++) listModel.addElement(options[i]);
        
		
		list = new JList(listModel);
		
		
	
		// pack();
		
		revalidate();
	       repaint();
	}
	
	
	/*public void addIndex(int index) 
	{
		selectedIndices.add(index);
	}
	
	public void removeIndex(int elem) // elem = element at some index
	{
		int index = -1;
		for(int i = 0; i < selectedIndices.size(); i++)
		 {
			if(elem ==   selectedIndices.get(i)) {index = i;break;}
		 }
		if(index>=0)listModel.remove(index);
	}*/
	public void removeSelected() 
	{
		
		for (int i = listModel.size()-1; i >=0; i--) 
		  {
			FavouriteItem item = (FavouriteItem)listModel.get(i);
			if(item.isSelected())listModel.removeElementAt(i);
		  }
		
		/*System.out.println("klm length = "+list.getSelectedIndices().length);
		if(list.getSelectedIndices().length > 0) 
		{
		  int[] tmp = list.getSelectedIndices();
		  int[] selectedIndices = list.getSelectedIndices();
		  System.out.println("b4 lm Size = "+listModel.getSize());  
		  for (int i = tmp.length-1; i >=0; i--) 
		  {
		      selectedIndices = list.getSelectedIndices();
		      listModel.removeElementAt(selectedIndices[i]);
		      System.out.println("lm Size = "+listModel.getSize());
		  } // end-for
		} */
		
	    
	}
	public JList getList() 
	{
		return list;
	}

	
}

class CheckListCellRenderer implements ListCellRenderer 
{
     
	protected static Border m_noFocusBorder = new EmptyBorder(1, 1, 1, 1);
   // private Icon icon = new MetalIconFactory.PaletteCloseIcon();
    private JCheckBox checkbox;
	public CheckListCellRenderer() 
	{
		
		checkbox = new JCheckBox();
		// checkbox.setPressedIcon(icon);
		
		checkbox.setOpaque(true);
		checkbox.setBorder(m_noFocusBorder);
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
	{
	
		if (value != null)checkbox.setText(value.toString()); 
		             
		// setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
		 checkbox.setBackground(isSelected ? list.getBackground() : list.getBackground());
		// setForeground(isSelected ? list.getSelectionForeground() : 	list.getForeground());
		checkbox.setForeground(isSelected ? ListBox.BRONZE : list.getForeground() );

		FavouriteItem data = (FavouriteItem)value;
		checkbox.setSelected(data.isSelected());

		checkbox.setFont(list.getFont());
		checkbox.setBorder((cellHasFocus) ? 
			UIManager.getBorder("List.focusCellHighlightBorder")
			 : m_noFocusBorder);

		return checkbox;
	}
}



/* class CheckListCellRenderer extends  JCheckBox implements ListCellRenderer 
{
     
	protected static Border m_noFocusBorder = new EmptyBorder(1, 1, 1, 1);
    private Icon icon = new MetalIconFactory.PaletteCloseIcon();
	public CheckListCellRenderer() 
	{
		
		// super( icon);
		setOpaque(true);
		setBorder(m_noFocusBorder);
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
	{
	
		if (value != null)setText(value.toString()); 
		                   
		// setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
		 setBackground(isSelected ? list.getBackground() : list.getBackground());
		// setForeground(isSelected ? list.getSelectionForeground() : 	list.getForeground());
		setForeground(isSelected ? CheckBoxList.BRONZE : list.getForeground() );

		InstallData data = (InstallData)value;
		setSelected(data.isSelected());

		setFont(list.getFont());
		setBorder((cellHasFocus) ? 
			UIManager.getBorder("List.focusCellHighlightBorder")
			 : m_noFocusBorder);

		return this;
	}
}  */

class CheckListener implements MouseListener
{

	protected ListBox parent;
	protected JList parentList;

	public CheckListener(ListBox theParent) {
		parent = theParent;
		parentList = parent.list;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getX() < 20)  // clicked the check-box
			doCheck();
		
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	
	

	protected void doCheck() 
	{ 
		int index = parentList.getSelectedIndex();
		
		FavouriteItem data = (FavouriteItem)parentList.getModel().getElementAt(index);
		data.invertSelected();
		
		// parent.client.select(data.getID());
		// m_list.revalidate();
		// m_list.setCellRenderer(new CheckListCellRenderer());
		
		parentList.repaint();
		
		
	}
}




