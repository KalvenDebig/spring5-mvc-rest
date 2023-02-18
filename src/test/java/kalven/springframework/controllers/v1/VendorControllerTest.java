package kalven.springframework.controllers.v1;

import kalven.springframework.api.v1.model.VendorDTO;
import kalven.springframework.controllers.RestResponseEntityExceptionHandler;
import kalven.springframework.services.ResourceNotFoundException;
import kalven.springframework.services.VendorService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/18/23
 */
@ExtendWith(MockitoExtension.class)
class VendorControllerTest extends AbstractRestControllerTest {
    @Mock
    VendorService vendorService;
    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    void getAllVendors() throws Exception {
        Mockito.when(vendorService.getAllVendors())
                .thenReturn(Arrays.asList(new VendorDTO(), new VendorDTO()));

        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendors", Matchers.hasSize(2)));
        Mockito.verify(vendorService).getAllVendors();
    }

    @Test
    void getVendorById() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Liverpool");

        Mockito.when(vendorService.getVendorById(ArgumentMatchers.anyLong())).thenReturn(vendorDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL + "/" + 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("Liverpool")));

        Mockito.verify(vendorService).getVendorById(ArgumentMatchers.anyLong());
    }

    @Test
    void createNewVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Man City");
        vendorDTO.setVendorUrl("/api/v1/vendors/1");
        Mockito.when(vendorService.createNewVendor(ArgumentMatchers.any(VendorDTO.class)))
                .thenReturn(vendorDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("Man City")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url", Matchers.equalTo("/api/v1/vendors/1")));

        Mockito.verify(vendorService).createNewVendor(ArgumentMatchers.any(VendorDTO.class));
    }

    @Test
    void saveVendorByID() throws Exception {
        VendorDTO savedDTO = new VendorDTO();
        savedDTO.setName("Man United");
        savedDTO.setVendorUrl("/api/v1/vendors/1");
        Mockito.when(vendorService.saveVendorByDTO(ArgumentMatchers.anyLong(), ArgumentMatchers.any(VendorDTO.class)))
                .thenReturn(savedDTO);

        mockMvc.perform(MockMvcRequestBuilders.put(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(savedDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("Man United")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url", Matchers.equalTo(VendorController.BASE_URL + "/1")));

        Mockito.verify(vendorService).saveVendorByDTO(ArgumentMatchers.anyLong(), ArgumentMatchers.any(VendorDTO.class));
    }

    @Test
    void patchVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Napoli");
        vendorDTO.setVendorUrl(VendorController.BASE_URL + "/1");

        VendorDTO savedDTO = new VendorDTO();
        savedDTO.setName(vendorDTO.getName());
        savedDTO.setVendorUrl(VendorController.BASE_URL + "/1");
        Mockito.when(vendorService.patchVendor(ArgumentMatchers.anyLong(), ArgumentMatchers.any(VendorDTO.class)))
                .thenReturn(savedDTO);
        mockMvc.perform(MockMvcRequestBuilders.patch(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("Napoli")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url",
                        Matchers.equalTo(VendorController.BASE_URL + "/1")));
        Mockito.verify(vendorService).patchVendor(ArgumentMatchers.anyLong(), ArgumentMatchers.any(VendorDTO.class));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(vendorService).deleteVendorById(ArgumentMatchers.anyLong());
    }

    @Test
    void getByIdNotFound() throws Exception {
        Mockito.when(vendorService.deleteVendorById(ArgumentMatchers.anyLong())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete(VendorController.BASE_URL + "/31")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}