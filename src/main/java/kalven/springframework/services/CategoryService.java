package kalven.springframework.services;

import kalven.springframework.api.v1.model.CategoryDTO;

import java.util.List;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/17/23
 */
public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
