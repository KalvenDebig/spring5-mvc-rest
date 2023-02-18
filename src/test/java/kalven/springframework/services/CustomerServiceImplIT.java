package kalven.springframework.services;

import kalven.springframework.api.v1.mapper.CustomerMapper;
import kalven.springframework.api.v1.model.CustomerDTO;
import kalven.springframework.bootstrap.Bootstrap;
import kalven.springframework.domain.Customer;
import kalven.springframework.repositories.CategoryRepository;
import kalven.springframework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/18/23
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class CustomerServiceImplIT {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;
    @BeforeEach
    void setUp() throws Exception {
        System.out.println("Loading Customer data");
        System.out.println(customerRepository.count());

        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    void patchCustomerUpdateFirstName() {
        String updatedFirstName = "updatedFirstName";
        Long id = getId();
        Optional<Customer> originalCustomer = customerRepository.findById(id);
        assertNotNull(originalCustomer);
        if (!originalCustomer.isPresent()) {
            System.out.println("Original Customer is not found");
            return;
        }

        String originFirstName = originalCustomer.get().getFirstName();
        String originLastName = originalCustomer.get().getLastName();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedFirstName);
        customerService.patchCustomer(id, customerDTO);

        Optional<Customer> updatedCustomer = customerRepository.findById(id);
        assertNotNull(updatedCustomer);
        assertEquals(updatedFirstName, updatedCustomer.get().getFirstName());
    }

    @Test
    void pathCustomerUpdateLastName() {
        String updatedLastName = "updatedLastName";
        Long id = getId();
        Optional<Customer> originalCustomer = customerRepository.findById(id);
        assertNotNull(originalCustomer);
        if (!originalCustomer.isPresent()) {
            System.out.println("Original Customer is not found");
            return;
        }

        String originFirstName = originalCustomer.get().getFirstName();
        String originLastName = originalCustomer.get().getLastName();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedLastName);
        customerService.patchCustomer(id, customerDTO);

        Optional<Customer> updatedCustomer = customerRepository.findById(id);
        assertNotNull(updatedCustomer);
        assertEquals(updatedLastName, updatedCustomer.get().getFirstName());
    }

    Long getId() {
        List<Customer> list = customerRepository.findAll();
        return list.get(0).getId();
    }
}