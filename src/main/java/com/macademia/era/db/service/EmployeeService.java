package com.macademia.era.db.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.macademia.era.db.entity.Employee;
import com.macademia.era.db.repository.EmployeeRepository;

/**
 * created by ysnky on Jun 14, 2022
 *
 */

@Service
public class EmployeeService {
	
	@Autowired 
	EmployeeRepository repository;
	
	public Employee add(Employee employee) {
		return repository.save(employee);
	}

	public List<Employee> find() {
		return repository.findAll();
	}
	
	public Long count() {
		return repository.count();
	}
	
	public Employee findByEmployeeNo(String employeeNo) {
		return repository.findByEmployeeNo(employeeNo);
	}
	
	public List<Employee> findByStartDateAndSalary(Date startDate, BigDecimal salary) {
		return repository.findByStartDateAndSalary(startDate, salary);
	}
	
	public Employee update(Employee employee) {
		
		Employee currEmployee = repository.findById(employee.getId()).get();
		
		currEmployee.setEmployeeNo(employee.getEmployeeNo());
		currEmployee.setName(employee.getName());
		currEmployee.setSurname(employee.getSurname());
		currEmployee.setBirthDate(employee.getBirthDate());
		currEmployee.setSalary(employee.getSalary());
		currEmployee.setStartDate(employee.getStartDate());
		currEmployee.setDepartment(employee.getDepartment());
		
		return repository.save(currEmployee);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
	
}
