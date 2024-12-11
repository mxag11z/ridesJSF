package modelo.dominio;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rides")
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin", nullable = false)
    private String origin;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "places", nullable = false)
    private int places;

    @Column(name = "price", nullable = false)
    private float price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_email", referencedColumnName = "email")
    private Driver driver;


    // Getters y setters
    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int nPlaces) {
        this.places = nPlaces;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    
    public String getDriverEmail() {
        return driver != null ? driver.getEmail() : null;
    }
    }

