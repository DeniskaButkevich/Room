package com.dez.room.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String country;
    private boolean lamp;


    public Room() {
    }

    public Room(String name, String country, boolean lamp) {
        this.name = name;
        this.country = country;
        this.lamp = lamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isLamp() {
        return lamp;
    }

    public void setLamp(boolean lamp) {
        this.lamp = lamp;
    }

}
