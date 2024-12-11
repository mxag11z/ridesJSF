package businessLogic;
import dataAccess.HibernateDataAccess;
import modelo.dominio.Ride;
import modelo.dominio.Driver;
import java.util.List;
import java.io.Serializable;
import java.util.Date;

public class BLFacadeImplementation implements Serializable{
    private HibernateDataAccess dataAccess = new HibernateDataAccess();
    
    // Crear un nuevo viaje
    public Ride createRide(String from, String to, Date date, int places, float price, String driverEmail) throws Exception {
        return dataAccess.createRide(from, to, date, places, price, driverEmail);
    }
    
    // Driver methods
    public Driver createDriver(String email, String password, String name) throws Exception {
        return dataAccess.createDriver(email, password, name);
    }
    
    public Driver loginDriver(String email, String password) throws Exception {
        return dataAccess.loginDriver(email, password);
    }
    
    public List<Ride> getDriverRides(String driverEmail) {
        return dataAccess.getDriverRides(driverEmail);
    }
    
    // Métodos para consulta de viajes
    public List<String> getDepartCities() {
        return dataAccess.getDepartCities();
    }
    
    
    public List<String> getDestinationCities(String from) {
        return dataAccess.getDestinationCities(from);
    }
    
    public List<Ride> getRides(String from, String to, Date date) {
    	
        return dataAccess.getRides(from, to, date);
    }
    
    public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
        return dataAccess.getThisMonthDatesWithRides(from, to, date);
    }

	public List<Ride> findRidesByDriverEmail(String email) {
		 return dataAccess.findRidesByDriverEmail(email);
	}
}