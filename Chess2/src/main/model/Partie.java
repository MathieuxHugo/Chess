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
    
    @Column(name = "finie")
    private boolean finie;
    
    @Column(name = "fen")
    private String fen;
    
    
    
    public Partie() {
	this.finie = false;
    }



    public String getFen() {
        return fen;
    }


    
    public void setFen(String fen) {
        this.fen = fen;
    }


    public boolean isFinie() {
        return finie;
    }

    
    public void setFinie(boolean finie) {
        this.finie = finie;
    }

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
