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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;



import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

import model.Car;
import model.ClientCore;




public class ClientView extends JFrame 
{
	
	public ClientCore myCore;
	public CarPanel carPanel = new CarPanel();
	public ChatPanel chatPanel = new ChatPanel();
	public String username;
	
	JTextField messageBox;
    JTextArea chatBox;
    JButton sendMessage;

	// set up GUI
	public ClientView()
	{
	   super();
	   //setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	   setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
	   addWindowListener(new CloseWindowListener(this));
	   setPreferredSize( new Dimension( 770, 330 ) );
	   setResizable( false );
       pack(); // set internal frame to size of contents
	   
	}
	public ClientView(ClientCore core)
	{
		this();
		myCore=core;
		username=core.getUserName();
		this.setTitle(username+" in "+ core.region+ " view");		
	   myCore.getExecutorService().execute(carPanel);
       add( carPanel, BorderLayout.CENTER ); // add panel
       add( chatPanel, BorderLayout.WEST );
	}
	
	public class CloseWindowListener extends WindowAdapter {
		
		private ClientView clientView;
		
		public CloseWindowListener(ClientView clientView)
		{
			this.clientView=clientView;
		}
		
	    public void windowClosing(WindowEvent e) {
            int confirm = JOptionPane.showOptionDialog(null,
                    "Are You Sure to Close this Application?",
                    "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == JOptionPane.YES_OPTION) {
            	
            	try {
					myCore.userlogout();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
            	clientView.dispose();
                System.exit(1);
            }

	    }
	}
	
	public class ChatPanel extends JPanel
	{
//		JTextField messageBox;
//	    JTextArea chatBox;
//	    JButton sendMessage;
	    
		public ChatPanel()
		{
			setLayout(new BorderLayout());
			JPanel southPanel = new JPanel();
			add(BorderLayout.SOUTH, southPanel);
			southPanel.setBackground(Color.BLUE);
	        southPanel.setLayout(new GridBagLayout());

	        messageBox = new JTextField(30);
	        sendMessage = new JButton("Send Message");
	        chatBox = new JTextArea();
	        chatBox.setEditable(false);
	        add(new JScrollPane(chatBox), BorderLayout.CENTER);
	        chatBox.setLineWrap(true);
	        GridBagConstraints left = new GridBagConstraints();
	        left.anchor = GridBagConstraints.WEST;
	        GridBagConstraints right = new GridBagConstraints();
	        right.anchor = GridBagConstraints.EAST;
	        right.weightx = 2.0;

	        southPanel.add(messageBox, left);
	        southPanel.add(sendMessage, right);

	        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
	        sendMessage.addActionListener(new sendMessageButtonListener());
	        setSize(470, 300);
		}
		class sendMessageButtonListener implements ActionListener {
	        public void actionPerformed(ActionEvent event) {
	            if (messageBox.getText().length() < 1) {
	                // do nothing 
	            } else if (messageBox.getText().equals("@clear")||messageBox.getText().equals("@clean")) {
	                //chatBox.setText("Cleared all messages\n");
	                chatBox.setText("");
	                messageBox.setText("");
	            } else {
	            	String newMessage=username + " : " + messageBox.getText();
	                chatBox.append("Me :  " + messageBox.getText() + "\n");
	                try {
						myCore.sendMessage(newMessage);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                messageBox.setText("");
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
	 					        	
	 					        	try {
										myCore.updateCar(b);
									} catch (RemoteException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
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
		 				                    "New Ticket car "+b.getLicense(),
		 				                    JOptionPane.PLAIN_MESSAGE,
		 				                    null,
		 				                    regions,
		 				                    "LEFT");

		 				        	if(ticket!=null && ticket.length()>0)
		 				        	{
		 				        		
		 				        		//b.setTicket(ticket);
		 				        		try {
											myCore.IssueTicket(b.getLicense(),ticket);
											
										} catch (RemoteException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}

		 				        		
		 				        	}
		 				        	try {
										myCore.execute(b);
									} catch (RemoteException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
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
					        	   //JOptionPane.showMessageDialog(null,Cars_inCircle.size());
					        	   try 
					        	   {
					        		   myCore.deleteCars(Cars_inCircle);
									} catch (RemoteException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}	
					        	   //JOptionPane.showMessageDialog(null,Cars_inCircle.size());
					        	  
					           }
					           
					           try {
								myCore.createKCarsInCircle(shape);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					           

					           
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
				
				

					for (Car iter : myCore.getCarList()) 
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

						for (Car b : myCore.getCarList())
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

		      

				for (Car iter : myCore.getCarList()) 
				{
					iter.paint(g);
				}

			
		
		}

	    public void run()
	    {
	    	//int i=0;
	        while (true)
	        {    
	
	        	
	
	            repaint();
	            
	            String messageString;
				try {
					messageString = myCore.getNewMessage();
					if(messageString!=null&&messageString.trim().length()>0)
					{
						//JOptionPane.showMessageDialog(null,messageString.trim().length());
						chatBox.append(messageString + "\n");
					}
					
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            
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
	
} 



