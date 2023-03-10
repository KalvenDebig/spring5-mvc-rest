package kalven.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/18/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {
    private String name;
    @JsonProperty("vendor_url")
    private String vendorUrl;
}
