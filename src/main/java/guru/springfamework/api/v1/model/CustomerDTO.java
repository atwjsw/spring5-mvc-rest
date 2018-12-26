package guru.springfamework.api.v1.model;

import guru.springfamework.controllers.v1.CustomerController;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String customer_url;

    public String getCustomer_url() {
        return CustomerController.BASE_URL + "/" + id;
    }
}
