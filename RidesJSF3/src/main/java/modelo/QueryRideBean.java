package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import businessLogic.BLFacadeImplementation;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import modelo.dominio.Driver;
import modelo.dominio.Ride;

@Named
@SessionScoped
public class QueryRideBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String selectedOrigin;
    private String selectedDestination;
    private Date selectedDate;
    private List<String> originCities;
    private List<String> destinationCities;
    private List<Ride> foundRides;
    private List<Date> datesWithRides;
    private Driver driver; 
    
    private BLFacadeImplementation facade = new BLFacadeImplementation();
    
    @PostConstruct
    public void init() {
        originCities = facade.getDepartCities();
        
    }
    
    public List<Ride> getDriverRide() {
        return facade.findRidesByDriverEmail(driver.getEmail());
    }


    public Driver getDriverBean() {
        return driver;
    }
    
    public void setDriverBean(Driver driver) {
        this.driver = driver;
    }
    
    public void onOriginSelect() {
        destinationCities = facade.getDestinationCities(selectedOrigin);
    }
    
    public void searchRides() {
        foundRides = facade.getRides(selectedOrigin, selectedDestination, selectedDate);
    }
    


	public void onDateSelect() {
        datesWithRides = facade.getThisMonthDatesWithRides(selectedOrigin, selectedDestination, selectedDate);
    }

	public String getSelectedOrigin() {
		return selectedOrigin;
	}

	public void setSelectedOrigin(String selectedOrigin) {
		this.selectedOrigin = selectedOrigin;
	}

	

	public String getSelectedDestination() {
		return selectedDestination;
	}

	public void setSelectedDestination(String selectedDestination) {
		this.selectedDestination = selectedDestination;
	}

	public Date getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}

	public List<String> getOriginCities() {
		return originCities;
	}

	public void setOriginCities(List<String> originCities) {
		this.originCities = originCities;
	}

	public List<String> getDestinationCities() {
		return destinationCities;
	}

	public void setDestinationCities(List<String> destinationCities) {
		this.destinationCities = destinationCities;
	}

	public List<Ride> getFoundRides() {
		return foundRides;
	}

	public void setFoundRides(List<Ride> foundRides) {
		this.foundRides = foundRides;
	}

	public List<Date> getDatesWithRides() {
		return datesWithRides;
	}

	public void setDatesWithRides(List<Date> datesWithRides) {
		this.datesWithRides = datesWithRides;
	}
	


}
