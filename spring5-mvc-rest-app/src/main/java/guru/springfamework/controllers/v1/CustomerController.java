package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static guru.springfamework.controllers.v1.CustomerController.BASE_URL;

@Api(description = "This is my customer controller")
@RestController
@RequestMapping(BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "This will get a list of customers.", notes = "some notes anout the API.")
    @GetMapping
    public CustomerListDTO getListOfCustomers() {
        return new CustomerListDTO(customerService.getAllCustomers());
    }

    @GetMapping("/{customerId}")
    public CustomerDTO getCustomerById(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createOrUpdateCustomer(customerDTO);
    }

    @PutMapping("/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(customerId);
        return customerService.createOrUpdateCustomer(customerDTO);
    }

    @PatchMapping("/{customerId}")
    public CustomerDTO patchCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(customerId);
        return customerService.patchCustomer(customerDTO);
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);
    }
}
