package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerListDTO> getListofCustomers() {
        return new ResponseEntity<CustomerListDTO>(
                new CustomerListDTO(customerService.getAllCustomers()),
                HttpStatus.OK
        );
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long customerId) {
        return new ResponseEntity<CustomerDTO>(
                customerService.getCustomerById(customerId),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<CustomerDTO>(
                customerService.createOrUpdateCustomer(customerDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(customerId);
        return new ResponseEntity<CustomerDTO>(
                customerService.createOrUpdateCustomer(customerDTO),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(customerId);
        return new ResponseEntity<CustomerDTO>(
                customerService.patchCustomer(customerDTO),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
