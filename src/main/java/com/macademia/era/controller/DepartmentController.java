package com.macademia.era.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.macademia.era.db.entity.Department;
import com.macademia.era.db.service.DepartmentService;
import com.macademia.era.model.OutputData;
import com.macademia.era.util.GlobalUtil;

/**
 * created by ysnky on Jun 14, 2022
 *
 */

@RestController
@RequestMapping("/api/department")
public class DepartmentController extends BaseController {

	@Autowired
	DepartmentService departmentService;

	@PostMapping("/add")
	public OutputData<Department> add(@RequestBody Department department) {
		
		// to handle unexpected cases like unique constraints, format validation etc...
		// this exception handling could be implemented to other methods.
		try {
			Department departmentResult = departmentService.add(department);
			return new OutputData<Department>(departmentResult);
		} catch (Exception e) {
			logger.error(GlobalUtil.exceptionToString(e));
			return new OutputData<Department>(false); // error message could be customized instead of default error message
		}
	}

	@PostMapping("/update")
	public OutputData<Department> update(@RequestBody Department department) {
		
		Department departmentResult = departmentService.update(department);
		return new OutputData<Department>(departmentResult);
	}
	
	// updates the office location of all the employees of a specific department
	@PostMapping("/updateLocation")
	public OutputData<Department> updateLocation(@RequestParam String code, @RequestParam String location) {
		
		Department department = departmentService.updateLocation(code, location);
		return new OutputData<Department>(department);
	}
	
	@GetMapping("/findByCode/{code}")
	public OutputData<Department> find(@PathVariable String code) {

		Department department = departmentService.findByCode(code);
		return new OutputData<Department>(department);
	}

	@GetMapping("/find")
	public OutputData<List<Department>> find() {
		
		List<Department> departments = departmentService.find();
		return new OutputData<List<Department>>(departments);
	}
	
	
	@PostMapping("/delete/{id}")
	public OutputData<Department> delete(@PathVariable Long id) {
		
		departmentService.delete(id);
		return new OutputData<Department>(true);
	}

}
