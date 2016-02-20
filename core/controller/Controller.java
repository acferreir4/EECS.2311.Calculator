/**
 * Class is extended to create a new controller
*/

public class Controller 
{
	protected Model model;
	// protected View view;
	protected int occurance(String word, String character )
	{
		return  word.length() - word.replace(character, "").length();
		
	}
	public void inform(Message msg){};
	
}
