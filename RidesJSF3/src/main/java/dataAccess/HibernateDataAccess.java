package dataAccess;



import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import modelo.JPAutil;
import modelo.dominio.Driver;
import modelo.dominio.Ride;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HibernateDataAccess {
	
	
	
	public Ride createRide(String from, String to, Date date, int places, float price, String driverEmail) throws Exception {
	    EntityManager em = JPAutil.getEntityManager();
	    try {
	        em.getTransaction().begin();
	        
	        Driver driver = em.find(Driver.class, driverEmail);
	        
	        // Verifica si el conductor ya tiene un viaje similar
	        Query query = em.createQuery(
	            "SELECT r FROM Ride r WHERE r.driver.email = :driverEmail " +
	            "AND r.origin = :origin " +
	            "AND r.destination = :destination " +
	            "AND r.date = :date");
	        
	        query.setParameter("driverEmail", driverEmail);
	        query.setParameter("origin", from);
	        query.setParameter("destination", to);
	        query.setParameter("date", date);
	        
	        if (!query.getResultList().isEmpty()) {
	            throw new Exception("Ya has creado un viaje similar para esta fecha");
	        }
	        
	        Ride ride = new Ride();
	        ride.setOrigin(from);
	        ride.setDestination(to);
	        ride.setDate(date);
	        ride.setPlaces(places);
	        ride.setPrice(price);
	        ride.setDriver(driver);
	        
	        em.persist(ride);
	        em.getTransaction().commit();
	        return ride;
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        throw e;
	    } finally {
	        em.close();
	    }
	}
    
    public Driver createDriver(String email, String password, String name) throws Exception {
        EntityManager em = JPAutil.getEntityManager();
        try {
            em.getTransaction().begin();
            // Verifica si el conductor ya existe
            Query query = em.createQuery("SELECT d FROM Driver d WHERE d.email = :email");
            query.setParameter("email", email);
            if (!query.getResultList().isEmpty()) {
                throw new Exception("El conductor ya está registrado.");
            }
            // Crea y guarda el nuevo conductor
            Driver driver = new Driver();
            driver.setEmail(email);
            driver.setPassword(password);
            driver.setName(name);
            em.persist(driver);
            em.getTransaction().commit();
            return driver;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Driver loginDriver(String email, String password) throws Exception {
        EntityManager em = JPAutil.getEntityManager();
        try {
            Query query = em.createQuery(
                "SELECT d FROM Driver d WHERE d.email = :email AND d.password = :password");
            query.setParameter("email", email);
            query.setParameter("password", password);
            List<Driver> result = query.getResultList();
            if (result.isEmpty()) {
                throw new Exception("Email o contraseña incorrectos.");
            }
            return result.get(0);
        } finally {
            em.close();
        }
    }
    
 
    public List<Ride> findRidesByDriverEmail(String email) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            return em.createQuery("SELECT r FROM Ride r WHERE r.driverEmail = :email", Ride.class)
                     .setParameter("email", email)
                     .getResultList();
        } finally {
            em.close();
        }
    }


    public List<Ride> getDriverRides(String driverEmail) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            Query query = em.createQuery(
                "SELECT r FROM Ride r WHERE r.driverEmail = :driverEmail");
            query.setParameter("driverEmail", driverEmail);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<String> getDepartCities() {
        EntityManager em = JPAutil.getEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT r.origin FROM Ride r ORDER BY r.origin", String.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public List<String> getDestinationCities(String from) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT r.destination FROM Ride r WHERE r.origin = :from ORDER BY r.destination", String.class)
                     .setParameter("from", from)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Ride> getRides(String from, String to, Date date) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            return em.createQuery("SELECT r FROM Ride r WHERE r.origin = :from AND r.destination = :to AND r.date = :date ", Ride.class)
                     .setParameter("from", from)
                     .setParameter("to", to)
                     .setParameter("date", date)
                     .getResultList();
        } finally {
            em.close();
        }
    }


    public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
        EntityManager em = JPAutil.getEntityManager();
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            Date firstDay = cal.getTime();
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date lastDay = cal.getTime();
            
            return em.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.origin = :from AND r.destination = :to AND r.date BETWEEN :firstDay AND :lastDay ORDER BY r.date", Date.class)
                     .setParameter("from", from)
                     .setParameter("to", to)
                     .setParameter("firstDay", firstDay)
                     .setParameter("lastDay", lastDay)
                     .getResultList();
        } finally {
            em.close();
        }
    }
}
