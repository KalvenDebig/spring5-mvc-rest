package kalven.springframework.services;

import kalven.springframework.api.v1.mapper.CustomerMapper;
import kalven.springframework.api.v1.model.CustomerDTO;
import kalven.springframework.domain.Customer;
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
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/v1/customer" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    //todo: implement Exception Handling
    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);

        savedCustomerDTO.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId());
        return savedCustomerDTO;
    }
    private CustomerDTO saveAndReturnDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
        return customerDTO;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnDTO(customer);
    }
}
