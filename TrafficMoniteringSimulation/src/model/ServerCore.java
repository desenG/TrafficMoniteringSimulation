package model;

import java.awt.Component;
import java.awt.geom.Ellipse2D;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import util.HibernateUtil;
import model.Car;
import dao.CarDao;

public class ServerCore implements Runnable,RMIServerInterface 
{
	
	public CarDao dao_LEFT;
	public CarDao dao_CENTER;
	public CarDao dao_RIGHT;
	public ExecutorService threadExecutor;

	public ArrayList<Car> allcarList;
	public ArrayList<Car> leftcarList;
	public ArrayList<Car> centercarList;
	public ArrayList<Car> rightcarList;
	
	
	public ArrayList<Officer> onlineUserList;
	public ArrayList<Officer> onlineUserList_LEFT;
	public ArrayList<Officer> onlineUserList_CENTER;
	public ArrayList<Officer> onlineUserList_RIGHT;
	
	private int number_car_L=0;
	
	private int number_car_C=0;
	
	private int number_car_R=0;
	
	
	private StringBuilder messagebuff;


	
	private ArrayList<MessageBuff> messagebuffs;

	
	
	private static Random random=new Random();
	public ServerCore ()
	{
		
		messagebuff=new StringBuilder();

		
		messagebuffs=new ArrayList<MessageBuff>();
		
		dao_LEFT = new CarDao("LEFT");
		dao_CENTER = new CarDao("CENTER");
		dao_RIGHT = new CarDao("RIGHT");
		onlineUserList=new ArrayList<Officer>();
		
		
		threadExecutor = Executors.newCachedThreadPool();
		
		allcarList = new ArrayList<Car>();
		leftcarList = new ArrayList<Car>();
		centercarList = new ArrayList<Car>();
		rightcarList = new ArrayList<Car>();
		
		deleteAllUsers();

		deleteAllCars();
		
		// Add new users

		int number_users = 1;
		while(number_users<10)
		{
			Officer PoliceL;
			PoliceL = new Officer("Police"+number_users++,"LEFT");			
			dao_LEFT.addUser(PoliceL);

			Officer PoliceC;
			PoliceC = new Officer("Police"+number_users++,"CENTER");			
			dao_CENTER.addUser(PoliceC);
			
			Officer PoliceR;
			PoliceR = new Officer("Police"+number_users++,"RIGHT");			
			dao_RIGHT.addUser(PoliceR);
			
		}
			

		
		// Add new car
		for(int i=0;i<10;i++)
		{
			Car car;
			car = new Car("LEFT","L "+number_car_L++);			
			leftcarList.add(car);
			dao_LEFT.addCar(car);
			allcarList.add(car);
			
			car = new Car("CENTER","C "+number_car_C++);
			centercarList.add(car);
			dao_CENTER.addCar(car);
			allcarList.add(car);
			
			car = new Car("RIGHT","R "+number_car_R++);
			rightcarList.add(car);
			dao_RIGHT.addCar(car);
			allcarList.add(car);
		}
		

		


		// Get all cars
		for (Car iter : allcarList) {
			System.out.println(iter);
		}
		
		// Get all cars
		for (Car iter : allcarList) {

			threadExecutor.execute(iter);			
			
		}	
		
		
		threadExecutor.execute(this);
		
		

	}	
	
	
	public void deleteAllUsers()
	{
		
		dao_LEFT.deleteAllUsers();

		
		dao_CENTER.deleteAllUsers();

		
		dao_RIGHT.deleteAllUsers();


	}
	

	
	public void deleteAllCars()
	{
		
		dao_LEFT.deleteAllCars();
		leftcarList.clear();
		
		dao_CENTER.deleteAllCars();
		centercarList.clear();
		
		dao_RIGHT.deleteAllCars();
		rightcarList.clear();

	}
	
