import java.text.DecimalFormat;
import java.util.StringTokenizer;

/**
 * Formats the output to display on the screen
 * @author Yari Yousefian
 *
 */
public class DisplayFormat
{
	private Double value ;
	private boolean fragmented ;
	private String result ;
	private String integerPart, fraction;
	  
	/**
	 * Constructs a display format with the given
	 * value
	 * @param val The value to format
	 */
	public DisplayFormat(Double val)
	{
		value = val;
	    process();
	}
	   
	/**
	 * Returns the formatted string
	 * @return The fomratted string
	 */
	public String format()
	{		   	       
	    return result;
	}
	   
	/**
	 * Re-proccess the interface
	 * for a given value
	 * @param val
	 */
	public void reset(Double val)
	{
	   value = val;
	   process();
	}
	   
	/**
	 * Manages all steps required to properly format
	 * a string
	 */
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
	   
	/**
	 * Determine if the expression has a decimal place
	 * @return True if the expression has a decimal place
	 */
	public boolean hasFraction()
	{
		return fragmented;
	}
	   
	/**
	 * Returns the decimals from the number
	 * @return The decimals from the given number
	 */
	public String getFraction()
	{
		return fraction;
	}
	 
	/**
	 * Gets the whole number part from the expression number
	 * @return The whole number part
	 */
	public String getIntegerPart()
	{
		return integerPart;
	}
}