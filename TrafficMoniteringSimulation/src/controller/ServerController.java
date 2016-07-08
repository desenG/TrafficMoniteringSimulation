package controller;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;

import model.ServerCore;
import view.ServerView;

public class ServerController {
	ServerCore myServerCore;

	
	public ServerController()
	{
	}
	
	public ServerController(int portNum)
	{
		try {
			myServerCore=new ServerCore();
			ServerView ServerViewWindown = new ServerView(myServerCore); 			
			ServerViewWindown.setVisible(true);
			LocateRegistry.createRegistry(portNum);
			System.out.println( "Registry created" );
			UnicastRemoteObject.exportObject(myServerCore,portNum);
			System.out.println( "Exported" );
			Naming.rebind("//localhost:" + portNum + "/ServerCore", myServerCore);	
		} catch (Exception e) {
			System.out.println("Trouble: " + e);
		}
	}
	
	
   public static void main( String[] args )
   {
        int portNum = 8088;
        if(args.length > 0)
        {
                portNum = Integer.parseInt(args[0]);
        }
	new ServerController(portNum);
   } // end main
}
