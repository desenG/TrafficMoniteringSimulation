package view;
//Fig. 25.11: DesktopFrame.java
//Demonstrating JDesktopPane.
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;



import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import view.ClientView.ChatPanel.sendMessageButtonListener;
import model.Car;
import model.ServerCore;



public class ServerView extends JFrame 
{
	
	public ServerCore myCore;
	private JDesktopPane theDesktop;
	

        JTextArea chatBox;


	public ServerView()
	{
		super( "Using a JDesktopPane" );
		setSize(1200, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

        // set up GUI
        public ServerView(final ServerCore myCore)
        {
           this();

           this.myCore=myCore;

           theDesktop = new JDesktopPane(); // create desktop pane
           add( theDesktop ); // add desktop pane to frame
           JMenuBar bar = new JMenuBar(); // create menu bar
           setJMenuBar( bar ); // set menu bar for this application

           JMenu addMenu = new JMenu( "Add" ); // create Add menu   
           bar.add( addMenu ); // add Add menu to menu bar

           JMenuItem addLeftMonitorItem = new JMenuItem( "addLeftMonitor" );
           addMenu.add( addLeftMonitorItem ); // add new frame item to Add menu 
           // set up listener for newFrame menu item
           addLeftMonitorItem.addActionListener(

              new ActionListener() // anonymous inner class
              {  
                 // display new internal window
                 public void actionPerformed( ActionEvent event ) 
                 {
                    // create internal frame
                    JInternalFrame frame = new JInternalFrame( 
                       "Left Monitor", true, true, true, true );
                    frame.setResizable( false );

                    CarPanel panel = new CarPanel("LEFT"); // create new panel
                    myCore.getExecutorService().execute(panel);
                    frame.add( panel, BorderLayout.CENTER ); // add panel
                    frame.pack(); // set internal frame to size of contents

                    theDesktop.add( frame ); // attach internal frame
                    frame.setVisible( true ); // show internal frame
                 } // end method actionPerformed
              } // end anonymous inner class
           ); // end call to addActionListener



           JMenuItem addCenterMonitorItem = new JMenuItem( "addCenterMonitor" );
           addMenu.add( addCenterMonitorItem ); // add new frame item to Add menu 
           // set up listener for newFrame menu item
           addCenterMonitorItem.addActionListener(

              new ActionListener() // anonymous inner class
              {  
                 // display new internal window
                 public void actionPerformed( ActionEvent event ) 
                 {
                    // create internal frame
                    JInternalFrame frame = new JInternalFrame( 
                       "Center Monitor", true, true, true, true );
                    frame.setResizable( false );

                    CarPanel panel = new CarPanel("CENTER"); // create new panel
                    myCore.getExecutorService().execute(panel);
                    frame.add( panel, BorderLayout.CENTER ); // add panel
                    frame.pack(); // set internal frame to size of contents

                    theDesktop.add( frame ); // attach internal frame
                    frame.setVisible( true ); // show internal frame
                 } // end method actionPerformed
              } // end anonymous inner class
           ); // end call to addActionListener




           JMenuItem addRightMonitorItem = new JMenuItem( "addRightMonitor" );
           addMenu.add( addRightMonitorItem ); // add new frame item to Add menu 
           // set up listener for newFrame menu item
           addRightMonitorItem.addActionListener(

              new ActionListener() // anonymous inner class
              {  
                 // display new internal window
                 public void actionPerformed( ActionEvent event ) 
                 {
                    // create internal frame
                    JInternalFrame frame = new JInternalFrame( 
                       "Right Monitor", true, true, true, true );
                    frame.setResizable( false );

                    CarPanel panel = new CarPanel("RIGHT"); // create new panel
                    myCore.getExecutorService().execute(panel);
                    frame.add( panel, BorderLayout.CENTER ); // add panel
                    frame.pack(); // set internal frame to size of contents

                    theDesktop.add( frame ); // attach internal frame
                    frame.setVisible( true ); // show internal frame
                 } // end method actionPerformed
              } // end anonymous inner class
           ); // end call to addActionListener
   
   
   
   
   JMenuItem addChatRoomMonitorItem = new JMenuItem( "addChatRoomMonitor" );
   addMenu.add( addChatRoomMonitorItem ); // add new frame item to Add menu 
   // set up listener for newFrame menu item
   addChatRoomMonitorItem.addActionListener(

      new ActionListener() // anonymous inner class
      {  
         // display new internal window
         public void actionPerformed( ActionEvent event ) 
         {
            // create internal frame
            JInternalFrame frame = new JInternalFrame( 
               "Chat Room Monitor", true, true, true, true );
            frame.setResizable( false );

            MessagePanel panel = new MessagePanel(); // create new panel

            myCore.getExecutorService().execute(panel);
            
            frame.add( panel, BorderLayout.CENTER ); // add panel
            frame.setPreferredSize( new Dimension( 350, 330 ) );
            frame.pack(); // set internal frame to size of contents

            theDesktop.add( frame ); // attach internal frame
            frame.setVisible( true ); // show internal frame
         } // end method actionPerformed
      } // end anonymous inner class
   ); // end call to addActionListener
   
   
} // end constructor DesktopFrame

public class MessagePanel extends JPanel implements Runnable
{
//	JTextField messageBox;
//    JTextArea chatBox;
//    JButton sendMessage;
    
	public MessagePanel()
	{
		setLayout(new BorderLayout());
		JPanel southPanel = new JPanel();
		add(BorderLayout.SOUTH, southPanel);
		southPanel.setBackground(Color.BLUE);
        southPanel.setLayout(new GridBagLayout());


        chatBox = new JTextArea();
        chatBox.setEditable(false);
        add(new JScrollPane(chatBox), BorderLayout.CENTER);
        chatBox.setLineWrap(true);
        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.WEST;
        GridBagConstraints right = new GridBagConstraints();
        right.anchor = GridBagConstraints.EAST;
        right.weightx = 2.0;



        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));

        setSize(470, 300);
	}

	@Override
	public void run() {
		 while (true)
	        {    
	
	        	
	

	            
	            String messageString;

				messageString = myCore.getNewMessage();
				if(messageString!=null&&messageString.trim().length()>0)
				{
					
					chatBox.append(messageString);
				}
				
	            //chatBox.append("xxxx\n");
					

	            
	
	            //sleep while waiting to display the next frame of the animation
	            try{
	                Thread.sleep(60);  // wake up roughly 25 frames per second
	            }
	            catch ( InterruptedException exception )
	            {
	                System.out.printf("interrupt\n");
	            }
	        }
		
	}


}


public class CarPanel extends JPanel implements Runnable
{

