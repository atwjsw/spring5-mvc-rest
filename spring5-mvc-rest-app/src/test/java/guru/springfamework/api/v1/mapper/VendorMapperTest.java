package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class VendorMapperTest {

    private VendorMapper vendorMapper;
    private Vendor vendor;
    private VendorDTO vendorDTO;

    @Before
    public void setUp() throws Exception {
        vendorMapper = VendorMapper.INSTANCE;
        vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("vendor1");

        vendorDTO = new VendorDTO();
        vendorDTO.setId(1L);
        vendorDTO.setName("vendor1");
    }

    @Test
    public void toDTO() {
        VendorDTO mappedVendorDTO = vendorMapper.toDTO(vendor);
        assertNotNull(mappedVendorDTO);
        assertThat(mappedVendorDTO.getId(), equalTo(1L));
        assertThat(mappedVendorDTO.getName(), equalTo("vendor1"));
        assertThat(mappedVendorDTO.getVendorUrl(), equalTo(VendorController.BASE_URL + "/1"));
    }

    @Test
    public void toDTONullValue() {
        VendorDTO mappedVendorDTO = vendorMapper.toDTO(null);
        assertNull(mappedVendorDTO);
    }


    @Test
    public void toEntity() {
        Vendor mappedVendor = vendorMapper.toEntity(vendorDTO);
        assertNotNull(mappedVendor);
        assertThat(mappedVendor.getId(), equalTo(1L));
        assertThat(mappedVendor.getName(), equalTo("vendor1"));
    }

    @Test
    public void toEntityNullValue() {
        Vendor mappedVendor = vendorMapper.toEntity(null);
        assertNull(mappedVendor);
    }
}