import java.util.StringTokenizer;
import java.util.ArrayList;
public class Controller 
{
	private final String[] ACTIVATION = {"calculator","graph","favourites"};
	protected Model model;
	protected View view;
	private CalculatorViewController subControllerOne;
	private GraphicalViewController subControllerTwo;
	private FavouritesViewController subControllerThree;
	
	public Controller(Model mdl, View v)
	{
		model = mdl ;
		view = v ;
		subControllerOne = new CalculatorViewController(this,model);
		subControllerTwo =  new GraphicalViewController(this,model);
		subControllerThree = new FavouritesViewController(this,model);
		view.attachSubControllers(subControllerOne,subControllerTwo,subControllerThree);
	}
	
	public void swap(int id)
	{ 
		if(id==2)  // graph view
			{
			 
			 if(model.size()==0)
			 {  
				 Expression dummy = new Term(0,1,false);
				 ArrayList<Expression> exp = new  ArrayList<Expression>(1); 
				   exp.add(dummy);
				 view.activateGraph(exp, false);
			 }
			 else{
				   ArrayList<Expression> exp = new  ArrayList<Expression>(1); 
				   exp.add(model.lastExpression());
				   displayGraph(exp);
			     }
			}
		else if(id ==3) // fav view
		{
			view.activateFavourite(model.getFavourites());
		}
		else view.activate(id);
	}
	
	public void displayGraph(ArrayList<Expression> exp)
	{
		
		System.out.println("view called");
		view.activateGraph(exp,true);
		
		
	}
	
	/*public void inform(Message msg)
	{
		if( msg.getContent().equalsIgnoreCase("favourite")) model.addFovourite();
		else if(msg.getContent().equalsIgnoreCase("addFovourite"))
		{
			model.add
		}
	} */
	public int identifier(String id)
	{
		int index = -1;
		
		for(index = 0; index < 3; index++) if(ACTIVATION[index].equalsIgnoreCase(id)) break ;
		
		return index;
	}
	
	
	/*public void accessModel(SubController ctrl) // only subControllers can access its model
	{
		ctrl.
	} */
	
}
