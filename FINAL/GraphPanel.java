import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

/****************************************************************************
 * This class plots curve of expression between 12 and -12
 ********************************************************************/

    public class GraphPanel extends JPanel
    {
    	 
    	// private final int SCALE_FACTOR = 10 ; // no seperate scaling for x-axis and y-axis becuz of assumption x_scale = y_scale
    	private boolean connect;  // whether to draw  line between dots
    	private boolean draw;
    	private String curve;
        
        private int degree;
        private GraphicalView client;
        private ArrayList<Expression> expr;
        private int cartesianWidth ;  // no seperate scaling for x-axis and y-axis becuz of assumption x_scale = y_scale
        private int cartesianHeight ; 
         // half of total x or y
        
        public GraphPanel( boolean cnct,GraphicalView view, int x, int y )
        {
        	super(true);
        	client = view;
        	cartesianWidth = x;  // no seperate scaling for x-axis and y-axis becuz of assumption x_scale = y_scale
            cartesianHeight = y;
        	  
        	this.setBackground(new Color(0,0,0));
        	connect = cnct;
        	
        	
        	draw = true;
        	
        }
                      
        
        public void setConnection(boolean b)
        {
        	connect = b;       
        }
        private double radian (int deg)
        {
            return ((2*Math.PI)/360.0) * deg;
        }
 
     /*   private int cartesianX(int pixel, int width)  // x-value from pixel to cartesian frame
        {
            return  (int) Math.round( ( ((double)(pixel/width))*SCALE_FACTOR )  - HALF_SCALE);  
        }
        
        private int pixelX(int i, int width)  // x-value from cartesian  to pixel
        {
           
        	int length = HALF_SCALE + i;  // from 0th pixel to some distance
        	return (int) Math.round(  ((double)(length/SCALE_FACTOR))*width );  
        }
        
        private int cartesianY(int pixel, int height)  // y-value from pixel to cartesian frame
        {
            int value =  (int) ((pixel/(double)height)*SCALE_FACTOR)  ;
            if ((value - HALF_SCALE)>=0) value =  HALF_SCALE - value; 
            return value ;
        }
        
        private int pixelY(int length, int halfHeight)  // y-value from cartesian  to pixel
        {
        	return (int) Math.round(  ((double)(length/HALF_SCALE))*halfHeight );
        	
        	
        	/*int length = HALF_SCALE - i;  // from 0th pixel to some distance
        	return (int) ((length/(double)SCALE_FACTOR)*height); */ 
        // } 
        
        public void input(int x , double y, String curvetype)
        {
        	curve = curvetype ;
        	degree = x ; // for sin/cos      	
        	draw = true;
        }
        
        @Override
        public void paintComponent (Graphics g)
        {
        	
           /* double xScaleFactor = W / data.length;   
            
            // vertical scaling
            double yMinimum = -21;  // should use a for loop to find it out
            double yMaximum = 87;   // should use a for loop to find it out
            double yScale = H / (yMaximum-yMinimum);
         
            // actually plotting the graph
            int xPrev=-1, yPrev=-1;
            for (int i=0;i<data.length;i++)
            {
              int x = (int) ( i*xScale );
              int y = (int) ( (data[i] - yMinimum) *yScale );
              y = H-y;  // up-side down
              plot(gr,x,y);
              // join data point with straight line
              if (xPrev!=-1) gr.drawLine(xPrev,yPrev,x,y);
              xPrev=x; yPrev=y;
            }  */
        	
          
            super.paintComponent(g);
            
            int width = getWidth();
            int height = getHeight();
            System.out.println("b4 draw");
           if(draw)
           { System.out.println("nside draw");
            int lastY=-1;
            int lastX=-1;
            int halfCartW = (int) Math.round(cartesianWidth/(double)2.00);
            int halfCartH = (int) Math.round(cartesianHeight/(double)2.00);
            double xScaleFactor = width / cartesianWidth ;   
            double yScaleFactor = height / cartesianHeight ; 
            
            for(int j = 0; j < expr.size(); j++)  // for each selected expression
   		    { 		 
             for (int i=-halfCartW;i<=halfCartW;i++)  // for each point on visible scale, place it in screen coordinate
             {
                
              int pixelX = (int)Math.round( ( (i+halfCartW)*xScaleFactor ) );
              // int y = (int) ( (data[i] - yMinimum) *yScale );
              int y = (int) Math.round( (expr.get(j)).evaluate((double)i));
              // if( ((expr.get(j)).type())==5|| ((expr.get(j)).type())==6) y *= halfCartH;
              int pixelY = (int) Math.round( (y - (-halfCartH))*yScaleFactor ) ;
              pixelY = height-pixelY;  //  flipping over swing origin
              System.out.println("i="+i+" , pixelX="+pixelX+" , y="+y+" pixelY ="+pixelY);
              // join data point with straight line
              
              if (connect && lastX>-1)  g.drawLine(lastX,lastY,pixelX,pixelY);
              else if(!connect && lastX>-1)  g.drawLine(pixelX,pixelY,pixelX,pixelY);
              /*if((i%4) == 0)
              {
           	   g.setColor( View.ADAPTIVE);  
           	   g.drawOval(pixelX-1,  pixelY-2,   3,  3) ;
           	   
              } */
              g.setColor( Color.cyan);
              lastX=pixelX; lastY=pixelY;
             }
   		    } 
           } // draw
            
            
            
            
            g.setColor( Color.orange);
            int  halfX = (int) (width/2.0);
            int  halfY = (int) (height/2.0);
            int gap = 5;
            for (int i=0; i<width; i=i+gap) g.drawLine(i, halfY, i, halfY);  // draw x axis
            for (int i=0; i<height; i=i+gap) g.drawLine(halfX, i, halfX, i);  // draw y axis
             
            g.setColor( Color.red);
            
            
            halfX = (int) ((width)/4.0);
            
            gap = 10;
            
            for (int i=0; i<height; i=i+gap) g.drawLine(halfX, i, halfX, i);  // draw 1/4    
            halfX *= 3;
            for (int i=0; i<height; i=i+gap) g.drawLine(halfX, i, halfX, i);  // draw 1/4  
            
            draw = true;
            // draw = false;
        }
        
        public void reset(ArrayList<Expression> exprs, boolean paint)
        {
        	
        	draw = paint;
        	expr = exprs;
        }
        
    }