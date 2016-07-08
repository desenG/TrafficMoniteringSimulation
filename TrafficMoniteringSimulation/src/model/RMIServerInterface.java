package model;

import java.awt.geom.Ellipse2D;
import java.rmi.Remote;
import java.util.ArrayList;

public interface RMIServerInterface extends Remote
{

	public ArrayList<Car> getRegionCarList(String region) throws java.rmi.RemoteException;

	public void deleteCarsFromRegion(ArrayList<Car> balls_inCircle, String region)throws java.rmi.RemoteException;

	public void createKCarsInCircle_Region(Ellipse2D shape, String region)throws java.rmi.RemoteException;

	public void updateCarInRegion(Car b, String region)throws java.rmi.RemoteException;

	public void execute(Car b, String region)throws java.rmi.RemoteException;

	public boolean userLogon(String account, String region)throws java.rmi.RemoteException;

	public void IssueTicket(String carLicense, String account, String region,
			String ticketName) throws java.rmi.RemoteException;

	public String getNewMessage(String officerID)throws java.rmi.RemoteException;

	public void sendMessage(String account, String newMessage)throws java.rmi.RemoteException;

	public void userlogout(String account, String region)throws java.rmi.RemoteException;

	









}
