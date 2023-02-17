package kalven.springframework.services;

import kalven.springframework.api.v1.mapper.CategoryMapper;
import kalven.springframework.api.v1.model.CategoryDTO;
import kalven.springframework.domain.Category;
import kalven.springframework.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/17/23
 */
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    CategoryRepository categoryRepository;
    CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    void getAllCategories() {
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();
        assertEquals(3, categoryDTOList.size());
        Mockito.verify(categoryRepository).findAll();
    }

    @Test
    void getCategoryByName() {
        Category category = new Category();
        category.setId(1L);
        category.setName("name");
        Mockito.when(categoryRepository.findByName(ArgumentMatchers.anyString())).thenReturn(category);

        CategoryDTO categoryDTO = categoryService.getCategoryByName("name");
        assertEquals(1L, categoryDTO.getId());
        assertEquals("name", categoryDTO.getName());

        Mockito.verify(categoryRepository).findByName(ArgumentMatchers.anyString());
    }
}