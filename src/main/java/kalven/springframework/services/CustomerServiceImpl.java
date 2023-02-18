package kalven.springframework.services;

import kalven.springframework.api.v1.mapper.CustomerMapper;
import kalven.springframework.api.v1.model.CustomerDTO;
import kalven.springframework.controllers.v1.CustomerController;
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
                    customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                    return customerDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);

        savedCustomerDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
        return savedCustomerDTO;
    }
    private CustomerDTO saveAndReturnDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
        return customerDTO;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id)
                .map(customer -> {
                    if (customerDTO.getFirstName() != null) {
                        customer.setFirstName(customerDTO.getFirstName());
                    }

                    if (customerDTO.getLastName() != null) {
                        customer.setLastName(customerDTO.getLastName());
                    }
                    CustomerDTO savedCustomerDto = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
                    savedCustomerDto.setCustomerUrl(getCustomerUrl(id));
                    return savedCustomerDto;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<CustomerDTO> deleteCustomer(Long id) {
        customerRepository.deleteById(id);
        return getAllCustomers();
    }

    private String getCustomerUrl(Long id) {
        return CustomerController.BASE_URL + "/" + id;
    }
}
