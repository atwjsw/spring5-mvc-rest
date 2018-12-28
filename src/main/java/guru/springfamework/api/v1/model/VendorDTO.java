package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import guru.springfamework.controllers.v1.VendorController;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VendorDTO {

    private Long id;

    @ApiModelProperty(value = "name of the vendor", required = true)
    private String name;

    @JsonProperty("vendor_url")
    private String vendorUrl;

    public String getVendorUrl() {
        return VendorController.BASE_URL + "/" + id;
    }
}
