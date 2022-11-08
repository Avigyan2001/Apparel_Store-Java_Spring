package com.example.demo.control;

import org.springframework.ui.Model;

import com.example.demo.DB.Customer;
import com.example.demo.DB.CustomerRepo;
import com.example.demo.services.Validate;

import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class Signup {

    @Autowired
    CustomerRepo cr;

    @Autowired
    Validate vd;

    @RequestMapping({"", "/"})
    public String home()
    {
        return "signup";
    }

    @RequestMapping("/submit")
    public String submit(Customer c, Model model)
    {
        try
        {
            if(!vd.validateEmail(c.getEmail())) throw new Exception();
            cr.save(c);
            model.addAttribute("success", true);
            model.addAttribute("username", c.getName());
        }
        catch(DataIntegrityViolationException e)
        {
            if(e.getCause() instanceof PropertyValueException) model.addAttribute("error", "Username and email cannot be empty.");
            else if(e.getCause() instanceof ConstraintViolationException) model.addAttribute("error", "Email already registered."); 

            model.addAttribute("success", false);
        }
        catch(Exception e)
        {
            model.addAttribute("error", "Could not validate email-id.");
            model.addAttribute("success", false);
        }

        return "signup_submit";
    }

}
