package guru.springfamework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest {

    @Mock
    private VendorService vendorService;

    @InjectMocks
    private VendorController vendorController;

    MockMvc mockMvc;

    VendorDTO v1;
    VendorDTO v2;
    List<VendorDTO> vendors;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();

        v1 = new VendorDTO();
        v1.setId(1L);
        v1.setName("vendor1");
        v2 = new VendorDTO();
        v2.setId(2L);
        v2.setName("vendor2");
        vendors = new ArrayList<>();
        vendors.add(v1);
        vendors.add(v2);

    }

    @Test
    public void getListOfVendors() throws Exception {
        when(vendorService.getAllVendors()).thenReturn(vendors);

        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void getListOfVendorsReturnEmptyList() throws Exception {
        when(vendorService.getAllVendors()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(0)));
    }

    @Test
    public void getVendorById() throws Exception {
        when(vendorService.getVendorById(anyLong())).thenReturn(v1);

        mockMvc.perform(get(VendorController.BASE_URL +"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void getVendorByIdNotFound() throws Exception {
        when(vendorService.getVendorById(anyLong())).thenThrow(new ResourceNotFoundException());

        mockMvc.perform(get(VendorController.BASE_URL +"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", equalTo("entity.not.found")));
    }

    @Test
    public void getVendorByIdBadInputId() throws Exception {

        mockMvc.perform(get(VendorController.BASE_URL + "/abc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", equalTo("badinput.numberformat")));

        verifyZeroInteractions(vendorService);
    }

    @Test
    public void createVendor() throws Exception{

        VendorDTO saveVendorDTO = new VendorDTO();
        saveVendorDTO.setId(1L);
        saveVendorDTO.setName("Vendor1");
        when(vendorService.createOrUpdateVendor(any())).thenReturn(saveVendorDTO);

        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(saveVendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Vendor1")));
    }

    @Test
    public void updateVendor() throws Exception {

        VendorDTO saveVendorDTO = new VendorDTO();
        saveVendorDTO.setId(1L);
        saveVendorDTO.setName("Vendor1");
        when(vendorService.createOrUpdateVendor(any())).thenReturn(saveVendorDTO);

        mockMvc.perform(put(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(saveVendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Vendor1")));
    }

    @Test
    public void updateVendorNotFound() throws Exception {

        VendorDTO saveVendorDTO = new VendorDTO();
        saveVendorDTO.setId(1L);
        saveVendorDTO.setName("Vendor1");
        when(vendorService.createOrUpdateVendor(any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(saveVendorDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", equalTo("entity.not.found")));
    }

    @Test
    public void patchVendor() throws Exception {

        VendorDTO saveVendorDTO = new VendorDTO();
        saveVendorDTO.setId(1L);
        saveVendorDTO.setName("Vendor1");
        when(vendorService.patchVendor(any())).thenReturn(saveVendorDTO);

        mockMvc.perform(patch(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(saveVendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Vendor1")));
    }

    @Test
    public void patchVendorNotFound() throws Exception {

        VendorDTO saveVendorDTO = new VendorDTO();
        saveVendorDTO.setId(1L);
        saveVendorDTO.setName("Vendor1");
        when(vendorService.patchVendor(any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(patch(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(saveVendorDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", equalTo("entity.not.found")));
    }

    @Test
    public void deleteVendorById() throws Exception {

        mockMvc.perform(delete(VendorController.BASE_URL +"/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).deleteVendorById(anyLong());

    }
}