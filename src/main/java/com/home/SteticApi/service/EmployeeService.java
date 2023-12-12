package com.home.SteticApi.service;

import com.home.SteticApi.domain.Employee;
import com.home.SteticApi.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Transactional
    public void removeEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    public void modifyEmployee(Employee newEmployee, long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();
            existingEmployee.setName(newEmployee.getName());
            existingEmployee.setDNI(newEmployee.getDNI());
            existingEmployee.setAge(newEmployee.getAge());
            existingEmployee.setHeight(newEmployee.getHeight());
            existingEmployee.setAcademicTitle(newEmployee.isAcademicTitle());
            existingEmployee.setRegisterDate(newEmployee.getRegisterDate());
            existingEmployee.setShop(newEmployee.getShop());
            employeeRepository.save(existingEmployee);
        }
    }
}
