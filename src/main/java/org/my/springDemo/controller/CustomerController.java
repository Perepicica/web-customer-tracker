package org.my.springDemo.controller;

import org.my.springDemo.entity.Customer;
import org.my.springDemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    //injecting the customer service
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomers(Model model) {
        //get customers from service
        List<Customer> theCustomers = customerService.getCustomers();
        //add the customers to the model
        model.addAttribute("customers", theCustomers);
        return "list-customers";
    }
}
