package com.ems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ems.controller.EmployeeController;
import com.ems.dto.EmployeeDto;
import com.ems.service.EmployeeService;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employeeDto = new EmployeeDto();
        employeeDto.setId(1L);
        employeeDto.setFirstName("John");
        employeeDto.setLastName("Doe");
        employeeDto.setEmail("john.doe@example.com");
    }

    @Test
    void testCreateEmployee() {
        when(employeeService.createEmployee(employeeDto)).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.createEmployee(employeeDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employeeDto, response.getBody());

        verify(employeeService).createEmployee(employeeDto);
    }

    @Test
    void testGetEmployeeById() {
        when(employeeService.getEmployeeById(1L)).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.getEmployeeById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDto, response.getBody());

        verify(employeeService).getEmployeeById(1L);
    }

    @Test
    void testGetAllEmployees() {
        List<EmployeeDto> employeeList = Arrays.asList(employeeDto);
        when(employeeService.getAllEmployees()).thenReturn(employeeList);

        ResponseEntity<List<EmployeeDto>> response = employeeController.getAllEmployees();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeList, response.getBody());

        verify(employeeService).getAllEmployees();
    }

    @Test
    void testUpdateEmployee() {
        when(employeeService.updateEmployee(1L, employeeDto)).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.updateEmployee(1L, employeeDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDto, response.getBody());

        verify(employeeService).updateEmployee(1L, employeeDto);
    }

    @Test
    void testDeleteEmployee() {
        ResponseEntity<String> response = employeeController.deleteEmployee(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee deleted sucessfully!", response.getBody());

        verify(employeeService).deleteEmployee(1L);
    }
}