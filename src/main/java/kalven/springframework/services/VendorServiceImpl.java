package kalven.springframework.services;

import kalven.springframework.api.v1.mapper.VendorMapper;
import kalven.springframework.api.v1.model.VendorDTO;
import kalven.springframework.api.v1.model.VendorListDTO;
import kalven.springframework.controllers.v1.VendorController;
import kalven.springframework.domain.Vendor;
import kalven.springframework.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/18/23
 */
@Service
@Slf4j
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;
    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper.INSTANCE;
    }

    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .map(vendorDTO -> {
                    vendorDTO.setVendorUrl(getVendorUrl(id));
                    return vendorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<VendorDTO> getAllVendors() {
          return vendorRepository.findAll()
                .stream().map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO savedVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        savedVendorDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));
        return savedVendorDTO;
    }

    private VendorDTO saveAndReturnVendorDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
        return vendorDTO;
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        vendor.setId(id);
        return saveAndReturnVendorDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    if (vendorDTO.getName() != null) {
                        vendor.setName(vendorDTO.getName());
                    }
                    VendorDTO savedVendorDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
                    savedVendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                    return savedVendorDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<VendorDTO> deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
        return getAllVendors();
    }
}
