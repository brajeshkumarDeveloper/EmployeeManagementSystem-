package com.ems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ems.dto.EmployeeDto;
import com.ems.entity.Employee;
import com.ems.mapper.EmployeeMapper;
import com.ems.repository.EmployeeRepository;
import com.ems.service.implementation.EmployeeServiceImpl;

@SpringBootTest
class RestApiSpringBootApplicationTests {
    @Mock
    private EmployeeRepository employeeRepository;  // Mocking the employeeRepository
    
    @Mock
    private EmployeeMapper employeeMapper; // Mocking the EmployeeMapper
    
    @InjectMocks
    private EmployeeServiceImpl employeeService; // The service under test
    
    private Employee employee1;
    private Employee employee2;
    
    @BeforeEach
    void setUp() {
        // Initialize employee objects before each test
        employee1 = new Employee(1L, "John", "Doe", "john@gmail.com");
        employee2 = new Employee(2L, "Jane", "Smith", "Marketing@gmail.com");
    }


    @Test
    public void testGetAllEmployees_HappyPath() {
        // Given: Mock the repository to return a list of employees
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(employees);

        // Mock the EmployeeMapper to return EmployeeDtos
        EmployeeDto employeeDto1 = new EmployeeDto(1L, "John", "Doe", "john@gmail.com");
        EmployeeDto employeeDto2 = new EmployeeDto(2L, "Jane", "Smith", "Marketing@gmail.com");
//
//        when(employeeMapper.mapToEmployeeDto(employee1)).thenReturn(employeeDto1);
//        when(employeeMapper.mapToEmployeeDto(employee2)).thenReturn(employeeDto2);

        // When: Calling the service method
        List<EmployeeDto> result = employeeService.getAllEmployees();

        // Then: Validate the result
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());

        // Verify interactions with mocks
        verify(employeeRepository, times(1)).findAll();
//        verify(employeeMapper, times(1)).mapToEmployeeDto(employee1);
//        verify(employeeMapper, times(1)).mapToEmployeeDto(employee2);
    }


    @Test
    public void testGetAllEmployees_EmptyList() {
        // Given: Mock the repository to return an empty list
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        // When: Calling the service method
        List<EmployeeDto> result = employeeService.getAllEmployees();

        // Then: Validate that the result is empty
        assertNotNull(result);
       

        // Verify that findAll was called
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllEmployees_ExceptionInRepository() {
        // Given: Mock the repository to throw an exception
        when(employeeRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // When: Calling the service method, expect an exception
        try {
            employeeService.getAllEmployees();
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            // Then: Validate that the exception is thrown
            assertEquals("Database error", e.getMessage());
        }

        // Verify that findAll was called once
        verify(employeeRepository, times(1)).findAll();
    }
}
