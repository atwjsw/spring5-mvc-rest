package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {

    List<VendorDTO> getAllVendors();

    VendorDTO getVendorById(Long Id);

    VendorDTO createOrUpdateVendor(VendorDTO customerDTO);

    VendorDTO patchVendor(VendorDTO customerDTO);

    void deleteVendorById(Long id);

}
