 public class FavouriteItem
	 {
		 private String item;
		 private int id ;
		 private boolean selected;
		 private boolean remove;
		 public FavouriteItem(String itm,int i)
		 {
			item = itm;
			id = i;
			selected = false;
			remove = false;
		 }
		 
		 @Override
		 public String toString()
		 {
			 return "y = "+item;
		 }
		 public int getID()
		 {
			 return id;
		 }
		 
		 
		 
			

			

			

			public void setSelected(boolean selected) { 
				selected = selected;
			}

			public void invertSelected() { 
				selected = !selected; 
			}

			public boolean isSelected() { 
				return selected; 
			}

			
			public void setRemove(boolean delete) { 
				remove = delete;
			}

			public void invertRemove() { 
				remove = !remove; 
			}

			public boolean isRemove() { 
				return remove; 
			}
		 
		 
		 
		 
	 }
