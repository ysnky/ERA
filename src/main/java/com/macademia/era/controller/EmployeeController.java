package com.macademia.era.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.macademia.era.db.entity.Employee;
import com.macademia.era.db.service.EmployeeService;
import com.macademia.era.model.OutputData;
import com.macademia.era.util.DateUtil;
import com.macademia.era.util.GlobalUtil;

/**
 * created by ysnky on Jun 14, 2022
 *
 */

@RestController
@RequestMapping("/api/employee")
public class EmployeeController extends BaseController {

	@Autowired
	EmployeeService employeeService;

	@PostMapping("/add")
	public OutputData<Employee> add(@RequestBody Employee employee) {
		
		// to handle unexpected cases like unique constraints, format validation etc...
		// this exception handling could be implemented to other methods.
		try {
			Employee employeeResult = employeeService.add(employee);
			return new OutputData<Employee>(employeeResult);
		} catch (Exception e) {
			logger.error(GlobalUtil.exceptionToString(e));
			return new OutputData<Employee>(false); // error message could be customized instead of default error message
		}
	}

	@PostMapping("/update")
	public OutputData<Employee> update(@RequestBody Employee employee) {
		
		Employee employeeResult = employeeService.update(employee);
		return new OutputData<Employee>(employeeResult);
	}
	
	@GetMapping("/findByEmployeeNo/{employeeNo}")
	public OutputData<Employee> findByEmployeeNo(@PathVariable String employeeNo) {

		Employee employee = employeeService.findByEmployeeNo(employeeNo);
		return new OutputData<Employee>(employee);
	}

	@GetMapping("/find")
	public OutputData<List<Employee>> find() {
		
		List<Employee> employees = employeeService.find();
		return new OutputData<List<Employee>>(employees);
	}
	
	
	@PostMapping("/delete/{id}")
	public OutputData<Employee> delete(@PathVariable Long id) {
		
		employeeService.delete(id);
		return new OutputData<Employee>(true);
	}
	
	
	
	@GetMapping("/findByStartDateAndSalary")
	public OutputData<List<Employee>> findByStartDateAndSalary(@RequestParam String startDate, @RequestParam String salary) {

		List<Employee> employees = employeeService.findByStartDateAndSalary(DateUtil.stringToDate(startDate), new BigDecimal(salary));
		return new OutputData<List<Employee>>(employees);
	}

	
	

}
