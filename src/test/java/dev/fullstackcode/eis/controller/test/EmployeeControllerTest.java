package dev.fullstackcode.eis.controller.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.fullstackcode.eis.config.AppProperties;
import dev.fullstackcode.eis.controller.EmployeeController;
import dev.fullstackcode.eis.dto.DepartmentDto;
import dev.fullstackcode.eis.dto.EmployeeDto;
import dev.fullstackcode.eis.entity.Gender;
import dev.fullstackcode.eis.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {

    private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    ;
    private ObjectWriter jsonWriter = mapper.writerFor(EmployeeDto.class);

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private AppProperties appProperties;


    @Test
    public void testEmployeeCreationFailsWithInvalidData() throws Exception {
        DepartmentDto deptDto = new DepartmentDto(100, "HR");
        EmployeeDto empRecordDto = new EmployeeDto(null, "", "tester", Gender.F, LocalDate.of(1990, 12, 12), LocalDate.of(20023, 1, 1), deptDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/employee").content(jsonWriter.writeValueAsString(empRecordDto)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
        this.mockMvc.perform(requestBuilder).andExpect(status().isBadRequest()).andDo(result -> System.out.println("response : " + result.getResponse().getContentAsString()))
                .andExpect(response -> assertEquals("{\"error\":\"BAD_REQUEST\",\"code\":400,\"errors\":[{\"object\":\"employeeDto\",\"field\":\"first_name\",\"message\":\"FirstName should not be blank\",\"rejectedValue\":\"\"}]}", response.getResponse().getContentAsString()));


    }

}
