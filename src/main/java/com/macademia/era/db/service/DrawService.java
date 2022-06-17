package com.macademia.era.db.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.macademia.era.db.entity.Draw;
import com.macademia.era.db.entity.Employee;
import com.macademia.era.db.repository.DrawRepository;

/**
 * created by ysnky on Jun 14, 2022
 *
 */

@Service
public class DrawService {
	
	@Autowired DrawRepository repository;
	
	public Draw create(Draw draw) {
		return repository.save(draw);
	}

	
	public List<Draw> find() {
		return repository.findAll();
	}
	
	public Draw update(Draw draw) {
		
		Draw currDraw = repository.findById(draw.getId()).get();
		
		currDraw.setPeriod(draw.getPeriod());
		currDraw.setDrawDate(draw.getDrawDate());
		currDraw.setWinner(draw.getWinner());
		
		return repository.save(currDraw);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}


	public List<Draw> findByPeriod(String period) {
		return repository.findByPeriod(period);
	}


	public Draw create(String period, Employee winnerEmployee) {
		
		Draw draw = new Draw();
		
		draw.setPeriod(period);
		draw.setDrawDate(new Date());
		draw.setWinner(winnerEmployee);
		
		return repository.save(draw);

	}
	
}
