package com.example.demo.control;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.demo.DB.Customer;
import com.example.demo.DB.CustomerRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class Login {
    
    @Autowired
    CustomerRepo cr;

    @RequestMapping({"", "/"})
    public String login()
    {
        return "login";
    }

    @RequestMapping("/submit")
    public String submit(@RequestParam String name, @RequestParam String email, HttpSession session, Model model)
    {
        List <Customer> clist = cr.findByEmail(email);
        
        if(clist.isEmpty())
        {
            model.addAttribute("success", false);
            model.addAttribute("message", "Email not registered.");
        }
        else if(!clist.get(0).getName().equals(name))
        {
            model.addAttribute("success", false);
            model.addAttribute("message", "Incorrect username.");
        }
        else
        {
            session.setAttribute("user", clist.get(0));
            model.addAttribute("success", true);
        }
        
        return "login_submit";
    }
}
