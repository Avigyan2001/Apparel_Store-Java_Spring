package com.example.demo.DB;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends CrudRepository <Order, Integer> {
    public List <Order> findByCustomer(Customer c);
}
