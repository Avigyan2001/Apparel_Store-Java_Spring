package com.example.demo.DB;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer implements Serializable{
    
    @Id
    @GeneratedValue
    @Column(name = "CustomerId", updatable = false)
    private int id;

    @Column(columnDefinition = "VARCHAR(1) NOT NULL CHECK (gender IN ('M', 'F') )")
    private String gender;

    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'season' CHECK (preference IN ('arrival', 'season') )")
    private String preference;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer [email=" + email + ", gender=" + gender + ", id=" + id + ", name=" + name + ", preference="
                + preference + "]";
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public String getPreference() {
        return preference;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public void setName(String name) {
        this.name = name;
        if(name.length() == 0) this.name = null;
    }

    public void setEmail(String email) {
        this.email = email;
        if(email.length() == 0) this.email = null;
    }
        
}
