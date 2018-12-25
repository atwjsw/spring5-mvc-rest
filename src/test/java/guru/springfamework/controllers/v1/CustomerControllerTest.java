package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.exceptions.NotFoundException;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    List<CustomerDTO> customerDTOs;
    CustomerDTO customerDTO1;
    CustomerDTO customerDTO2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
        customerDTO1 = new CustomerDTO();
        customerDTO1.setId(1L);
        customerDTO2 = new CustomerDTO();
        customerDTO2.setId(2L);
        customerDTOs = new ArrayList<>();
        customerDTOs.add(customerDTO1);
        customerDTOs.add(customerDTO2);
    }

    @Test
    public void getAllCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(customerDTOs);
        mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getAllCustomersReturnEmptyList() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(0)));
    }

    @Test
    public void getCustomerById() throws Exception{
        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO1);
        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void getCustomerByIdNotFound() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/api/v1/customers/-1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", equalTo("entity.not.found")));
    }

    @Test
    public void getCustomerByIdBadInputId() throws Exception {

        mockMvc.perform(get("/api/v1/customers/abc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", equalTo("badinput.numberformat")));

        verifyZeroInteractions(customerService);
    }
}
