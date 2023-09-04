package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controllers.CustomerController;
import org.example.model.Customer;
import org.example.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerControllerTests {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    List<Customer> mockCustomer = Collections.singletonList(new Customer(1, "customer", "Bangalore", "1234567890"));

    @Test
    public void testGetAllUsers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(mockCustomer);
        mockMvc.perform(get("/getAllCustomers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].customerName").value("customer"));
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    public void testAddCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerName("customer");

        mockMvc.perform(MockMvcRequestBuilders.post("/addCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetCustomerByName() throws Exception {
        String customerName = "customer";
        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        when(customerService.getCustomerByName(customerName)).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.get("/getCustomerByName")
                        .param("customerName", customerName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteCustomerByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteCustomerByName")
                        .param("customerName", "customer"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteAllCustomers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteAllCustomers"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
