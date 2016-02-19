/**     
 *  =================================================== 
 *  author :  Yari Yousefian
 *  This class is the user interface   
 *  =================================================== 
 */

import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class DisplayFormat
{
	private Double value ;
	private boolean fragmented ;
	private String result ;
	private String integerPart, fraction;
	  
	public DisplayFormat(Double val)
	{
		value = val;
	    process();
	}
	   
	public String format()
	{		   	       
	    return result;
	}
	   
	public void reset(Double val)
	{
	   value = val;
	   process();
	}
	   
	private void process()
	{
		fragmented = true;
		DecimalFormat df = new DecimalFormat("#.##");
		//  System.out.println("original value = "+value);
		// System.out.println("raw value = "+df.format(value));
	    Double doubleValue =  Double.valueOf(df.format(value));
	    result = Double.toString(doubleValue);
		   
	    // result = df.format(value);
	    //  System.out.println("formatted value = "+result);
		
	    StringTokenizer tokens = new StringTokenizer(result,".");
	    // System.out.println("dot numbers = "+tokens.countTokens());
	    integerPart = tokens.nextToken();
	    fraction = tokens.nextToken();
	    int fractionValue = Integer.parseInt(fraction);
	    
	    if(fractionValue == 0)  // eg: 25.00
	    { 
	    	result = integerPart;  // if 25.00 then make it 25
	    	fragmented = false; 
	    }
	    else if(fractionValue<10) result += "0" ; // show in 2 decimal place
	
	}
	   
	public boolean hasFraction()
	{
		return fragmented;
	}
	   
	public String getFraction()
	{
		return fraction;
	}
	   
	public String getIntegerPart()
	{
		return integerPart;
	}
}