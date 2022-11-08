package com.example.demo.DB;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends CrudRepository <Customer, Integer> {
    public List<Customer> findByEmail(String email);
}
