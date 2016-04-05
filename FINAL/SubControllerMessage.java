
public class SubControllerMessage extends Message
{
   private int changeID;
   
   public void setChange(int id)
   {
	   changeID = id;
   }
   
   public int getID()
   {
	   return changeID;
   }
}
