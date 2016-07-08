package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Car;
import model.Officer;
import model.Ticket;
import util.HibernateUtil;

public class CarDao {
//	Session LeftSession = HibernateUtil.getLeftSessionFactory().openSession();
//	Session CenterSession = HibernateUtil.getCenterSessionFactory().openSession();
//	Session RightSession = HibernateUtil.getRightSessionFactory().openSession();
	
	private SessionFactory sessionFactory;

	public CarDao(String region)
	{
		switch(region)
		{
			case "LEFT":
				sessionFactory = HibernateUtil.getLeftSessionFactory();
				break;
			case "CENTER":
				sessionFactory = HibernateUtil.getCenterSessionFactory();
				break;
			default://"RIGHT"
				sessionFactory=HibernateUtil.getRightSessionFactory();	
				
		}
	}
	public void addUser(Officer police) {
		Transaction trns = null;
		
		Session session = sessionFactory.openSession();

		try {
			trns = session.beginTransaction();
			session.save(police);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}

		
	}
	public void addCar(Car car) {
		Transaction trns = null;
		
		Session session = sessionFactory.openSession();

		try {
			trns = session.beginTransaction();
			session.save(car);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}

	public void deleteCar(String carlicense) {
		Transaction trns = null;
		Session session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			Car car = (Car) session.load(Car.class, new String(carlicense));
			session.delete(car);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}
	
	public void deleteAllUsers() {

		Transaction trns = null;
		Session session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			String hqlDelete = "delete Officer";
			session.createQuery(hqlDelete).executeUpdate();
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		
		
	}
	
	public void deleteAllCars() {
		Transaction trns = null;
		Session session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			String hqlDelete = "delete Car";
			session.createQuery(hqlDelete).executeUpdate();
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}
	public void updateUser(Officer officer) {
		Transaction trns = null;
		Session session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			session.update(officer);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}

		
	}
	public void updateCar(Car car) {
		Transaction trns = null;
		Session session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			session.update(car);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}
	

	
	public void updateCars(ArrayList<Car> carList) {
		
		Transaction trns = null;
		Session session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			for(Car Iter:carList)
			session.update(Iter);			
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		
	}

	public List<Car> getAllCars() {
		List<Car> Cars = new ArrayList<Car>();
		Transaction trns = null;
		Session session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			Cars = session.createQuery("from Car").list();
			
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return Cars;
	}
	


	public Car getCarByLicense(String LICENSE) {
		Car Car = null;
		Transaction trns = null;
		Session session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			String queryString = "from Car where LICENSE = :LICENSE";
			Query query = session.createQuery(queryString);
			query.setParameter("LICENSE", LICENSE);
			Car = (Car) query.uniqueResult();
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return Car;
	}
	
	public ArrayList<Ticket> getTicketList(String officerId) {
		ArrayList<Ticket> tickets = null;
		Officer officer = null;
		Transaction trns = null;
		Session session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			String queryString = "from Officer where officer_Id = :officerId";

			Query query = session.createQuery(queryString);
			query.setParameter("officerId", officerId);
			
			//JOptionPane.showMessageDialog(null,query.getQueryString());
			
			officer = (Officer) query.uniqueResult();
			tickets=new ArrayList(officer.getTickets());
			//session.getTransaction().commit();
			
		} catch (RuntimeException e) {
			//System.out.println("aaaaa");
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null,e);
		} finally {
			session.flush();
			session.close();
		}

		return   tickets;

		
	}
	public Officer getUserByID(String officerId) {
		Officer officer = null;
		Transaction trns = null;
		Session session = sessionFactory.openSession();
		try {
			trns = session.beginTransaction();
			String queryString = "from Officer where officer_Id = :officerId";

			Query query = session.createQuery(queryString);
			query.setParameter("officerId", officerId);
			
			//JOptionPane.showMessageDialog(null,query.getQueryString());
			
			officer = (Officer) query.uniqueResult();
			//session.getTransaction().commit();
			
		} catch (RuntimeException e) {
			//System.out.println("aaaaa");
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null,e);
		} finally {
			session.flush();
			session.close();
		}
		return officer;
		
	}
	public void issueTicket(Car issueCar, Officer officer) {
		
		Transaction trns = null;
		
		Session session = sessionFactory.openSession();

		try {
			trns = session.beginTransaction();
			
			Ticket t=new Ticket();
			
			t.setCar(issueCar);
			t.setOfficer(officer);
			t.setCreatedDate(new Date());
			
			//issueCar.getTickets().add(t);
			
			session.save(t);
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			if (trns != null) {
				trns.rollback();
			}
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,e);
		} finally {
			
			
			session.flush();
			session.close();
		}
		
	}








	
//	public List<Car> getCarsByRegionn(String region) {
//		List<Car> cars = null;
//		Transaction trns = null;
//		Session session = sessionFactory.openSession();
//		try {
//			trns = session.beginTransaction();
//			String queryString = "from Car where region = :region";
//			Query query = session.createQuery(queryString);
//			query.setString("region", region);
//			cars = query.list();
//		} catch (RuntimeException e) {
//			e.printStackTrace();
//		} finally {
//			session.flush();
//			session.close();
//		}
//		return cars;
//	}
}
