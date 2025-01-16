package com.ems;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.ems.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.ems.dto.EmployeeDto;
import com.ems.entity.Employee;
import com.ems.mapper.EmployeeMapper;
import com.ems.repository.EmployeeRepository;
import com.ems.service.implementation.EmployeeServiceImpl;

@SpringBootTest
class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");

        employeeDto = new EmployeeDto();
        employeeDto.setId(1L);
        employeeDto.setFirstName("John");
        employeeDto.setLastName("Doe");
        employeeDto.setEmail("john.doe@example.com");
    }

    @Test
    void testCreateEmployee() {
        when(employeeRepository.save(employee)).thenReturn(employee);

        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);

        assertNotNull(createdEmployee);
        assertEquals("John", createdEmployee.getFirstName());
        assertEquals("Doe", createdEmployee.getLastName());
        assertEquals("john.doe@example.com", createdEmployee.getEmail());

        verify(employeeRepository).save(employee);
    }

    @Test
    void testGetEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        try (MockedStatic<EmployeeMapper> mockedMapper = mockStatic(EmployeeMapper.class)) {
            mockedMapper.when(() -> EmployeeMapper.mapToEmployeeDto(employee)).thenReturn(employeeDto);

            EmployeeDto foundEmployee = employeeService.getEmployeeById(1L);

            assertNotNull(foundEmployee);
            assertEquals("John", foundEmployee.getFirstName());
            assertEquals("Doe", foundEmployee.getLastName());
            assertEquals("john.doe@example.com", foundEmployee.getEmail());

            verify(employeeRepository).findById(1L);
        }
    }



@Test
void testGetAllEmployees() {
    when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));

    try (MockedStatic<EmployeeMapper> mockedMapper = mockStatic(EmployeeMapper.class)) {
        mockedMapper.when(() -> EmployeeMapper.mapToEmployeeDto(employee)).thenReturn(employeeDto);

        List<EmployeeDto> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals("John", employees.get(0).getFirstName());
        assertEquals("Doe", employees.get(0).getLastName());

        verify(employeeRepository).findAll();
    }}

    @Test
    void testUpdateEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        try (MockedStatic<EmployeeMapper> mockedMapper = mockStatic(EmployeeMapper.class)) {
            mockedMapper.when(() -> EmployeeMapper.mapToEmployeeDto(employee)).thenReturn(employeeDto);

            EmployeeDto updatedEmployee = employeeService.updateEmployee(1L, employeeDto);

            assertNotNull(updatedEmployee);
            assertEquals("John", updatedEmployee.getFirstName());
            assertEquals("Doe", updatedEmployee.getLastName());
            assertEquals("john.doe@example.com", updatedEmployee.getEmail());

            verify(employeeRepository).findById(1L);
            verify(employeeRepository).save(employee);
        }
    }

    @Test
    void testUpdateEmployee_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(1L, employeeDto);
        });

        verify(employeeRepository).findById(1L);
    }

    @Test
    void testDeleteEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).findById(1L);
        verify(employeeRepository).deleteById(1L);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.deleteEmployee(1L);
        });

        verify(employeeRepository).findById(1L);
    }




    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(1L);
        });

        verify(employeeRepository).findById(1L);
    }


}