package guru.springfamework.api.v1.model;

import lombok.Data;

@Data
public class CustomerDTO {
    public static final String CUSTOMERS_URL = "/api/v1/customers/";
    private Long id;
    private String firstName;
    private String lastName;
    private String customer_url;

    public String getCustomer_url() {
        return CUSTOMERS_URL + id;
    }
}
