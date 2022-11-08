package com.example.demo.control;

import com.example.demo.DB.Customer;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.example.demo.DB.Item;
import com.example.demo.DB.ItemRepo;
import com.example.demo.services.DBService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/buy")
public class Buy {

    @Autowired 
    ItemRepo ir;

    @Autowired
    DBService dbService;
    
    @RequestMapping("")
    public String buy(@RequestParam int itemId, Model model, HttpSession session)
    {
        if(session.getAttribute("user") == null) return "no_user";

        Optional<Item> item = ir.findById(itemId);

        if (!item.isPresent()) return "redirect:/404";
        else if (item.get().getQuantity() == 0) return "redirect:/404";
        {
            session.setAttribute("purchase", item.get());
            return "buy";
        }
    }

    @RequestMapping("/confirm")
    public String confirmation(@RequestParam int id, @RequestParam int quantity, HttpSession session, Model model)
    {
        if(session.getAttribute("user") == null) return "no_user";
        if(session.getAttribute("purchase") == null) return "redirect:404";
        Customer c = (Customer)session.getAttribute("user");
        Item item = (Item)session.getAttribute("purchase");
        if(item.getId() != id) return "redirect:404";

        try
        {
            dbService.updateOrder(c, item, quantity);
            model.addAttribute("purchase", item);
            model.addAttribute("quantity", quantity);
            model.addAttribute("delivery_date", new java.sql.Date(System.currentTimeMillis() + 7 * 24 * 3600 * 1000));
            session.removeAttribute("purchase");
        }
        catch (Exception e)
        {
            System.out.println(e);
            return "redirect:404";
        }

        return "confirmation";
    }
}