	public void deleteCarFromRegion(Car b, String region)
	{
		CarDao dao;
		ArrayList<Car> regionCarList= new ArrayList<Car>();
		
		switch(region)
		{
			case "LEFT":
				
				dao=dao_LEFT;				
				regionCarList=leftcarList;
				break;
			case "CENTER":
				dao=dao_CENTER;
				regionCarList=centercarList;
				break;
			default://"RIGHT"

				dao=dao_RIGHT;
				regionCarList=rightcarList;
				
		}
		dao.deleteCar(b.getLicense());
		regionCarList.remove(b);

	}
	@Override
	public void deleteCarsFromRegion(ArrayList<Car> cars_inCircle,String region) {
		CarDao dao;
		ArrayList<Car> regionCarList= new ArrayList<Car>();
		
		
		switch(region)
		{
			case "LEFT":
				
				dao=dao_LEFT;				
				regionCarList=leftcarList;
				break;
			case "CENTER":
				dao=dao_CENTER;
				regionCarList=centercarList;
				break;
			default://"RIGHT"

				dao=dao_RIGHT;
				regionCarList=rightcarList;
				
		}	
		
		
		ArrayList<Car> circledList= new ArrayList<Car>();
		circledList.clear();
		synchronized(regionCarList)
		{
			for(Car b_circled:cars_inCircle)
			{
				for(Car b:regionCarList)
				{
					if(b.getLicense().equals(b_circled.getLicense()))
					{
						
						circledList.add(b);

					}
				}
				
				for(Car b:circledList)
				{
					regionCarList.remove(b);
					allcarList.remove(b);
					dao.deleteCar(b.getLicense());					
					
				}
				

				
				

				
			}
		}

		
				
	}
	
	public ArrayList<Car> getAllCarList()
	{
		ArrayList<Car> CarList= new ArrayList<Car>();
		
		
		
		CarList.addAll(leftcarList);
		
		CarList.addAll(centercarList);
		
		CarList.addAll(rightcarList);

		return CarList;
	}
	
	private ArrayList<Ticket> getTicketList(Officer officer) {
		CarDao dao;

		
		switch(officer.getRegion())
		{
			case "LEFT":
				
				dao=dao_LEFT;

				break;
			case "CENTER":
				dao=dao_CENTER;

				break;
			default://"RIGHT"

				dao=dao_RIGHT;
				
		}
		
		
		
		
		return dao.getTicketList(officer.getOfficerId());
	}
	@Override
	public ArrayList<Car> getRegionCarList(String region)
	{

		ArrayList<Car> regionCarList;
		
		switch(region)
		{
			case "LEFT":
				

				regionCarList=leftcarList;
				break;
			case "CENTER":
		
				regionCarList=centercarList;
				break;
			default://"RIGHT"
				regionCarList=rightcarList;
			
				
		}
		
		return regionCarList;
	}
	
	

	
	
	public ExecutorService getExecutorService()
	{
		return threadExecutor;
	}
	
	public void updateCarInRegion(Car b, String region)
	{
		CarDao dao;
		ArrayList<Car> regionCarList;
		
		switch(region)
		{
			case "LEFT":
				
				dao=dao_LEFT;
				regionCarList=leftcarList;
				break;
			case "CENTER":
				dao=dao_CENTER;
				regionCarList=centercarList;
				break;
			default://"RIGHT"
				regionCarList=rightcarList;
				dao=dao_RIGHT;
				
		}
		//regionCarList.get(regionCarList.indexOf(b)).setRun(b.get;);;
		//regionCarList.get(regionCarList.indexOf(b)).setRegion(b.getRun());
		for(Car c:regionCarList)
		{
			if(c.getLicense().equals(b.getLicense()))
			{
				
				c.setRun(b.getRun());
				
				
				
			}
		}
		
		dao.updateCar(b);
	}
	
	public void updateCarsInRegion(String region)
	{
		CarDao dao;
		ArrayList<Car> regionCarList;
		
		switch(region)
		{
			case "LEFT":
				
				dao=dao_LEFT;
				regionCarList=leftcarList;
				break;
			case "CENTER":
				dao=dao_CENTER;
				regionCarList=centercarList;
				break;
			default://"RIGHT"
				regionCarList=rightcarList;
				dao=dao_RIGHT;
				
		}
		
		dao.updateCars(regionCarList);
	}

