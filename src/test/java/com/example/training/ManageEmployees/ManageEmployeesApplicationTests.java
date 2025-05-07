package com.example.training.ManageEmployees;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ManageEmployeeApplicationTests {
    @LocalServerPort
    Integer serverPort; //Server port will be injected here during Testcase execution.
	
	@Test
	void contextLoads() {
	}

	@Test
	void testQueryEmployee() {
		RestTemplate template = new RestTemplate();
		//Invoke http://localhost:8080/employees/100 using GET
		ResponseEntity<Employee> response = template.getForEntity("http://localhost:"+serverPort+"/employees/100", Employee.class);
		//Checking if the response object name is 'James Cooper' ==> If yes, then case is passed.
		assertEquals(response.getBody().getEmpName(), "James Cooper");		
	} 
	
	@Test
	void testQueryEmployeeNotFound() {
	    RestTemplate template = new RestTemplate();
	    try {
	        template.getForEntity("http://localhost:" + serverPort + "/employees/900", Employee.class);
	        fail("Expected HttpClientErrorException to be thrown");
	    } catch (HttpClientErrorException e) {
	        assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
	        assertTrue(e.getMessage().contains("Employee with given id not found"));
	    }
	}

}
