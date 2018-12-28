package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static guru.springfamework.controllers.v1.VendorController.BASE_URL;

@Api(description = "Api for vendor operations")
@RestController
@RequestMapping(BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @ApiOperation(value = "get a list of vendors", notes = "return 404 when id not found")
    @GetMapping
    public VendorListDTO getListOfVendors() {
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @ApiOperation(value = "get a vendor by id", notes = "return 404 when id not found")
    @GetMapping("/{id}")
    public VendorDTO getVendorById(@PathVariable  Long id) {
        return vendorService.getVendorById(id);
    }

    @ApiOperation(value = "create a new vendor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createOrUpdateVendor(vendorDTO);
    }

    @ApiOperation(value = "update an existing vendor", notes = "return 404 when id not found")
    @PutMapping("/{customerId}")
    public VendorDTO updateVendor(@PathVariable Long customerId, @RequestBody VendorDTO vendorDTO) {
        vendorDTO.setId(customerId);
        return vendorService.createOrUpdateVendor(vendorDTO);
    }

    @ApiOperation(value = "modify an existing vendor", notes = "return 404 when id not found")
    @PatchMapping("/{customerId}")
    public VendorDTO patchVendor(@PathVariable Long customerId, @RequestBody VendorDTO vendorDTO) {
        vendorDTO.setId(customerId);
        return vendorService.patchVendor(vendorDTO);
    }

    @ApiOperation(value = "delete an existing vendor")
    @DeleteMapping("/{id}")
    public void deleteVendorById(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
    }
}
