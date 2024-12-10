package modelo;
import java.io.Serializable;
import java.util.Date;

import businessLogic.BLFacadeImplementation;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import modelo.dominio.Driver;

@Named
@SessionScoped
public class DriverBean implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	private String name;
	private Driver loggedInDriver;
	
	private BLFacadeImplementation facade = new BLFacadeImplementation();
	
	public DriverBean() {}
	
	public String register() {
		try {
			System.out.println("Intentando registrar driver:");
            System.out.println("Email: " + email);
            System.out.println("Nombre: " + name);
           facade.createDriver(email, password, name);
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Exito","Registro completado correctamente"));
           email = null;
           password = null;
           name = null;
           return "index?faces-redirect=true";
		}catch(Exception e ) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",e.getMessage()));
			return null;
		}
	}
	
	public String login() {
	    try {
	        System.out.println("Intentando login con email: " + email);
	        
	        // Verificar si el login es exitoso
	        loggedInDriver = facade.loginDriver(email, password);
	        if (loggedInDriver != null) {
	            System.out.println("Login exitoso: " + loggedInDriver.getEmail());
	            this.email = loggedInDriver.getEmail();
	            this.name = loggedInDriver.getName();
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_INFO,
	                "Éxito", "Login correcto"));
	            return "menuAfterLogin?faces-redirect=true";
	        } else {
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                "Error", "Credenciales incorrectas"));
	            return null;
	        }
	        
	    } catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_ERROR,
	            "Error", e.getMessage()));
	        return null;
	    }
	}

    
    // Método para verificar el login y redirigir si no está logueado
    public void checkLogin() {
        if (!isLoggedIn()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // Método para logout
    public String logout() {
        loggedInDriver = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }
    
    // Método para verificar si hay un usuario logueado
    public boolean isLoggedIn() {
        return loggedInDriver != null;
    }
    
    
    // Getters y Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Driver getLoggedInDriver() { return loggedInDriver; }
    public void setLoggedInDriver(Driver loggedInDriver) { this.loggedInDriver = loggedInDriver; }
}
