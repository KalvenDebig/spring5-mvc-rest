package kalven.springframework.controllers.v1;

import kalven.springframework.api.v1.model.CategoryDTO;
import kalven.springframework.services.CategoryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/17/23
 */
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    @Mock
    CategoryService categoryService;
    @InjectMocks
    CategoryController categoryController;
    MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void getAllCategories() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("name1");

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(2L);
        categoryDTO2.setName("name2");

        List<CategoryDTO> categories = Arrays.asList(categoryDTO, categoryDTO2);
        Mockito.when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.get(CategoryController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories", Matchers.hasSize(2)));
    }

    @Test
    void getCategoryByName() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("jim");

        Mockito.when(categoryService.getCategoryByName(ArgumentMatchers.anyString()))
                .thenReturn(categoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(CategoryController.BASE_URL + "/jim")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("jim")));
    }
}