	public void updateAllCars()
	{
		ArrayList<Car> toLeftRegionCars=new ArrayList<Car>();
		ArrayList<Car> toCenterRegionCars=new ArrayList<Car>();
		ArrayList<Car> toRightRegionCars=new ArrayList<Car>();
		toCenterRegionCars.clear();
		
		synchronized(leftcarList)
		{
		
			for(Car b:leftcarList)
			{
				
				if(!b.getRegion().equals("LEFT"))
				{
					
					toCenterRegionCars.add(b);
				}
				else
				{
					if(dao_LEFT.getCarByLicense(b.getLicense())!=null)
					{
						
						dao_LEFT.updateCar(b);
					}
					
					
					
					//dao_LEFT.deleteCar(b.getCarid());
				}
				
			}
			
			for(Car b: toCenterRegionCars)
			{
				leftcarList.remove(b);
				dao_LEFT.deleteCar(b.getLicense());
				Car nb=new Car(b.getLicense(),b.getPx(),b.getPy(),b.getDx(),b.getDy(),b.getSize(),b.getColor(),"CENTER");
				threadExecutor.execute(nb);
				centercarList.add(nb);
				dao_CENTER.addCar(nb);	
				
			}
		}
	
		synchronized(centercarList)
		{
			toLeftRegionCars.clear();
			toRightRegionCars.clear();
			for(Car b:centercarList)
			{
				
				if(b.getRegion().equals("LEFT"))
				{
					toLeftRegionCars.add(b);				
				}
				else if(b.getRegion().equals("RIGHT"))
				{
					toRightRegionCars.add(b);

				}			
				else 
				{
					
					if(dao_CENTER.getCarByLicense(b.getLicense())!=null)
					{
						
						dao_CENTER.updateCar(b);
					}
					
				}
					
			}
			
			for(Car b: toLeftRegionCars)
			{
				centercarList.remove(b);
				dao_CENTER.deleteCar(b.getLicense());
				Car nb=new Car(b.getLicense(),b.getPx(),b.getPy(),b.getDx(),b.getDy(),b.getSize(),b.getColor(),"LEFT");
				threadExecutor.execute(nb);
				leftcarList.add(nb);
				dao_LEFT.addCar(nb);	
				
			}
			
			for(Car b: toRightRegionCars)
			{
				centercarList.remove(b);
				dao_CENTER.deleteCar(b.getLicense());
				Car nb=new Car(b.getLicense(),b.getPx(),b.getPy(),b.getDx(),b.getDy(),b.getSize(),b.getColor(),"RIGHT");
				threadExecutor.execute(nb);
				rightcarList.add(nb);
				dao_RIGHT.addCar(nb);	
				
			}
		}
		
		synchronized(rightcarList)
		{			
			
			toCenterRegionCars.clear();
			for(Car b:rightcarList)
			{

				if(!b.getRegion().equals("RIGHT"))
				{
					toCenterRegionCars.add(b);
				}
				else
				{
					
					if(dao_RIGHT.getCarByLicense(b.getLicense())!=null)
					{
						
						dao_RIGHT.updateCar(b);
					}
					
				}
				
			}
			
			for(Car b: toCenterRegionCars)
			{
				rightcarList.remove(b);
				dao_RIGHT.deleteCar(b.getLicense());
				Car nb=new Car(b.getLicense(),b.getPx(),b.getPy(),b.getDx(),b.getDy(),b.getSize(),b.getColor(),"CENTER");
				threadExecutor.execute(nb);
				centercarList.add(nb);
				dao_CENTER.addCar(nb);	
				
			}
		}
		
		
		

//		dao_LEFT.updateCars(leftcarList);
//		dao_CENTER.updateCars(centercarList);
//		dao_RIGHT.updateCars(rightcarList);
	}
	
