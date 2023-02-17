package kalven.springframework.api.v1.mapper;

import kalven.springframework.api.v1.model.CategoryDTO;
import kalven.springframework.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/17/23
 */
@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDto(Category category);
}
