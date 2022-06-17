package com.macademia.era;

import static org.junit.Assert.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.macademia.era.business.DrawBusiness;
import com.macademia.era.db.entity.Department;
import com.macademia.era.db.entity.Draw;
import com.macademia.era.db.entity.Employee;
import com.macademia.era.db.service.DepartmentService;
import com.macademia.era.db.service.EmployeeService;
import com.macademia.era.exception.DrawException;
import com.macademia.era.exception.DrawException.DEC;
import com.macademia.era.util.DateUtil;

/**
 * created by ysnky on Jun 15, 2022
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ERAUnitTest {
    @Autowired
    private EmployeeService  employeeService;
    
    @Autowired
    private DepartmentService departmentService;
    
    @Autowired
    private DrawBusiness drawBusiness;
    
    
    private Department dummyDepartment(String code) {
    
		Department department = new Department();
		department.setLocation("İstanbul");
		department.setName("ABC TECH / " + code);
		department.setCode(code);
		
		return department;
    }
    
    
    private Department createDepartment(String code) {
    	
    		Department department = dummyDepartment(code);
    		return departmentService.add(department);
    }
    
    private Department createDepartment(String code, String location) {
    	
    		Department department = dummyDepartment(code);
    		department.setLocation(location);
    		return departmentService.add(department);
    }
    
    
    private Employee dummyEmployee(String employeeNo, Department department) {
		Employee employee = new Employee();
		employee.setBirthDate(DateUtil.stringToDate("01-05-1980"));
		employee.setDepartment(department);
		employee.setEmployeeNo(employeeNo);
		employee.setName("Yasin");
		employee.setSurname("Kaya");
		employee.setSalary(BigDecimal.valueOf(8000));
		employee.setStartDate(new Date());
		
		return employee;
    }
    
    private void createEmployee(String employeeNo, String code) {
    	
		Department department = createDepartment(code);
		
		Employee employee = dummyEmployee(employeeNo, department);
		employeeService.add(employee);
    }
    
    
    private void createEmployee(String employeeNo, String startDate, String salary, Department department) {
    	
    		Employee employee = dummyEmployee(employeeNo, department);
    		employee.setStartDate(DateUtil.stringToDate(startDate));
    		employee.setSalary(new BigDecimal(salary));
    		employeeService.add(employee);
    }
    
    
    
    /****** CRUD Service Test for Department ******/
    @Test
    public void createDepartmentTest() {
    		String code = "IT";
    		createDepartment(code);
    		
    		Department foundDepartment = departmentService.findByCode(code);
    		assertTrue(foundDepartment.getCode().equals(code));
    }
    
    @Test
    public void updateDepartmentTest() {
		String code = "HR";
		createDepartment(code);
		
		String newLocation = "London";
		Department department = departmentService.findByCode(code);
		department.setLocation(newLocation);
		
		departmentService.update(department);
		
		Department foundDepartment = departmentService.findByCode(code);
		assertTrue(foundDepartment.getLocation().equals(newLocation));
    }

    @Test
    public void deleteDepartmentTest() {
    		String code = "DWH";
    		createDepartment(code);
    		
    		Department department = departmentService.findByCode(code);
    		departmentService.delete(department.getId());
    		
    		Department foundDepartment = departmentService.findByCode(code);
    		assertTrue(foundDepartment == null);
    }
    
    
    /****** CRUD Service Test for Employee ******/
    @Test
    public void createEmployeeTest() {
    	
		String code = "AI";
    		String employeeNo = "0001";
		createEmployee(employeeNo, code);
		
		Employee foundEmployee = employeeService.findByEmployeeNo(employeeNo);
		assertTrue(foundEmployee.getEmployeeNo().equals(employeeNo));
    }
    
    @Test
    public void updateEmployeeTest() {
		String code = "MARKETING";
		String employeeNo = "0002";
		createEmployee(employeeNo, code);

    	
		BigDecimal newSalary = new BigDecimal("9000");
		
		Employee employee = employeeService.findByEmployeeNo(employeeNo);
		employee.setSalary(newSalary);
		employeeService.update(employee);
		
		Employee foundEmployee = employeeService.findByEmployeeNo(employeeNo);
		assertTrue(foundEmployee.getSalary().compareTo(newSalary) == 0);
    	
    }
    
    @Test
    public void deleteEmployeeTest() {
    	
		String code = "MIDDLEWARE";
		String employeeNo = "0003";
		createEmployee(employeeNo, code);
		
		Employee employee = employeeService.findByEmployeeNo(employeeNo);
		employeeService.delete(employee.getId());
    	
    		Employee foundEmployee = employeeService.findByEmployeeNo(employeeNo);
    		assertTrue(foundEmployee == null);
    }
    
    
    /****** employees that started after a specific date and their income is greater than specific amount ******/
    @Test
    public void findEmployeesByStartDateAndSalaryTest() {
		
    		Department department = createDepartment("FINANCE");
    		createEmployee("0004", "01-01-2021", "15000", department);
    		createEmployee("0005", "01-02-2022", "5000", department);
    		createEmployee("0006", "01-03-2022", "11000", department);
    		createEmployee("0007", "01-04-2022", "12000", department);
    		
    		Date startDate = DateUtil.stringToDate("01-01-2022");
    		BigDecimal salary = new BigDecimal("10000");
		
		List<Employee> foundEmployees = employeeService.findByStartDateAndSalary(startDate, salary);
		assertTrue(foundEmployees.size() == 2);
    }

    
    
    /****** update the office location of all the employees of a specific department ******/
    @Test
    public void updateOfficeLocationForAllTest() {
    	
    		String code = "OPERATION";
    		String oldLocation = "Berlin";
    		String newLocation = "Cardiff";
    		
    		Department department = createDepartment(code, oldLocation);
		createEmployee("0008", "01-01-2021", "1500", department);
		createEmployee("0009", "01-02-2022", "500", department);
		createEmployee("0010", "01-03-2022", "1100", department);
		
		departmentService.updateLocation(code, newLocation);
		
		List<Employee> employees = employeeService.find();
		
		AtomicInteger oldLocationCount = new AtomicInteger(0);
		AtomicInteger newLocationCount = new AtomicInteger(0);
		employees.forEach(employee-> { 				// lambda usage
			Department dept = employee.getDepartment();
			if (dept.getCode().equals(code)) {
				if (dept.getLocation().equals(oldLocation)) {
					oldLocationCount.getAndIncrement();
				} else if (dept.getLocation().equals(newLocation)) {
					newLocationCount.getAndIncrement();
				}
			}
		});
		
    		assertTrue(oldLocationCount.get() == 0 && newLocationCount.get() >= 3);
    }
    
    
    /****** prize draw every month at a specific time and saves it to the database. ******/
    @Test
    public void drawPrizeTest() {
    		
		Date now = new Date();
		String dateStr = DateUtil.dateToString(now); // dd-MM-yyyy HH:mm:ss formatted
		
		String day = dateStr.substring(0,2);
		String hour = dateStr.substring(11,13);
		String minute = dateStr.substring(14,16);
		String period = dateStr.substring(3, 10);
		
		int currMinute = drawBusiness.convertToMinute(day + "-" + hour + "-" +  minute);

    		
    		try {
    			Draw draw = drawBusiness.draw();
    			Employee employeeWinner =  drawBusiness.findWinner(period).getWinner();
    			
    			assertTrue(employeeWinner.getId() == draw.getWinner().getId());
    		} catch (DrawException e) {
    			
    			if (e.getCode() == DEC.NOT_START) {
    				assertTrue(currMinute < drawBusiness.getDrawStartTimeInt());
    			} else if (e.getCode() == DEC.EXPIRED) {
    				assertTrue(currMinute > drawBusiness.getDrawEndTimeInt());    			
    			} else if (e.getCode() == DEC.COMPLETED) {
    				Draw draw =  drawBusiness.findWinner(period);
    				assertTrue(draw != null);
    			} else {
    				assertTrue(false);
    			}
		}
    }
    
}
