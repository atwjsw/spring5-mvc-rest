package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {

    @Mock
    private VendorRepository vendorRepository;

    private VendorService vendorService;

    private Vendor v1;
    private Vendor v2;
    private List<Vendor> vendors;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
        v1 = new Vendor();
        v1.setId(1L);
        v1.setName("vendor1");
        v2 = new Vendor();
        v2.setId(2L);
        v2.setName("vendor2");
        vendors = new ArrayList<>();
        vendors.add(v1);
        vendors.add(v2);
    }

    @Test
    public void getAllVendors() {
        when(vendorRepository.findAll()).thenReturn(vendors);
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();
        assertThat(vendorDTOS.size(), equalTo(2));
    }

    @Test
    public void getVendorById() {
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(v1));
        VendorDTO vendorDTO = vendorService.getVendorById(1L);
        assertThat(vendorDTO.getId(), equalTo(1L));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound() {
        when(vendorRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException());
        VendorDTO vendorDTO = vendorService.getVendorById(1L);
        assertNull(vendorDTO);
    }

    @Test
    public void createOrUpdateVendor() {
        when(vendorRepository.save(any())).thenReturn(v1);
        VendorDTO savedVendorDTO = vendorService.createOrUpdateVendor(new VendorDTO());
        assertNotNull(savedVendorDTO);
        assertThat(savedVendorDTO.getId(), equalTo(1L));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void createOrUpdateVendorNotFound() {
        when(vendorRepository.existsById(any())).thenReturn(false);
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(1L);

        VendorDTO savedVendorDTO = vendorService.createOrUpdateVendor(vendorDTO);
        assertNull(savedVendorDTO);
        verifyZeroInteractions(vendorRepository.save(any()));
    }

    @Test
    public void patchVendor() {
    }

    @Test
    public void deleteVendorById() {
        vendorService.deleteVendorById(1L);
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}