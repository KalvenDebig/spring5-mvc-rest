package kalven.springframework.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/17/23
 */
@Data
@AllArgsConstructor
public class CategoryListDTO {
    List<CategoryDTO> categories;
}