	@Override
	public void createKCarsInCircle_Region(Ellipse2D shape,String region) {
		

		int k=random.nextInt(4)+1;
		for(int i=0;i<k;i++)
		{
			float R=(float) shape.getWidth()/2;
			Double x1=shape.getX()+R;
			Double y1=shape.getY()+R;
			//random.nextFloat() * (maxX - minX) + minX;
			float r=(float) (random.nextFloat() * (R - 1) + 1);//1<r<R
			double angle=random.nextFloat() * (2*Math.PI);//0<angle<360
			float d=random.nextFloat() * (R-r);//0<d<R-r
			float px=(float) (x1-Math.sin(angle-0.5*Math.PI));
			float py=(float) (y1-Math.sin(angle-0.5*Math.PI));
			
			
			Car car;


			CarDao dao;
			ArrayList<Car> regionCarList;
			
			switch(region)
			{
				case "LEFT":
					
					dao=dao_LEFT;
					regionCarList=leftcarList;

					synchronized (regionCarList)
					{
						car = new Car("L "+number_car_L++,px,py,region);
						threadExecutor.execute(car);
						dao.addCar(car);
						regionCarList.add(car);						
					}
					break;
				case "CENTER":
					dao=dao_CENTER;
					regionCarList=centercarList;
					
					synchronized (regionCarList)
					{
						car = new Car("C "+number_car_L++,px,py,region);
						threadExecutor.execute(car);
						dao.addCar(car);
						regionCarList.add(car);						
					}
					break;
				default://"RIGHT"
					regionCarList=rightcarList;
					dao=dao_RIGHT;
					synchronized (regionCarList)
					{
						car = new Car("R "+number_car_L++,px,py,region);
						threadExecutor.execute(car);
						dao.addCar(car);
						regionCarList.add(car);						
					}
					
			}
			

		}
	}

	
	
	@Override
	public void run() {

        while (true) {
            // update database
        	updateAllCars();
        	

            // Delay and give other thread a chance
            try {
               Thread.sleep(50);
            } catch (InterruptedException ex) {}
         }
	}


	@Override
	public void execute(Car b, String region) throws RemoteException {

		ArrayList<Car> regionCarList;
		
		switch(region)
		{
			case "LEFT":
				

				regionCarList=leftcarList;
				break;
			case "CENTER":

				regionCarList=centercarList;
				break;
			default://"RIGHT"
				regionCarList=rightcarList;

				
		}
		for(Car c:regionCarList)
		{
			if(c.getLicense().equals(b.getLicense()))
			{
				threadExecutor.execute(c);
			}
		}
		
	}


	@Override
	public boolean userLogon(String account, String region) throws RemoteException {
		CarDao dao;

		
		switch(region.trim())
		{
			case "LEFT":
				//messagebuff=messagebuff_LEFT;
				dao=dao_LEFT;

				break;
			case "CENTER":
				//messagebuff=messagebuff_CENTER;
				dao=dao_CENTER;

				break;
			default://"RIGHT"
				//messagebuff=messagebuff_RIGHT;
				dao=dao_RIGHT;
				
		}
		Officer user=dao.getUserByID(account.trim());
		if(user!=null)
		{
			Officer officer=new Officer(user.getOfficerId(),user.getOfficerName(),
					user.getRegion(),user.getTickets(),"online");
			dao.updateUser(officer);
			onlineUserList.add(officer);
			
			
			

			
			for(MessageBuff buff:messagebuffs)
			{
				buff.setBuffContent(account+": entered the chatroom");
			}
			
			
			
			MessageBuff mb=new  MessageBuff(account.trim(),account+": entered the chatroom");
			
			messagebuffs.add(mb);
			
			messagebuff.append(account+": entered the chatroom \n");
			
//			
//			
//			messagebuff_LEFT.append(account+" entered the chatroom");
//			messagebuff_CENTER.append(account+" entered the chatroom");
//			messagebuff_RIGHT.append(account+" entered the chatroom");
			return true;

		}

		
		
		return false;
		
	}


	@Override
	public void IssueTicket(String carLicense, String officerName, String region,
			String ticketName) throws RemoteException {
		CarDao dao;
		
		
		
		switch(region.trim())
		{
			case "LEFT":
				
				dao=dao_LEFT;

				break;
			case "CENTER":
				dao=dao_CENTER;

				break;
			default://"RIGHT"

				dao=dao_RIGHT;
				
		}
		
		Car issueCar=dao.getCarByLicense(carLicense);
		Officer officer=dao.getUserByID(officerName);
		dao.issueTicket(issueCar,officer);
	}


