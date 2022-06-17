package com.macademia.era.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.macademia.era.db.entity.Draw;

/**
 * created by ysnky on Jun 14, 2022
 *
 */
@Repository
public interface DrawRepository extends CrudRepository<Draw, Long> {
	List<Draw> findAll();
	List<Draw> findByPeriod(String period);
}
