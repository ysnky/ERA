package com.macademia.era.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.macademia.era.db.entity.Department;

/**
 * created by ysnky on Jun 14, 2022
 *
 */
@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {
	List<Department> findAll();
	
	Department findByCode(String code);
}
