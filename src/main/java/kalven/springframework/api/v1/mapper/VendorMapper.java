package kalven.springframework.api.v1.mapper;

import kalven.springframework.api.v1.model.VendorDTO;
import kalven.springframework.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/18/23
 */
@Mapper
public interface VendorMapper {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO vendorToVendorDTO(Vendor vendor);

    Vendor vendorDTOtoVendor(VendorDTO vendorDTO);
}
