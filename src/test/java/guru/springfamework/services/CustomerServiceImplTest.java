package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.exceptions.NotFoundException;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    List<Customer> customers;
    Customer customer1;
    Customer customer2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);

        customer1 = new Customer();
        customer1.setId(1L);
        customer2 = new Customer();
        customer2.setId(2L);
        customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
    }

    @Test
    public void getAllCustomers() {
        when(customerRepository.findAll()).thenReturn(customers);
        List<CustomerDTO> customerDTOs = customerService.getAllCustomers();
        assertNotNull(customerDTOs);
        assertThat(customerDTOs.size(), equalTo(2));
    }

    @Test
    public void getCustomerById() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer1));
        CustomerDTO customerDTO = customerService.getCustomerById(1L);
        assertNotNull(customerDTO);
        assertThat(customerDTO.getId(), equalTo(1L));
        assertThat(customerDTO.getCustomer_url(), equalTo("/api/v1/customers/1"));
    }

    @Test(expected = NotFoundException.class)
    public void getCustomerByIdReturnNull() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        CustomerDTO customerDTO = customerService.getCustomerById(1L);
        assertNull(customerDTO);
    }

    @Test
    public void createOrUpdateCustomer() {
        when(customerRepository.save(any())).thenReturn(customer1);
        CustomerDTO savedCustomerDTO = customerService.createOrUpdateCustomer(new CustomerDTO());
        assertNotNull(savedCustomerDTO);
        assertThat(savedCustomerDTO.getId(), equalTo(1L));

    }
}