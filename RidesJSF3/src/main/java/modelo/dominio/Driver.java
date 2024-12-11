package modelo.dominio;


import jakarta.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "drivers")
public class Driver {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; 
	
	@Id
    @Column(name = "email")
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String name;
    
    @OneToMany(mappedBy = "driver", fetch = FetchType.EAGER)
    private List<Ride> rides;
	    
	   
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Ride> getRides() {
		return rides;
	}
	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}
	   
	   
	   
}
