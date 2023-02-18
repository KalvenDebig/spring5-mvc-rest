package kalven.springframework.api.v1.mapper;

import kalven.springframework.api.v1.model.CustomerDTO;
import kalven.springframework.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/17/23
 */
@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
//    @Mapping(source = "id", target = "id")
    CustomerDTO cutomerToCustomerDTO(Customer customer);
}
