package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
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
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        assertThat(customerDTO.getId(), equalTo(1L));
        assertThat(customerDTO.getFirstName(), equalTo("John"));
        assertThat(customerDTO.getLastName(), equalTo("Smith"));
        assertThat(customerDTO.getCustomer_url(), equalTo("/shop/customers/1"));
    };

    @Test
    public void customerToCustomerDTONullValue() {
        Customer customer = null;
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        assertNull(customer);
    };
}