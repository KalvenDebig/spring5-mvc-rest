package kalven.springframework.repositories;

import kalven.springframework.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/17/23
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
