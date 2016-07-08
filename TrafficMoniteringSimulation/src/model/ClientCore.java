package model;

import java.awt.geom.Ellipse2D;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import util.HibernateUtil;
import model.Car;
import dao.CarDao;

public class ClientCore implements Runnable {
	

	public ExecutorService threadExecutor;

	public ArrayList<Car> carList;

	public String region;
	private static Random random=new Random();
	
	private RMIServerInterface es;
	
	private String account;
	
	public ClientCore (String region,RMIServerInterface es)
	{
		carList=new ArrayList<Car>();
		this.region=region;
		this.es=es;
		threadExecutor = Executors.newCachedThreadPool();
		threadExecutor.execute(this);
	}
		
	public ClientCore(String region, String account, RMIServerInterface es) 
	{
		this(region,es);
		this.account=account;	
	}

	public Executor getExecutorService() {
		
		return threadExecutor;
	}

	public ArrayList<Car> getCarList()  
	{
		synchronized(carList)
		{
			return carList;
		}	
	}
	
	public void updateCarList() throws RemoteException
	{
		ArrayList<Car> CarList=es.getRegionCarList(region);
		
		carList.clear();

		for(Car b:CarList)
		{
			if(b!=null)
			{
				carList.add(b);
			}
		}	
	}

	public void deleteCars(ArrayList<Car> cars_inCircle) throws RemoteException {


		es.deleteCarsFromRegion(cars_inCircle,region);
	}
	
	public void createKCarsInCircle(Ellipse2D shape) throws RemoteException
	{
		es.createKCarsInCircle_Region(shape,region);
	}

	@Override
	public void run() {

        while (true) {
        	try {
        		
        		synchronized(carList)
        		{
        			updateCarList();
        		}
        		
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // Delay and give other thread a chance
            try {
               Thread.sleep(50);
            } catch (InterruptedException ex) {}
         }
	}

	public void updateCar(Car b) throws RemoteException {
		es.updateCarInRegion(b,region);
	}

	public void execute(Car b) throws RemoteException {
		es.execute(b,region);
	}

	public String getUserName() {
		return account;
	}

	public boolean userlogon(String account) {
		boolean connected = false;
		try {
		connected=es.userLogon(account,region);
		//JOptionPane.showMessageDialog(null,"HERE!");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connected;
	}

	public void setUserName(String account) {
		this.account=account;
	}

	public void IssueTicket(String carLicense, String ticketName) throws RemoteException {
		es.IssueTicket(carLicense,account,region,ticketName);
	}

	public String getNewMessage() throws RemoteException {
		return es.getNewMessage(account);
	}

	public void sendMessage(String newMessage) throws RemoteException {
		es.sendMessage(account,newMessage);
	}

	public void userlogout() throws RemoteException {
		es.userlogout(account,region);
	}


























}
