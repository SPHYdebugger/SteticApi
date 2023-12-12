package com.home.SteticApi.controller;

import com.home.SteticApi.domain.Employee;
import com.home.SteticApi.domain.ErrorResponse;
import com.home.SteticApi.exception.EmployeeException.EmployeeNotFoundException;
import com.home.SteticApi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Obtener todos los empleados o filtrar uno por el nombre
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> findAll(@RequestParam(defaultValue = "") String name) throws EmployeeNotFoundException {
        if (!name.isEmpty()) {
            List<Employee> employees = employeeService.findEmployeeByName(name);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }

        List<Employee> allEmployees = employeeService.findAll();
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    // Obtener un empleado por ID
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> findById(@PathVariable long employeeId) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = employeeService.findById(employeeId);
        Employee employee = optionalEmployee.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    // Añadir un nuevo empleado
    @PostMapping("/employees")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    // Borrar un empleado por ID
    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<Void> removeEmployee(@PathVariable long employeeId) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = employeeService.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            employeeService.removeEmployee(employeeId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new EmployeeNotFoundException(employeeId);
        }
    }

    // Modificar un empleado por ID
    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<Employee> modifyEmployee(@RequestBody Employee employee, @PathVariable long employeeId) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = employeeService.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            employeeService.modifyEmployee(employee, employeeId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new EmployeeNotFoundException(employeeId);
        }
    }

    // Control de excepciones
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> employeeNotFoundException(EmployeeNotFoundException enfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, enfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
