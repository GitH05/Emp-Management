package com.employee.Emp_Management.service;

import com.employee.Emp_Management.entity.EmployeeEntity;
import com.employee.Emp_Management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void addEmployee(EmployeeEntity e) {
        employeeRepository.save(e);
    }

    public List<EmployeeEntity> getAllEmployee() {
        return employeeRepository.findAll(); // Return the list of all employees
    }

    public EmployeeEntity getEmployeeById(int id) {
        Optional<EmployeeEntity> e = employeeRepository.findById(id);
        if(e.isPresent()) {
            return e.get();
        }
        return null;
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }
}
