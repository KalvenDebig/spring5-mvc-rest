package kalven.springframework.bootstrap;

import kalven.springframework.domain.Category;
import kalven.springframework.domain.Customer;
import kalven.springframework.repositories.CategoryRepository;
import kalven.springframework.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/16/23
 */
@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCustomers();
        loadCategories();
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fish = new Category();
        fish.setName("Fish");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fish);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);
        System.out.println("Data Loaded: " + categoryRepository.count());
    }

    private void loadCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Jessie");
        customer1.setLastName("Pinkman");

        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Walter");
        customer2.setLastName("White");
        customerRepository.save(customer2);

        System.out.println("Customer loaded: " + customerRepository.count());
    }
}
