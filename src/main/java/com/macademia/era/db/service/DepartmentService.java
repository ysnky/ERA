package com.macademia.era.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.macademia.era.db.entity.Department;
import com.macademia.era.db.repository.DepartmentRepository;

/**
 * created by ysnky on Jun 14, 2022
 *
 */

@Service
public class DepartmentService {
	
	@Autowired DepartmentRepository repository;
	
	public Department findByCode(String code) {
		return repository.findByCode(code);
	}
	
	public Department add(Department department) {
		return repository.save(department);
	}

	public List<Department> find() {
		return repository.findAll();
	}
	
	public Department update(Department department) {
		
		Department currDepartment = repository.findById(department.getId()).get();
		
		currDepartment.setCode(department.getCode());
		currDepartment.setName(department.getName());
		currDepartment.setLocation(department.getLocation());
		
		return repository.save(currDepartment);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	// updates the office location of all the employees of a specific department
	public Department updateLocation(String code, String location) {
		
		Department department = repository.findByCode(code);
		department.setLocation(location);
		
		return repository.save(department);
	}
}
