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
    private String confirmPassword;
	
	private BLFacadeImplementation facade = new BLFacadeImplementation();
	
	public DriverBean() {}
	

	public String register() throws Exception {
	    try {
	        // Check if any required field is empty
	        if (name == null || name.trim().isEmpty()) {
	            System.out.println("Error: Nombre vacío.");
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El nombre es obligatorio."));
	            return null;
	        }

	        if (email == null || email.trim().isEmpty()) {
	            System.out.println("Error: Email vacío.");
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El email es obligatorio."));
	            return null;
	        }

	        if (password == null || password.trim().isEmpty()) {
	            System.out.println("Error: Contraseña vacía.");
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La contraseña es obligatoria."));
	            return null;
	        }

	        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
	            System.out.println("Error: Confirmar contraseña vacío.");
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Por favor, confirma tu contraseña."));
	            return null;
	        }

	        if (!isPasswordMatching()) {
	            System.out.println("Error: Las contraseñas no coinciden.");
	            FacesContext.getCurrentInstance().addMessage(null, 
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match", ""));
	            return null;
	        }

	        // Check if email is already registered
	        if (isEmailAlreadyRegistered(email)) {
	            System.out.println("Error: Email ya registrado.");
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Este email ya está registrado."));
	            return null;
	        }

	        // If all validations pass, proceed with registration
	        System.out.println("Intentando registrar driver:");
	        System.out.println("Email: " + email);
	        System.out.println("Nombre: " + name);

	        // Create driver using facade
	        facade.createDriver(email, password, name);
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Registro completado correctamente"));

	        // Clear the fields after successful registration
	        email = null;
	        password = null;
	        name = null;
	        return "index?faces-redirect=true";
	        
	    } catch (Exception e) {
	        // Log any exceptions that occur during the process
	        System.out.println("Error en el registro: " + e.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
	        return null;
	    }
	}

	

	    private boolean isEmailAlreadyRegistered(String email) {
	        return false;  
	    }
	
		public void validateEmail() {
		    if (email != null && !email.isEmpty()) {
		        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
		            FacesContext.getCurrentInstance().addMessage("email", 
		                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de email inválido", null));
		        } else {
		            FacesContext.getCurrentInstance().addMessage("email", 
		                new FacesMessage(FacesMessage.SEVERITY_INFO, "Formato de email válido", null));
		        }
		    }
		}

	    
	    
	    public String getConfirmPassword() {
	        return confirmPassword;
	    }

	    public void setConfirmPassword(String confirmPassword) {
	        this.confirmPassword = confirmPassword;
	    }
	    
	    public boolean isPasswordMatching() {
	        return password != null && password.equals(confirmPassword);
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
