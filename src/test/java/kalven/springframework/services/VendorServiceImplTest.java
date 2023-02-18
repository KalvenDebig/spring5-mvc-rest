package kalven.springframework.services;

import kalven.springframework.api.v1.mapper.VendorMapper;
import kalven.springframework.api.v1.model.CustomerDTO;
import kalven.springframework.api.v1.model.VendorDTO;
import kalven.springframework.api.v1.model.VendorListDTO;
import kalven.springframework.domain.Vendor;
import kalven.springframework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/18/23
 */
@ExtendWith(MockitoExtension.class)
class VendorServiceImplTest {
    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    @BeforeEach
    void setUp() {
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    void getVendorById() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("some name");
        Mockito.when(vendorRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(vendor));
        VendorDTO vendorDTO = vendorService.getVendorById(1L);
        assertEquals("some name", vendorDTO.getName());
    }

    @Test
    void getAllVendors() {
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());
        Mockito.when(vendorRepository.findAll()).thenReturn(vendors);
        List<VendorDTO> actual = vendorService.getAllVendors();
        assertEquals(vendors.size(), actual.size());
    }

    @Test
    void createNewVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("some name");
        Vendor savedVendor = new Vendor();
        savedVendor.setId(1L);
        savedVendor.setName(vendorDTO.getName());
        Mockito.when(vendorRepository.save(ArgumentMatchers.any(Vendor.class))).thenReturn(savedVendor);
        VendorDTO savedDTO = vendorService.createNewVendor(vendorDTO);

        assertEquals(savedVendor.getName(), savedDTO.getName());
        assertEquals("/api/v1/vendors/1", savedDTO.getVendorUrl());
    }

    @Test
    void saveVendorByDTO() {
        VendorDTO passedInDTO = new VendorDTO();
        passedInDTO.setName("Inter");
        Vendor savedVendor = new Vendor();
        savedVendor.setName(passedInDTO.getName());
        savedVendor.setId(1L);
        Mockito.when(vendorRepository.save(ArgumentMatchers.any(Vendor.class))).thenReturn(savedVendor);

        VendorDTO savedDTO = vendorService.saveVendorByDTO(1L, passedInDTO);
        assertEquals(passedInDTO.getName(), savedDTO.getName());
        assertEquals("/api/v1/vendors/1", savedDTO.getVendorUrl());
    }

    @Test
    void deleteVendorById() {
        vendorRepository.deleteById(1L);
        Mockito.verify(vendorRepository).deleteById(ArgumentMatchers.anyLong());
    }
}