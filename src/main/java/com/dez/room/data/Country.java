package com.dez.room.data;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
@Table(name = "sxgeo_country")
public class Country {

    @Id
    private short id;
    @Length(max = 2)
    private String  iso;
    @Length(max = 2)
    private String  continent;
    @Length(max = 128)
    private String name_ru;
    @Length(max = 128)
    private String name_en;
    @Digits(integer=6, fraction=2)
    private BigDecimal lat;
    @Digits(integer=6, fraction=2)
    private BigDecimal lon;
    @Length(max = 30)
    private String timezone;

    public Country() {
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", iso='" + iso + '\'' +
                ", continent='" + continent + '\'' +
                ", name_ru='" + name_ru + '\'' +
                ", name_en='" + name_en + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", timezone='" + timezone + '\'' +
                '}';
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getName_ru() {
        return name_ru;
    }

    public void setName_ru(String name_ru) {
        this.name_ru = name_ru;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
