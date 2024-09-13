package main.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;


@Entity
public class Partie {

    @Id
    private String name;

    @Column(name = "coups")
    private String coups;

    @Column(name = "computer")
    private String computer;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getCoups() {
	return coups;
    }

    public void setCoups(String coups) {
	this.coups = coups;
    }
    
    public String getComputer() {
        return computer;
    }

    
    public void setComputer(String computer) {
        this.computer = computer;
    }

    public Date getCreatedAt() {
	return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
	this.createdAt = createdAt;
    }

}
