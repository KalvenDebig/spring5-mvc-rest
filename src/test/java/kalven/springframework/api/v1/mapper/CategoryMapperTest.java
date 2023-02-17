package kalven.springframework.api.v1.mapper;

import kalven.springframework.api.v1.model.CategoryDTO;
import kalven.springframework.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/17/23
 */
class CategoryMapperTest {
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;
    @BeforeEach
    void setUp() {
    }

    @Test
    void categoryToCategoryDto() {
        Category category = new Category();
        category.setName("someName");
        category.setId(110L);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDto(category);
        assertEquals("someName", categoryDTO.getName());
        assertEquals(110L, categoryDTO.getId());
    }
}