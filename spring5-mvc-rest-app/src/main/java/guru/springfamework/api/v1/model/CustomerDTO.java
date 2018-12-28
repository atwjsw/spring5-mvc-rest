package guru.springfamework.api.v1.model;

import guru.springfamework.controllers.v1.CustomerController;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;

    @ApiModelProperty(value = "This is the first name", required = true)
    private String firstName;

    @ApiModelProperty(value = "This is the last name", required = true)
    private String lastName;
    private String customer_url;

    public String getCustomer_url() {
        return CustomerController.BASE_URL + "/" + id;
    }
}
