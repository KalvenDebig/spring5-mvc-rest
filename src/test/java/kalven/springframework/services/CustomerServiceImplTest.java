package kalven.springframework.services;

import kalven.springframework.api.v1.mapper.CustomerMapper;
import kalven.springframework.api.v1.model.CustomerDTO;
import kalven.springframework.domain.Customer;
import kalven.springframework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/17/23
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    CustomerRepository customerRepository;
    CustomerService customerService;
    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    void getAllCustomers() {
        List<Customer> customerDTOS = Arrays.asList(new Customer(), new Customer(), new Customer());
        Mockito.when(customerRepository.findAll()).thenReturn(customerDTOS);
        List<CustomerDTO> actual = customerService.getAllCustomers();
        assertEquals(3, actual.size());
    }

    @Test
    void getCustomerById() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLastName("White");
        customer.setFirstName("Walter");
        Mockito.when(customerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(customer));
        CustomerDTO customerDTO = customerService.getCustomerById(1L);
        assertEquals("White", customerDTO.getLastName());
    }

    @Test
    void createNewCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Jessie");
        customerDTO.setLastName("Pinkman");
        Customer saved = new Customer();
        saved.setId(1L);
        saved.setFirstName(customerDTO.getFirstName());
        saved.setLastName(customerDTO.getLastName());

        Mockito.when(customerRepository.save(ArgumentMatchers.any())).thenReturn(saved);

        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

        assertEquals(saved.getFirstName(), savedDTO.getFirstName());
        assertEquals("/api/v1/customers/1", savedDTO.getCustomerUrl());
    }

    @Test
    void saveCustomerByDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Walter");
        customerDTO.setLastName("White");
        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1L);

        Mockito.when(customerRepository.save(ArgumentMatchers.any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO savedDTO = customerService.saveCustomerByDTO(1L, customerDTO);

        assertEquals(customerDTO.getFirstName(), savedDTO.getFirstName());
        assertEquals("/api/v1/customers/1", savedDTO.getCustomerUrl());
    }
}