package kalven.springframework.services;

import kalven.springframework.api.v1.mapper.CustomerMapper;
import kalven.springframework.api.v1.model.CustomerDTO;
import kalven.springframework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/17/23
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.cutomerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/v1/customer" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    //todo: implement Exception Handling
    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::cutomerToCustomerDTO)
                .orElseThrow(RuntimeException::new);
    }
}
