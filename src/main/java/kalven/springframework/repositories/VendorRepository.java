package kalven.springframework.repositories;

import kalven.springframework.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/18/23
 */
public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
