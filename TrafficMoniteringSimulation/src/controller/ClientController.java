package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Car;
import model.ClientCore;
import model.RMIServerInterface;
import view.ClientView;
import view.ServerView;

public class ClientController {

	ClientCore core;
	

	
	public ClientController(final String serverName,final int port)
	{
	      //Create array containing shapes  
	      String[] accounts ={"(no account selected)","1","2","3"};  

	      //Use combobox to create drop down menu
	      JComboBox comboBox_accounts=new JComboBox(accounts);
	      JPanel panel_accounts = new JPanel(); 
	      JLabel label_accounts = new JLabel("Select account:");
	      panel_accounts.add(label_accounts);
	      comboBox_accounts.add(panel_accounts);
	      //comboBox_accounts.disable();
	      
	      JButton gobutton = new JButton("GO");
	     // button.disable();
	      


	      
	      final JTextField textForAccout = new JTextField(20);
		
	      //Create array containing shapes  
	      String[] regions ={"(no region selected)","LEFT","CENTER","RIGHT"};  

	      //Use combobox to create drop down menu
	      final JComboBox comboBox_regions=new JComboBox(regions);
	      JPanel panel_regions = new JPanel(); 
	      JLabel label_regions = new JLabel("Select region:");
	      panel_regions.add(label_regions);
	      comboBox_regions.add(panel_regions);
	      
	      comboBox_regions.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
	      
	      gobutton.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	
		    		try {
		    			String region=(String) comboBox_regions.getSelectedItem();
		    			String account=textForAccout.getText();
		    			//JOptionPane.showMessageDialog(null,account);
	
		    			RMIServerInterface es = (RMIServerInterface) 
		    					Naming.lookup("rmi://"+serverName+":"+port+"/ServerCore");
		    			
			    		core = new ClientCore(region,es);
			    		boolean connected=core.userlogon(account);
			    		
//			    		ClientView ClentViewWindown = new ClientView(core);  
//			    		ClentViewWindown.setVisible( true ); // display frame
			    		if(connected)
			    		{
			    			core.setUserName(account);
				    		ClientView ClentViewWindown = new ClientView(core);  
				    		ClentViewWindown.setVisible( true ); // display frame
			    		}
			    		else
			    		{
			    			JOptionPane.showMessageDialog(null,"Account no found!");
			    		}


		    		}
		    		catch (MalformedURLException murle) {
		    			System.out.println();
		    			System.out.println(
		    					"MalformedURLException");
		    			System.out.println(murle);
		    		}
		    		catch (RemoteException re) {
		    			System.out.println();
		    			System.out.println("RemoteException");
		    			System.out.println(re);
		    		}
		    		catch (NotBoundException nbe) {
		    			System.out.println();
		    			System.out.println("NotBoundException");
		    			System.out.println(nbe);
		    		}
		            
		        }
	     });


	      //Create a JFrame that will be use to put JComboBox into it 
	      JFrame frame=new JFrame("Area And Account Window");  
	      frame.setLayout(new FlowLayout()); //set layout
	      frame.add(comboBox_regions);//add combobox to JFrame
	      frame.add(textForAccout);
	      frame.add(gobutton);

	      //set default close operation for JFrame 
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	      //set JFrame ssize 
	      frame.setSize(400,100);
	      
	      frame.setResizable(false);

	      //make JFrame visible. So we can see it 
	      frame.setVisible(true);  
	}
	
	
   public static void main( String[] args )
   { 
		int port = 8088;
		String serverName = new String("localhost");
		

		switch (args.length) {
		case 0:
			break;
		case 1: 
			serverName = args[0];
			break;
		case 2:
			serverName = args[0];
			port = Integer.parseInt(args[1]);
			break;
		default:
			System.out.println("usage: Client [hostname [portnum]]");
			break;
		}
		new ClientController(serverName,port);
   } // end main
}
