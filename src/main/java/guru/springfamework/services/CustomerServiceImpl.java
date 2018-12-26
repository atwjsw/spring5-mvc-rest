package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.exceptions.NotFoundException;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .orElseThrow(() -> new NotFoundException("Customer with id " + id + " not found"));
    }

    @Override
    public CustomerDTO createOrUpdateCustomer(CustomerDTO customerDTO) {
        Long id = customerDTO.getId();
        if ( id != null && !customerRepository.existsById(id))
            throw new NotFoundException("Customer with id " + id + " not found");
        return customerMapper.customerToCustomerDTO(customerRepository.save(customerMapper.customerDTOToCustomer(customerDTO)));
    }

    @Override
    public CustomerDTO patchCustomer(CustomerDTO customerDTO) {
        return customerRepository.findById(customerDTO.getId())
                .map(customer -> {
                    if (customerDTO.getFirstName() != null)
                        customer.setFirstName(customerDTO.getFirstName());
                    if (customerDTO.getLastName() != null)
                        customer.setLastName(customerDTO.getLastName());
                    return customerMapper.customerToCustomerDTO(customerRepository.save(customer));
                })
                .orElseThrow(()-> new NotFoundException("Customer with id " + customerDTO.getId() + " not found"));
    }
}
