package guru.springfamework.services;

//import guru.springfamework.api.v1.model.CustomerDTO;

import guru.springfamework.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long Id);

    CustomerDTO createOrUpdateCustomer(CustomerDTO customerDTO);

    CustomerDTO patchCustomer(CustomerDTO customerDTO);

    void deleteCustomerById(Long id);

}
