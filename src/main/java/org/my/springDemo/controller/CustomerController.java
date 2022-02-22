package org.my.springDemo.controller;

import org.my.springDemo.dao.CustomerDAO;
import org.my.springDemo.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    //injecting the customer dao
    @Autowired
    private CustomerDAO customerDAO;

    @GetMapping("/list")
    public String listCustomers(Model model) {
        //get customers from dao
        List<Customer> theCustomers = customerDAO.getCustomers();
        //add the customers to the model
        model.addAttribute("customers", theCustomers);
        return "list-customers";
    }
}
