package com.macademia.era.db.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.macademia.era.db.entity.Employee;

/**
 * created by ysnky on Jun 14, 2022
 *
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	  List<Employee> findAll();
	  
	  Employee findByEmployeeNo(String employeeNo);
	  
	  @Query("SELECT p FROM Employee p WHERE p.startDate > :startDate AND p.salary > :salary")
	  public List<Employee> findByStartDateAndSalary(@Param("startDate") Date startDate, @Param("salary") BigDecimal salary); 
}
