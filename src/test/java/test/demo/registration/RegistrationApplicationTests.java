package test.demo.registration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.demo.registration.controller.UserController;
import dev.demo.registration.dto.UserDto;

@DisplayName("Test Register")
@TestMethodOrder(OrderAnnotation.class)
class RegistrationApplicationTests {

	@Mock
    private UserController userController;
	

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    
    @Test
    @DisplayName("Test register with invalid email format: Get 400")
	@Order(1)
    public void test_register_with_empty_email() throws Exception {
		UserDto req = initUser();
		req.setEmail("GoldenTheater");
	    mockMvc.perform(MockMvcRequestBuilders.post("/api/demo/register")
    		    .contentType(MediaType.APPLICATION_JSON)
    		    .content(toJsonString(req))
    		    .accept(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }
    
    @Test
    @DisplayName("Test register with empty telephone number: Get 400")
	@Order(2)
    public void test_register_with_empty_telephone_number() throws Exception {
		UserDto req = initUser();
		req.setTelephoneNumber(null);
	    mockMvc.perform(MockMvcRequestBuilders.post("/api/demo/register")
    		    .contentType(MediaType.APPLICATION_JSON)
    		    .content(toJsonString(req))
    		    .accept(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }
	
	@Test
    @DisplayName("Test register success: Get 200")
	@Order(3)
    public void test_register_success() throws Exception {
		UserDto req = initUser();
	    mockMvc.perform(MockMvcRequestBuilders.post("/api/demo/register")
    		    .contentType(MediaType.APPLICATION_JSON)
    		    .content(toJsonString(req))
    		    .accept(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }
	
	@Test
    @DisplayName("Test get user without access token: Get 400")
	@Order(4)
    public void test_get_user_data_by_invalid_access_token() throws Exception {
		String userId = "0";
	    mockMvc.perform(MockMvcRequestBuilders.get("/api/demo/user/"+userId)
	    .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }
	


	private UserDto initUser() {
		UserDto userDto = new UserDto();
		userDto.setFirstName("Nero");
		userDto.setLastName("Claudius");
		userDto.setUsername("redrose");
		userDto.setPassword("fate/extra");
		userDto.setEmail("redrose@fate.extra");
		userDto.setAddress("2483/2 Roma, Italy");
		userDto.setTelephoneNumber("0123456789");
		userDto.setSalary("60000");
		return userDto;
	}
	
	
	private String toJsonString(Object obj) throws JsonProcessingException {
    	ObjectMapper mapper = new ObjectMapper();
    	String jsonStr = mapper.writeValueAsString(obj);
        return jsonStr;
}  

}
