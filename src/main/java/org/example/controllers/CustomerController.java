package org.example.controllers;

import org.example.model.Customer;
import org.example.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/getAllCustomers")
    public List<Customer> getAllUsers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/addCustomer")
    public String getAllUsers(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @GetMapping("/getCustomerById")
    public Customer getCustomerById(@RequestParam String customerName) {
        return customerService.getCustomerByName(customerName);
    }

    @DeleteMapping("/deleteCustomerById")
    public String deleteCustomerById(@RequestParam String customerName) {
        return customerService.deleteCustomerByName(customerName);
    }

    @DeleteMapping("/deleteAllCustomers")
    public String deleteAllCustomers() {
        return customerService.deleteCustomers();
    }
}
