package kalven.springframework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/17/23
 */
public abstract class AbstractRestControllerTest {
    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