	@Override
	public String getNewMessage(String officerID) throws RemoteException {

		
		String message = null;
		
		for(MessageBuff buff:messagebuffs)
		{
			if(buff.getOfficerId().equals(officerID.trim()))
			{
				message=buff.getBuffContent();
				buff.clearBuff();
			}
		}
		
		
		

		
		return message;

		
	}


	@Override
	public void sendMessage(String account, String newMessage)
			throws RemoteException {
		messagebuff.append(newMessage+"\n");
		//JOptionPane.showMessageDialog(null,newMessage);
		
		for(MessageBuff buff:messagebuffs)
		{
			if(!buff.getOfficerId().equals(account.trim()))
			{				
				buff.setBuffContent(newMessage);
			}
			else
			{
				
				Officer atOfficerForCarNumber = null;
				Officer atOfficerForTicketNumber = null;
				
				for(Officer officer:onlineUserList)
				{
					if(newMessage.replaceAll("\\s","").toUpperCase().equals((account+" : @ "+officer.getOfficerId()+", HowManyCars?").replaceAll("\\s","").toUpperCase()))
					{
						atOfficerForCarNumber=officer;
					}
					else if(newMessage.replaceAll("\\s","").toUpperCase().equals((account+" : @ "+officer.getOfficerId()+", HowManyTickets?").replaceAll("\\s","").toUpperCase()))
					{
						atOfficerForTicketNumber=officer;
					}
				}
				if(atOfficerForCarNumber!=null)
				{
					
					newMessage=atOfficerForCarNumber.getOfficerId()+" : -------->"+getRegionCarList(atOfficerForCarNumber.getRegion()).size()+ "Cars";				
					buff.setBuffContent(newMessage);
					newMessage=null;
					break;
				}
				
				if(atOfficerForTicketNumber!=null)
				{
					
					newMessage=atOfficerForTicketNumber.getOfficerId()+" : -------->"+getTicketList(atOfficerForTicketNumber).size()+ "Tickets";				
					buff.setBuffContent(newMessage);
					newMessage=null;
					break;
				}

				
			}
			
		}
		
	}





	public String getNewMessage() {
		//JOptionPane.showMessageDialog(null,"aa");
		String message = null;
		
		message=messagebuff.toString();
		
		messagebuff.delete(0, messagebuff.length());
		
		return message;
	}


	@Override
	public void userlogout(String account,String region) throws RemoteException {

		CarDao dao;

		
		switch(region.trim())
		{
			case "LEFT":
				//messagebuff=messagebuff_LEFT;
				dao=dao_LEFT;

				break;
			case "CENTER":
				//messagebuff=messagebuff_CENTER;
				dao=dao_CENTER;

				break;
			default://"RIGHT"
				//messagebuff=messagebuff_RIGHT;
				dao=dao_RIGHT;
				
		}
		Officer user=dao.getUserByID(account.trim());
		if(user!=null)
		{
			Officer officer=new Officer(user.getOfficerId(),user.getOfficerName(),
					user.getRegion(),user.getTickets(),"offline");
			dao.updateUser(officer);
			for(Officer o:onlineUserList)
			{
				if(o.getOfficerId().equals(account.trim()))
				{
					onlineUserList.remove(o);
				}
			}
			
			
			
			
			
			MessageBuff dispossingBuff = null;
			
			for(MessageBuff buff:messagebuffs)
			{
				if(buff.getOfficerId().equals(account.trim()))
				{
					dispossingBuff=buff;
				}
				else
				{
					buff.setBuffContent(account+": left the chatroom");
				}
				
			}
			if(dispossingBuff!=null)
			{
				messagebuffs.remove(dispossingBuff);
			}
			
			
			

			
			messagebuff.append(account+": left the chatroom \n");
			
//			
//			
//			messagebuff_LEFT.append(account+" entered the chatroom");
//			messagebuff_CENTER.append(account+" entered the chatroom");
//			messagebuff_RIGHT.append(account+" entered the chatroom");


		}

		
		

		
	}









}
