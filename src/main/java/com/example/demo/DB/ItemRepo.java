package com.example.demo.DB;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends CrudRepository <Item, Integer>{
    public List<Item> findAll();
    public Optional<Item> findById(int id); 

    public List<Item> findByGenderAndSeasonAndQuantityGreaterThan(String gender, String season, int quantity);
    public List<Item> findByGenderAndArrivalDateGreaterThanAndQuantityGreaterThan(String gender, Date arrivalDate, int quantity);

    public List<Item> findFirst8ByNameContainingIgnoreCaseAndQuantityGreaterThan(String name, int quantity);
}
