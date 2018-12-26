package guru.springfamework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.exceptions.ResourceNotFoundException;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
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
        mockMvc.perform(get(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getAllCustomersReturnEmptyList() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(new ArrayList<>());
        mockMvc.perform(get(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(0)));
    }

    @Test
    public void getCustomerById() throws Exception{
        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO1);
        mockMvc.perform(get(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void getCustomerByIdNotFound() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", equalTo("entity.not.found")));
    }

    @Test
    public void getCustomerByIdBadInputId() throws Exception {

        mockMvc.perform(get(CustomerController.BASE_URL + "/abc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", equalTo("badinput.numberformat")));

        verifyZeroInteractions(customerService);
    }

    @Test
    public void createCustomer() throws Exception{

        CustomerDTO saveCustomerDTO = new CustomerDTO();
        saveCustomerDTO.setId(1L);
        saveCustomerDTO.setFirstName("John");
        saveCustomerDTO.setLastName("Smith");
        when(customerService.createOrUpdateCustomer(any())).thenReturn(saveCustomerDTO);

        mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(saveCustomerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.firstName", equalTo("John")))
                .andExpect(jsonPath("$.lastName", equalTo("Smith")));
    }

    @Test
    public void updateCustomer() throws Exception {

        CustomerDTO saveCustomerDTO = new CustomerDTO();
        saveCustomerDTO.setId(1L);
        saveCustomerDTO.setFirstName("John");
        saveCustomerDTO.setLastName("Smith");
        when(customerService.createOrUpdateCustomer(any())).thenReturn(saveCustomerDTO);

        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(saveCustomerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.firstName", equalTo("John")))
                .andExpect(jsonPath("$.lastName", equalTo("Smith")));
    }

    @Test
    public void updateCustomerNotFound() throws Exception {

        CustomerDTO saveCustomerDTO = new CustomerDTO();
        saveCustomerDTO.setId(1L);
        saveCustomerDTO.setFirstName("John");
        saveCustomerDTO.setLastName("Smith");
        when(customerService.createOrUpdateCustomer(any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(saveCustomerDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", equalTo("entity.not.found")));
    }

    @Test
    public void patchCustomer() throws Exception {

        CustomerDTO saveCustomerDTO = new CustomerDTO();
        saveCustomerDTO.setId(1L);
        saveCustomerDTO.setFirstName("John");
        saveCustomerDTO.setLastName("Smith");
        when(customerService.patchCustomer(any())).thenReturn(saveCustomerDTO);

        mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(saveCustomerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.firstName", equalTo("John")))
                .andExpect(jsonPath("$.lastName", equalTo("Smith")));
    }

    @Test
    public void patchCustomerNotFound() throws Exception {

        CustomerDTO saveCustomerDTO = new CustomerDTO();
        saveCustomerDTO.setId(1L);
        saveCustomerDTO.setFirstName("John");
        saveCustomerDTO.setLastName("Smith");
        when(customerService.patchCustomer(any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(saveCustomerDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", equalTo("entity.not.found")));
    }

    @Test
    public void deleteCustomer() throws Exception {

             mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

             verify(customerService, times(1)).deleteCustomerById(anyLong());
    }
}
