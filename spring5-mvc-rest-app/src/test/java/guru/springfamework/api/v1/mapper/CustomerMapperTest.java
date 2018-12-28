package guru.springfamework.api.v1.mapper;

//import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.v1.CustomerController;
import guru.springfamework.domain.Customer;
import guru.springfamework.model.CustomerDTO;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class CustomerMapperTest {

    CustomerMapper customerMapper;

    @Before
    public void setUp() throws Exception {
        customerMapper = CustomerMapper.INSTANCE;
    }

    @Test
    public void customerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Smith");
        CustomerDTO customerDTO = customerMapper.toDTO(customer);
        assertThat(customerDTO.getId(), equalTo(1L));
        assertThat(customerDTO.getFirstName(), equalTo("John"));
        assertThat(customerDTO.getLastName(), equalTo("Smith"));
        assertThat(customerDTO.getCustomerUrl(), equalTo(CustomerController.BASE_URL + "/1"));
    };

    @Test
    public void customerToCustomerDTONullValue() {
        Customer customer = null;
        CustomerDTO customerDTO = customerMapper.toDTO(customer);
        assertNull(customerDTO);
    };

    @Test
    public void customerDTOToCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Smith");
        customerDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");
        Customer customer = customerMapper.toEntity(customerDTO);
        assertThat(customer.getId(), equalTo(1L));
        assertThat(customer.getFirstName(), equalTo("John"));
        assertThat(customer.getLastName(), equalTo("Smith"));
    }

    @Test
    public void customerDTOToCustomerNullValue() {
        CustomerDTO customerDTO = null;
        Customer customer = customerMapper.toEntity(customerDTO);
        assertNull(customer);
    };
}