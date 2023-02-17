package kalven.springframework.repositories;

import kalven.springframework.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/16/23
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
