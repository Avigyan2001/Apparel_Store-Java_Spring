package com.example.demo.DB;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "items")
public class Item implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name = "ItemId", updatable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(columnDefinition = "DOUBLE(5,2) DEFAULT 0 CHECK (discount >= 0 and discount <= 100) ")
    private double discount;

    @Column(columnDefinition = "INT DEFAULT 0 CHECK (quantity >= 0)")
    private int quantity;

    @Column(columnDefinition = "VARCHAR(1) CHECK (gender IN ('M', 'F') )")
    private String gender;

    @Column(columnDefinition = "VARCHAR(10) CHECK (season IN ('summer', 'winter'))")
    private String season;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date arrivalDate;

    @Column
    private String url;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getGender() {
        return gender;
    }

    public String getSeason() {
        return season;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    @Override
    public String toString() {
        return "Item [arrivalDate=" + arrivalDate + ", discount=" + discount + ", gender=" + gender + ", id=" + id
                + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", season=" + season + ", url="
                + url + "]";
    }

    public String getUrl() {
        return url;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public Double getActualPrice()
    {
        return price * (100 - discount) / 100;
    }

    public boolean isNew()
    {
        Date date = new Date(System.currentTimeMillis() - (1l * 30l * 24l * 3600l * 1000l));
        if(arrivalDate.compareTo(date) > 0)
        return true;
        else
        return false;
    }
}