package services;

import org.example.model.Customer;
import org.example.repository.CustomerRepository;
import org.example.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class CustomerServiceTests {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    Customer mockCustomer = new Customer(100, "customer", "password", "user@example.com");

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        customerService = new CustomerService();
        customerService.customerRepository = customerRepository;

        List<Customer> testData = new ArrayList<>();
        testData.add(new Customer(1,"customer", "Bangalore", "1234567890"));
        customerService.dataStore = testData;
    }


    @Test
    void GetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(customerService.dataStore);
        List<Customer> users = customerService.getAllCustomers();
        assertEquals(1, users.size());
    }

    @Test
    void AddCustomerValid() {
        when(customerRepository.save(mockCustomer)).thenReturn(mockCustomer);
        String result = customerService.addCustomer(mockCustomer);
        assertEquals(mockCustomer.toString(), result);
    }

    @Test
    void AddCustomerInvalid() {
        Customer invalidCustomer = new Customer(100, "customer", "Bangalore", "");
        String result = customerService.addCustomer(invalidCustomer);
        assertEquals("inValid Customer Details", result);
    }

    @Test
    void GetCustomerByName() {
        when(customerRepository.findByCustomerName("customer")).thenReturn(mockCustomer);
        Customer customer = customerService.getCustomerByName("customer");
        assertEquals(mockCustomer.getCustomerName(), customer.getCustomerName());
    }

    @Test
    void DeleteCustomerByName() {
        when(customerRepository.deleteByCustomerName("customer")).thenReturn("Customer is Deleted Successfully");
        customerService.deleteCustomerByName("customer");
    }

    @Test
    void DeleteCustomers() {
        customerService.deleteCustomers();
    }

}
