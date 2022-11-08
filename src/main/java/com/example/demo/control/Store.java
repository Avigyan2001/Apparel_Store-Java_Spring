package com.example.demo.control;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.demo.DB.Customer;
import com.example.demo.DB.Item;
import com.example.demo.custom.Pair;
import com.example.demo.services.DBService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/store")
public class Store {

    @Autowired
    DBService dbService;

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response)
    {
        response.addHeader("Cache-Control","no-cache, no-store, must-revalidate");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Expires", "0");
    }
    
    @RequestMapping({"", "/"})
    public String home(HttpSession session, Model model)
    {
        if(session.getAttribute("user") == null)
        {
            return "no_user";
        }
        else
        {
            Customer c = (Customer)session.getAttribute("user");
            List <Item> l = dbService.recommendList(c);
            model.addAttribute("items", l);
            return "store";
        }
    }

    @RequestMapping("/history")
    public String history(HttpSession session, Model model)
    {
        if(session.getAttribute("user") == null) return "no_user";

        Customer c = (Customer)session.getAttribute("user");

        List <Pair> historyList = dbService.getHistory(c);
        model.addAttribute("history", historyList);

        return "history";
    }

    @RequestMapping("/change")
    public String change(@RequestParam String preference, HttpSession session)
    {
        if(session.getAttribute("user") == null) return "no_user";

        Customer c = (Customer)session.getAttribute("user");

        try
        {
            dbService.changePreference(c, preference);
            return "forward:/store/history";
        }
        catch(Exception e)
        {
            return "redirect:404";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session)
    {
        session.setAttribute("user", null);
        session.setAttribute("purchase", null);
        return "forward:/";
    }

    @RequestMapping("/search")
    public String search(@RequestParam(required = false) String phrase, HttpSession session, Model model)
    {
        if(session.getAttribute("user") == null) return "no_user";

        List <Item> l = dbService.search(phrase);
        model.addAttribute("items", l);

        return "search";
    }
}
