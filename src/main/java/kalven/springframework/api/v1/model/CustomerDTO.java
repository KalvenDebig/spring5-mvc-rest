package kalven.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/17/23
 */
@Data
public class CustomerDTO {
    private String firstName;
    private String lastName;
    @JsonProperty("customer_url")
    private String customerUrl;
}
