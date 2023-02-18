package kalven.springframework.api.v1.mapper;

import kalven.springframework.api.v1.model.VendorDTO;
import kalven.springframework.domain.Vendor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/18/23
 */
class VendorMapperTest {
    VendorMapper vendorMapper;
    @BeforeEach
    void setUp() {
        vendorMapper = VendorMapper.INSTANCE;
    }

    @Test
    void vendorToVendorDTO() {
        Vendor vendor = new Vendor();
        vendor.setName("Jessie");

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
        assertEquals(vendor.getName(), vendorDTO.getName());
    }

    @Test
    void vendorDTOtoVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Andy");

        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        assertEquals(vendorDTO.getName(), vendor.getName());
    }
}