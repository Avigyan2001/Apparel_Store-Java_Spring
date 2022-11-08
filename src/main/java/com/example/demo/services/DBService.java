package com.example.demo.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.demo.DB.Customer;
import com.example.demo.DB.CustomerRepo;
import com.example.demo.DB.Item;
import com.example.demo.DB.ItemRepo;
import com.example.demo.DB.Order;
import com.example.demo.DB.OrderRepo;
import com.example.demo.custom.Pair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    OrderRepo or;

    @Autowired
    ItemRepo ir;

    @Autowired
    CustomerRepo cr;

    private Double toDouble(double d)
    {
        return d;
    }
   
    public void updateOrder(Customer c, Item i, int quantity) throws RuntimeException
    {
        int previousQuantity = i.getQuantity();
        int currentQuantity = previousQuantity - quantity;

        if(currentQuantity < 0) throw new RuntimeException("Quantity too high.");

        i.setQuantity(currentQuantity);

        ir.save(i);
        or.save(new Order(quantity, i, c));
    }

    public List <Pair> getHistory(Customer c)
    {
        List <Order> orderList = or.findByCustomer(c);
        List <Pair> historyList = new ArrayList <>();

        for(var order : orderList)
        {
            Pair p = new Pair(order.getItem(), order.getQuantity());
            historyList.add(p);
        }

        return historyList;
    }

    public void changePreference(Customer c, String prefenrence)
    {
        c.setPreference(prefenrence);
        cr.save(c);
    }


    public List <Item> recommendList(Customer customer)
    {
        String gender = customer.getGender();
        String preference = customer.getPreference();

        int month = Calendar.getInstance().get(Calendar.MONTH);

        String season = "summer";
        if(month <= 1 || month >= 9) season = "winter";

        List <Item> l = new ArrayList<>();

        if(preference.equals("season"))
        {
            l = ir.findByGenderAndSeasonAndQuantityGreaterThan(gender, season, 0);
        }
        else
        {
            Date date = new Date(System.currentTimeMillis() - (1l * 30l * 24l * 3600l * 1000l));
            l = ir.findByGenderAndArrivalDateGreaterThanAndQuantityGreaterThan(gender, date, 0);
        }

        double totalSpending = 0;
        double countSpending = 0;

        List <Order> orderList = or.findByCustomer(customer);
        for(Order order : orderList)
        {
            totalSpending += order.getItem().getPrice();
            countSpending += 1;
        }

        Double averageSpending = (countSpending == 0 ? 0 : totalSpending / countSpending);

        l.sort((i1, i2) -> this.toDouble(Math.abs(i1.getActualPrice() - averageSpending)).compareTo(this.toDouble(Math.abs(i2.getActualPrice() - averageSpending))));

        return l;
    }

    public List <Item> search(String searchString)
    {
        if(searchString == null)
        return new ArrayList<>();
        else
        return ir.findFirst8ByNameContainingIgnoreCaseAndQuantityGreaterThan(searchString, 0);
    }
}
