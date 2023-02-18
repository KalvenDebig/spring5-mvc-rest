package kalven.springframework.controllers.v1;

import kalven.springframework.api.v1.model.CustomerDTO;
import kalven.springframework.services.CustomerService;
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


/**
 * @Project spring5-mvc-rest
 * @Author kalvens on 2/17/23
 */
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest extends AbstractRestControllerTest {
    @Mock
    CustomerService customerService;
    @InjectMocks
    CustomerController customerController;
    MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        CustomerDTO customerDTO1 = new CustomerDTO();
        CustomerDTO customerDTO2 = new CustomerDTO();
        Mockito.when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customerDTO1, customerDTO2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customers", Matchers.hasSize(2)));

        Mockito.verify(customerService).getAllCustomers();
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Walter");
        customerDTO.setLastName("White");
        customerDTO.setCustomerUrl("/api/v1/customers/1");
        Mockito.when(customerService.getCustomerById(ArgumentMatchers.anyLong())).thenReturn(customerDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.equalTo("Walter")));

        Mockito.verify(customerService).getCustomerById(ArgumentMatchers.anyLong());
    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName("White");
        customerDTO.setFirstName("Walter");
        CustomerDTO savedCustomerDTO = new CustomerDTO();
        savedCustomerDTO.setLastName(customerDTO.getLastName());
        savedCustomerDTO.setFirstName(customerDTO.getFirstName());
        savedCustomerDTO.setCustomerUrl("/api/v1/customers/1");

        Mockito.when(customerService.createNewCustomer(customerDTO)).thenReturn(savedCustomerDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.equalTo("Walter")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.equalTo("White")));
    }

    @Test
    void saveCustomerByDTO() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Walter");
        customerDTO.setLastName("White");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customerDTO.getFirstName());
        returnDTO.setLastName(customerDTO.getLastName());
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        Mockito.when(customerService.saveCustomerByDTO(ArgumentMatchers.anyLong(), ArgumentMatchers.any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.equalTo("Walter")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.equalTo("White")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer_url", Matchers.equalTo("/api/v1/customers/1")));
    }
}