
package modelo;
import java.text.SimpleDateFormat;
import java.util.Date;

import businessLogic.BLFacadeImplementation;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class CreateRideBean {
    private String origin;
    private String destination;
    private Date date;
    private int nPlaces;
    private float price;
    private String driverEmail;
    
    @Inject  
    private DriverBean driverFromBean;

    
    private BLFacadeImplementation facade = new BLFacadeImplementation();

    public String createRide() {
        try {
            System.out.println("Datos a guardar:"); // Log
            System.out.println("Origin: " + origin);
            System.out.println("Destination: " + destination);
            System.out.println("Date: " + date);
            System.out.println("Places: " + nPlaces);
            System.out.println("Price: " + price);
            System.out.println("Email: " + driverEmail);

            
            if (date != null && date.before(new Date())) {
                FacesContext.getCurrentInstance().addMessage("date",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error", "La fecha no puede ser anterior a hoy"));
                return null;
            }
            
            if (nPlaces < 0) {
                FacesContext.getCurrentInstance().addMessage("places", 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error", "El número de plazas debe ser mayor que 0"));
                return null;
            }
            
            if (price < 0) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error", "El precio debe ser mayor que 0"));
                return null;
            }

            this.driverEmail = driverFromBean.getEmail();
            facade.createRide(origin, destination, date, nPlaces, price, driverEmail);
            System.out.println("Viaje creado exitosamente"); 
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Éxito", "Viaje creado correctamente"));
            
           clearFields();
            
            return "menuAfterLogin?faces-redirect=true";
            
        } catch (Exception e) {
        	if (e.getMessage().contains("Ya has creado un viaje similar")) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error", "Ya has creado un viaje para este destino en esta fecha"));
            } else {
                
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error", e.getMessage()));
            }
            return null;
        }
    }
    private void clearFields() {
        this.date = null;
        this.destination = null;
        this.driverEmail = null;
        this.nPlaces = 0;
        this.origin = null;
        this.price = 0;
    }
    public void onDateSelect() {
        if (date != null) {
            if (date.before(new Date())) {
                
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error", "La fecha no puede ser anterior a hoy"));
                date = null; 
            } else {
                
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = formatter.format(date);

                
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Fecha seleccionada", "Has seleccionado: " + formattedDate));
            }
        }
    }

    

    // Getters y Setters
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    
    public int getnPlaces() { return nPlaces; }
    public void setnPlaces(int nPlaces) { this.nPlaces = nPlaces; }
    
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    
    public String getDriverEmail() { return driverEmail; }
    public void setDriverEmail(String driverEmail) { this.driverEmail = driverEmail; }
}
