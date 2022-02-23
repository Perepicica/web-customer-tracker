package org.my.springDemo.controller;

import org.my.springDemo.entity.Customer;
import org.my.springDemo.service.CustomerService;
import org.my.springDemo.util.SortUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    //injecting the customer service
    @Autowired
    private CustomerService customerService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }

    @GetMapping("/list")
    public String listCustomers(Model model, @RequestParam(required = false) String sort) {
        //get customers from service
        List<Customer> theCustomers;
        // check for sort field
        if (sort != null) {
            int theSortField = Integer.parseInt(sort);
            theCustomers = customerService.getCustomers(theSortField);
        }
        else {
            // no sort field provided ... default to sorting by last name
            theCustomers = customerService.getCustomers(SortUtils.LAST_NAME);
        }
        //add the customers to the model
        model.addAttribute("customers", theCustomers);
        return "list-customers";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        //create model attribute to bind form data
        model.addAttribute("customer",new Customer());
        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(
            @Valid @ModelAttribute("customer") Customer theCustomer,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "customer-form";
        //save the customer using service
        customerService.saveCustomer(theCustomer);
        return "redirect:/customer/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("customerId") int id,
                                    Model model) {
        //get the customer from service
        Customer theCustomer = customerService.getCustomer(id);
        //set customer as a model attribute tp prepopulate the form
        model.addAttribute("customer",theCustomer);
        return "customer-form";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam("customerId") int id){
        customerService.deleteCustomer(id);
        return "redirect:/customer/list";
    }

    @GetMapping("/search")
    public String searchCustomers(@RequestParam("theSearchName") String theSearchName,
                                  Model model) {
        List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
        model.addAttribute("customers", theCustomers);
        return "list-customers";
    }
}
