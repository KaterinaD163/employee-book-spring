package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Map<Integer, Employee> employees = new HashMap<>();


    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
        if (employeeRequest.getFirstName() == null || employeeRequest.getLastName() == null) {
            throw new IllegalArgumentException("Employee name should be set");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(), employeeRequest.getLastName(), employeeRequest.getDepartment(),
                employeeRequest.getSalary());
        this.employees.put(employee.getId(), employee);
        return employee;
    }

    public int getSalarySum() {
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public Employee getEmployeeWithMinSalary() {
        return employees.values().stream()
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(() -> new RuntimeException(String.valueOf("not found")));
    }


    public Employee getEmployeeWithMaxSalary() {
        return employees.values().stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(() -> new RuntimeException(String.valueOf("not found")));
    }
    public Map<Integer, String> getEmployeeWithHighSalary() {
        double averageSalary = employees.values().stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);
        return employees.values().stream()
                .filter(x -> x.getSalary() > averageSalary)
                .collect(Collectors.toMap(Employee::getId, Employee:: getLastName));
    }
}