	   String region;
	   
	    Point startDrag, endDrag;
	    

	    
		public CarPanel() 
		{
			setPreferredSize( new Dimension( 300, 300 ) );
		     this.addMouseListener(new MouseAdapter() {
		         public void mousePressed(MouseEvent e) {
		        	 //////////////
	 	            	int PointedX=e.getX();
		            	int PointedY=e.getY();
		            	final ArrayList<Car>  Cars_Clicked=getCars_Clicked(PointedX,PointedY);
		            	
		            	  if(Cars_Clicked.size()>0)
	 	            	  {
		            		  //JOptionPane.showMessageDialog(null,Cars_Clicked.size());
	 					        for (final Car b : Cars_Clicked)
	 				            {
	 	
	 					        	b.setRun(false);
	 					        	
	 				            }
	 					        
	 					        for (final Car b : Cars_Clicked)
	 				            {
	 	 				           ///////////////
		 				          	String [] regions={"Speed ticket $100",
		 				          						"Running red lignt $200",
		 				          						"Careless driving $300"
		 				          						};
		 				        	String ticket = (String)JOptionPane.showInputDialog(
		 				                    null,
		 				                    "What's the ticket:\n"
		 				                    + "\"Speed, Running red lignt or Careless driving\"",
		 				                    "New Ticket for car "+b.getLicense(),
		 				                    JOptionPane.PLAIN_MESSAGE,
		 				                    null,
		 				                    regions,
		 				                    "LEFT");

		 				        	if(ticket!=null && ticket.length()>0)
		 				        	{
		 			            	
		 				        		//b.setTicket(ticket);
		 				        		//myCore.updateCarInRegion(b, region);

		 				        		
		 				        	}
		 				        	myCore.getExecutorService().execute(b);
		 				        	////////////////////////////


	 				            }

	 	            	  }
		            	
		        	 
		        	 //////////////
		           startDrag = new Point(PointedX, PointedY);
		           endDrag = startDrag;

		           repaint();
		         }

		         public void mouseReleased(MouseEvent e) {

		        	 
			           Ellipse2D shape = makeCircle(startDrag.x, startDrag.y, e.getX(), e.getY()); 
				   		if(shape.getWidth()>20)
				   		{
				   			ArrayList<Car> Cars_inCircle=getCars_inCircle(shape);
					           
					           if(Cars_inCircle.size()>0)
					           {
					        	   myCore.deleteCarsFromRegion(Cars_inCircle, region);	
					        	   //JOptionPane.showMessageDialog(null,Cars_inCircle.size());
					        	  
					           }
					           
					           myCore.createKCarsInCircle_Region(shape,region);
					           

					           
				   		}
				           startDrag = null;
				           endDrag = null;
				   		repaint();
			         }
		         
		       });

		       this.addMouseMotionListener(new MouseMotionAdapter() {
		         public void mouseDragged(MouseEvent e) {
		           endDrag = new Point(e.getX(), e.getY());
		           repaint();
		         }
		       });
			
		}	

