package org.example.services;

import org.example.model.Customer;
import org.example.model.User;
import org.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class CustomerService {
    public List<Customer> dataStore;
    @Autowired
    public CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public String addCustomer(@NotNull @Valid Customer customer) {
        if (customer.getCustomerName().isEmpty() || customer.getPlace().isEmpty() || customer.getMobileNo().isEmpty()) {
            return ("inValid Customer Details");
        } else {
            return customerRepository.save(customer).toString();
        }
    }

    public Customer getCustomerByName(@NotNull @Valid String customerName) {
        return customerRepository.findByCustomerName(customerName);
    }

    public String deleteCustomerByName(@NotNull @Valid String customerName) {
        try {
            customerRepository.deleteByCustomerName(customerName);
            return ("Customer is Deleted Successfully");
        } catch(Error error) {
            return ("Failed to delete the Customer" + error);
        }
    }

    public String deleteCustomers() {
        try {
            customerRepository.deleteAll();
            return ("all Customers Deleted Successfully");
        } catch(Error error) {
            return ("Failed to delete all Customers" + error);
        }
    }
}
