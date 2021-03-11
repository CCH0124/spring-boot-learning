package com.example.cch.postgresqldemo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.cch.postgresqldemo.exception.ResourceNotFoundException;
import com.example.cch.postgresqldemo.model.Employee;
import com.example.cch.postgresqldemo.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("employees")
    public List<Employee> getAllEmployees(){
        return this.employeeRepository.findAll();
    }

    @GetMapping("employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Employee not found for this id : " + id)
        );

        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return this.employeeRepository.save(employee);
    }

    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long id, @Validated @RequestBody Employee employeeDetails)
            throws ResourceNotFoundException {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Employee not found for this id : " + id)
        );

        employee.setEmail(employeeDetails.getEmail());
        employee.setLastName(employeeDetails.getLastName());
        employee.setLastName(employeeDetails.getLastName());
        // employee.setBirth(employeeDetails.getBirth());

        return ResponseEntity.ok(this.employeeRepository.save(employee));
    }

    @DeleteMapping("employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Employee not found for this id : " + id)
        );

        this.employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);

        return response;
    }

}