		public CarPanel(String region) 
		{
			this();
			this.region=region;
			
		}
		
		////////////////////////
		
		   /**get the cars in currently circle
		    */
			private ArrayList<Car> getCars_inCircle(Ellipse2D circle) {
				// TODO Auto-generated method stub
				ArrayList<Car> cars=new ArrayList<Car>();
				
				
				for (Car iter : myCore.getRegionCarList(region)) 
		    	{
					float x1=iter.getPx();
					float y1=iter.getPy();
					
					float x2=(float) circle.getCenterX();
					float y2=(float) circle.getCenterY();
					
					float distance=(float) Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2));
		        	if(distance<(iter.getSize()/2+circle.getWidth()/2))
		        	{
		        		cars.add(iter);
		        	}
		    	}
       
				
				return cars;
			}
			
			   /**get the cars in currently-clicked position
			    */
				private ArrayList<Car> getCars_Clicked(int pointx,int pointy) {
					// TODO Auto-generated method stub
					ArrayList<Car> cars=new ArrayList<Car>();
			        for (Car b : myCore.getRegionCarList(region))
			        {
			        	float distance=(float) Math.sqrt(Math.pow((pointx-b.getPx()),2)+Math.pow((pointy-b.getPy()),2));
			        	if(distance<b.getSize()/2)
			        	{
			        		cars.add(b);
			        	}

			         }
			        
					
					return cars;
				}
		
		
		
		////////////////////
		
	    private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
	        return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	      }
	      
	      private Ellipse2D.Float makeCircle(int x1, int y1, int x2, int y2) {
	      	Float r=(float) Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2))/2;
	          //return new Ellipse2D.Float(Math.min(x1, x2), Math.min(y1, y2), r, r);
	      	return new Ellipse2D.Float((x1+x2)/2-r, (y1+y2)/2-r, 2*r, 2*r);
	        }
		
	    private void paintBackground(Graphics2D g2){
	        g2.setPaint(Color.LIGHT_GRAY);
	        for (int i = 0; i < getSize().width; i += 10) {
	          Shape line = new Line2D.Float(i, 0, i, getSize().height);
	          g2.draw(line);
	        }

	        for (int i = 0; i < getSize().height; i += 10) {
	          Shape line = new Line2D.Float(0, i, getSize().width, i);
	          g2.draw(line);
	        }

	        
	      }
		///////////////////////
		
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			////////////////
		      Graphics2D g2 = (Graphics2D) g;
		      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		      paintBackground(g2);
		      Color[] colors = { Color.YELLOW, Color.MAGENTA, Color.CYAN , Color.RED, Color.BLUE, Color.PINK};
		      int colorIndex = 0;


		      g2.setStroke(new BasicStroke(2));
		      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));



		      if (startDrag != null && endDrag != null) {
		        //g2.setPaint(Color.LIGHT_GRAY);
		        Shape r = makeCircle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
		        g2.setPaint(Color.RED);
		        g2.draw(r);
		        
		        g2.fill(r);
		      }
		      ////////////

			for (Car iter : myCore.getRegionCarList(region)) 
	    	{
	    		iter.paint(g);
	    	}
			
		
		}

	    public void run()
	    {
	        while (true)
	        {    
	
	        	
	
	            repaint();
	           
					

	            
	
	            //sleep while waiting to display the next frame of the animation
	            try{
	                Thread.sleep(60);  // wake up roughly 25 frames per second
	            }
	            catch ( InterruptedException exception )
	            {
	                System.out.printf("interrupt\n");
	            }
	        }
	    }					
	}
} // end class DesktopFrame



