package com.macademia.era;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.macademia.era.business.DrawBusiness;
import com.macademia.era.db.entity.Department;
import com.macademia.era.db.entity.Draw;
import com.macademia.era.db.entity.Employee;
import com.macademia.era.exception.DrawException.DEC;
import com.macademia.era.model.OutputData;
import com.macademia.era.util.AppConstant;
import com.macademia.era.util.DateUtil;

/**
 * created by ysnky on Jun 15, 2022
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ERAIntegrationTest {
	
	@LocalServerPort
    int port;
 
	@Autowired
	private TestRestTemplate restTemplate;
	
    @Autowired
    private DrawBusiness drawBusiness;


    
    private Department dummyDepartment(String code) {
    		return dummyDepartment(code, "İstanbul");
    }
    
    private Department dummyDepartment(String code, String location) {
    
		Department department = new Department();
		department.setLocation(location);
		department.setName("ABC TECH / " + code);
		department.setCode(code);
		
		return department;
    }
    
    
    
    
    private Department createDepartment(Department department) {
    	
    		ResponseEntity<OutputDataWithDepartment> responseEntity = this.restTemplate
	      .postForEntity("http://localhost:" + port + "/api/department/add", department, OutputDataWithDepartment.class);
	    
	    return responseEntity.getBody().getData();
    }
    
    
    private Department findDepartment(String code) {
    	
	    ResponseEntity<OutputDataWithDepartment> responseEntity = this.restTemplate
	    		.getForEntity("http://localhost:" + port + "/api/department/findByCode/" + code, OutputDataWithDepartment.class);
    	
    		return responseEntity.getBody().getData();
    }
    
    
    
    
    private Employee dummyEmployee(String employeeNo, String startDate, String salary, Department department) {
		Employee employee = new Employee();
		employee.setBirthDate(DateUtil.stringToDate("01-01-1980"));
		employee.setDepartment(department);
		employee.setEmployeeNo(employeeNo);
		employee.setName("Yasin");
		employee.setSurname("Kaya");
		employee.setSalary(new BigDecimal(salary));
		employee.setStartDate(DateUtil.stringToDate(startDate));
		
		return employee;
    }
    
    private Employee dummyEmployee(String employeeNo, Department department) {
    		return dummyEmployee(employeeNo, "01-05-1980", "8000", department);
    }
    
    
    private Employee createEmployee(Employee employee) {
    	
		ResponseEntity<OutputDataWithEmployee> responseEntity = this.restTemplate
      .postForEntity("http://localhost:" + port + "/api/employee/add", employee, OutputDataWithEmployee.class);
    
		return responseEntity.getBody().getData();
    }

    
    private Employee findEmployee(String employeeNo) {
    	
	    ResponseEntity<OutputDataWithEmployee> responseEntity = this.restTemplate
	    		.getForEntity("http://localhost:" + port + "/api/employee/findByEmployeeNo/" + employeeNo, OutputDataWithEmployee.class);
    	
    		return responseEntity.getBody().getData();
    }
    
   
  
    static class OutputDataWithDepartment extends OutputData<Department>{};
    static class OutputDataWithDepartments extends OutputData<List<Department>>{};
    
    static class OutputDataWithEmployee extends OutputData<Employee>{};
    static class OutputDataWithEmployees extends OutputData<List<Employee>>{};
    
    static class OutputDataWithDraw extends OutputData<Draw>{};
    
    
    /****** CRUD Service Test for Department ******/
    @Test
    public void createDepartmentTest() {
    		String code = "IT";
    	    createDepartment(dummyDepartment(code));

    	    Department department = findDepartment(code);
    	    
    	    assertTrue(department.getCode().equals(code));
    }
    
    @Test
    public void updateDepartmentTest() {
		String code = "HR";
		String oldLocation = "İstanbul";
		String newLocation = "London";
		
		createDepartment(dummyDepartment(code, oldLocation));
		
		Department department = findDepartment(code);
	    department.setLocation(newLocation);
	    
	    this.restTemplate
	      .postForEntity("http://localhost:" + port + "/api/department/update", department, OutputDataWithDepartment.class);
	    
	    Department foundDepartment = findDepartment(code);
	    assertTrue(foundDepartment.getLocation().equals(newLocation));
    }

    @Test    
    public void deleteDepartmentTest() {
    		String code = "DWH";
    		createDepartment(dummyDepartment(code));
    		
    		Department department = findDepartment(code);
    	    this.restTemplate
  	      .postForEntity("http://localhost:" + port + "/api/department/delete/" + department.getId(), null, OutputDataWithDepartment.class);
  	    
    	    Department foundDepartment = findDepartment(code);
    		assertTrue(foundDepartment == null);
    }
    
    @Test    
    public void findDepartmentTest() {
	    	ResponseEntity<OutputDataWithDepartments> responseEntity = this.restTemplate
	    		.getForEntity("http://localhost:" + port + "/api/department/find" , OutputDataWithDepartments.class);
	    	
	    	int countOld = responseEntity.getBody().getData().size();

	    	
	    	String code = "AI1";
	    	createDepartment(dummyDepartment(code));
	    	
	    	
	    	responseEntity = this.restTemplate
		    		.getForEntity("http://localhost:" + port + "/api/department/find" , OutputDataWithDepartments.class);
		    	
	    	int countNew = responseEntity.getBody().getData().size();
	    	
	    	assertTrue(countNew == countOld+1);
    }
    
    
    
    /****** CRUD Service Test for Employee ******/
    @Test
    public void createEmployeeTest() {
		String code = "AI";
    		String employeeNo = "0001";
    		
		createEmployee(dummyEmployee(employeeNo, createDepartment(dummyDepartment(code))));
		
		Employee foundEmployee = findEmployee(employeeNo);
		assertTrue(foundEmployee.getEmployeeNo().equals(employeeNo));
    }
    
    @Test
    public void updateEmployeeTest() {
		String code = "MARKETING";
		String employeeNo = "0002";
		BigDecimal newSalary = new BigDecimal("9000");
		
		createEmployee(dummyEmployee(employeeNo, createDepartment(dummyDepartment(code))));
	
		Employee employee = findEmployee(employeeNo);
		employee.setSalary(newSalary);
		
	    this.restTemplate
	      .postForEntity("http://localhost:" + port + "/api/employee/update", employee, OutputDataWithEmployee.class);

		Employee foundEmployee = findEmployee(employeeNo);
		assertTrue(foundEmployee.getSalary().compareTo(newSalary) == 0);
    }
    
    @Test
    public void deleteEmployeeTest() {
    	
		String code = "MIDDLEWARE";
		String employeeNo = "0003";
		
		createEmployee(dummyEmployee(employeeNo, createDepartment(dummyDepartment(code))));
		
		Employee employee = findEmployee(employeeNo);

	    this.restTemplate
	      .postForEntity("http://localhost:" + port + "/api/employee/delete/" + employee.getId(), null, OutputDataWithDepartment.class);
		
		Employee foundEmployee = findEmployee(employeeNo);
		assertTrue(foundEmployee == null);
    }
    
    
    @Test    
    public void findEmployeeTest() {
	    	ResponseEntity<OutputDataWithEmployees> responseEntity = this.restTemplate
	    		.getForEntity("http://localhost:" + port + "/api/employee/find" , OutputDataWithEmployees.class);
	    	
	    	int countOld = responseEntity.getBody().getData().size();

	    	
	    	createEmployee(dummyEmployee("0004", createDepartment(dummyDepartment("Dept"))));	    	
	    	
	    	responseEntity = this.restTemplate
		    		.getForEntity("http://localhost:" + port + "/api/employee/find" , OutputDataWithEmployees.class);
		    	
	    	int countNew = responseEntity.getBody().getData().size();
	    	
	    	assertTrue(countNew == countOld+1);
    }    
    
    /****** employees that started after a specific date and their income is greater than specific amount ******/
    @Test
    public void findByStartDateAndSalaryTest() {
    	
		Department department = createDepartment(dummyDepartment("FINANCE"));
		
		createEmployee(dummyEmployee("0005", "01-01-2021", "15000", department));
		createEmployee(dummyEmployee("0006", "01-02-2022", "5000", department));
		createEmployee(dummyEmployee("0007", "01-03-2022", "11000", department));
		createEmployee(dummyEmployee("0008", "01-04-2022", "12000", department));
    	
	    	ResponseEntity<OutputDataWithEmployees> responseEntity = this.restTemplate
	    			.getForEntity("http://localhost:" + port + "/api/employee/findByStartDateAndSalary?startDate=01-01-2022&salary=10000" , OutputDataWithEmployees.class);
    	
	    	assertTrue(responseEntity.getBody().getData().size() == 2);
    }    
    
    
    
    
    
    /****** update the office location of all the employees of a specific department ******/
    @Test
    public void updateOfficeLocationForAllTest() {
    	
    		String code = "OPERATION";
    		String oldLocation = "Berlin";
    		String newLocation = "Cardiff";
    		
    		Department department = createDepartment(dummyDepartment(code, oldLocation));
		createEmployee(dummyEmployee("0009", "01-01-2021", "1500", department));
		createEmployee(dummyEmployee("00010", "01-02-2022", "500", department));
		createEmployee(dummyEmployee("00011", "01-03-2022", "1100", department));
		
		this.restTemplate
		.postForEntity("http://localhost:" + port + "/api/department/updateLocation?code=" + code + "&location=" +  newLocation, null, OutputDataWithDepartment.class);
    		
		ResponseEntity<OutputDataWithEmployees> responseEntity = this.restTemplate
	    		.getForEntity("http://localhost:" + port + "/api/employee/find" , OutputDataWithEmployees.class);

		
		List<Employee> employees = responseEntity.getBody().getData();
		
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
		
		ResponseEntity<OutputDataWithDraw> responseEntityDraw = this.restTemplate
	    		.getForEntity("http://localhost:" + port + "/api/prize/draw" , OutputDataWithDraw.class);
		
		OutputDataWithDraw outputDraw = responseEntityDraw.getBody();
		if (outputDraw.getReturnCode().equals(AppConstant.RETURN_CODE_SUCCESS)) {
			
			ResponseEntity<OutputDataWithDraw> responseEntityWinner = this.restTemplate
		    		.getForEntity("http://localhost:" + port + "/api/prize/winner?period=" + period , OutputDataWithDraw.class);
			
			assertTrue(outputDraw.getData().getWinner().getId() == responseEntityWinner.getBody().getData().getWinner().getId());
		} else if (outputDraw.getReturnCode().equals(DEC.NOT_START.toString())) {
			assertTrue(currMinute < drawBusiness.getDrawStartTimeInt());
		} else if (outputDraw.getReturnCode().equals(DEC.EXPIRED.toString())) {
			assertTrue(currMinute > drawBusiness.getDrawEndTimeInt());
		} else if (outputDraw.getReturnCode().equals(DEC.COMPLETED.toString())) {

			ResponseEntity<OutputDataWithDraw> responseEntityWinner = this.restTemplate
		    		.getForEntity("http://localhost:" + port + "/api/prize/winner?period=" + period , OutputDataWithDraw.class);

			assertTrue(responseEntityWinner.getBody().getData() != null);
		} else {
			assertTrue(false);
		}
    }
}
