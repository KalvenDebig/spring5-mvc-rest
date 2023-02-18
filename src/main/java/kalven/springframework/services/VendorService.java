package kalven.springframework.services;

import kalven.springframework.api.v1.model.VendorDTO;

import java.util.List;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/18/23
 */
public interface VendorService {
    VendorDTO getVendorById(Long id);
    List<VendorDTO> getAllVendors();
    VendorDTO createNewVendor(VendorDTO vendorDTO);
    VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO);
    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);
    List<VendorDTO> deleteVendorById(Long id);
}
