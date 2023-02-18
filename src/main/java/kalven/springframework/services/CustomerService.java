package kalven.springframework.services;

import kalven.springframework.api.v1.model.CustomerDTO;

import java.util.List;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/17/23
 */
public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
}