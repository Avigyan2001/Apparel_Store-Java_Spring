package com.example.demo.DB;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name = "orders")
public class Order implements Serializable{
    
    @Id
    @GeneratedValue
    @Column(name = "OrderId", updatable = false)
    private int id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "ItemId", name = "ItemId")
    private Item item;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CustomerId", name="CustomerId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    public Order(int quantity, Item item, Customer customer) {
        this.quantity = quantity;
        this.item = item;
        this.customer = customer;
    }

    public Order() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
