package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;

    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll().stream().map(vendorMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor with id '" + id + "' not found"));
    }

    @Override
    public VendorDTO createOrUpdateVendor(VendorDTO vendorDTO) {
        Long id = vendorDTO.getId();
        if ( id != null && ! vendorRepository.existsById(id))
            throw new ResourceNotFoundException("Vendor with id " + id + " not found");
        return vendorMapper.toDTO(vendorRepository.save(vendorMapper.toEntity(vendorDTO)));
    }

    @Override
    public VendorDTO patchVendor(VendorDTO vendorDTO) {
        return vendorRepository.findById(vendorDTO.getId())
                .map(customer -> {
                    if (vendorDTO.getName() != null)
                        customer.setName(vendorDTO.getName());
                    return vendorMapper.toDTO(vendorRepository.save(customer));
                })
                .orElseThrow(()-> new ResourceNotFoundException("Vendor with id " + vendorDTO.getId() + " not found"));
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
