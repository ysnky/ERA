package com.macademia.era.business;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.macademia.era.db.entity.Draw;
import com.macademia.era.db.entity.Employee;
import com.macademia.era.db.service.DrawService;
import com.macademia.era.db.service.EmployeeService;
import com.macademia.era.exception.DrawException;
import com.macademia.era.exception.DrawException.DEC;
import com.macademia.era.util.DateUtil;

/**
 * created by ysnky on Jun 15, 2022
 *
 */

@Service
public class DrawBusiness {
	@Autowired 
	EmployeeService employeeService;

	@Autowired 
	DrawService drawService;
	
	@Value("${draw.start.time}")
	private String drawStartTimeStr;

	@Value("${draw.end.time}")
	private String drawEndTimeStr;
	
	private int drawStartTimeInt;
	private int drawEndTimeInt;
	
	public int convertToMinute(String timeStr) {
		
		String[] timeAry = timeStr.split("-");
		return ((Integer.parseInt(timeAry[0]) -1) * 24 * 60) + (Integer.parseInt(timeAry[1]) * 60) + Integer.parseInt(timeAry[2]);    
	}
	
	@PostConstruct
	public void init() {
		drawStartTimeInt = convertToMinute(drawStartTimeStr);
		drawEndTimeInt = convertToMinute(drawEndTimeStr);
	}
	
	public Draw draw() throws DrawException {
		
		Date now = new Date();
		String dateStr = DateUtil.dateToString(now); // dd-MM-yyyy HH:mm:ss formatted
		
		String day = dateStr.substring(0,2);
		String hour = dateStr.substring(11,13);
		String minute = dateStr.substring(14,16);
		String period = dateStr.substring(3, 10);
		
		int currMinute = convertToMinute(day + "-" + hour + "-" +  minute);
		
		if (currMinute < drawStartTimeInt) { // draw has not started yet
			throw new DrawException(DEC.NOT_START, "Prize draw has not started yet for this month: " + period);
		} else if (currMinute > drawEndTimeInt) { // draw has ended
			throw new DrawException(DEC.EXPIRED, "Prize draw has ended for this month: " + period);
		}
		
		
		List<Draw> draws = drawService.findByPeriod(period);
		
		if (draws.size() > 0) { // winner had already choosen.
			throw new DrawException(DEC.COMPLETED, "Prize draw had already choosen winner for this month: " + period);
		}
		
		
		int employeeCount =  (int) (employeeService.count() % Integer.MAX_VALUE);
	    int index = (int) (Math.random() * (employeeCount));
	    
	    List<Employee> employeeList = employeeService.find();
	    
	    Employee winnerEmployee = employeeList.get(index);
	    
	    return drawService.create(period, winnerEmployee);
	}

	
	
	
	
	public Draw findWinner(String period) {
		
		if (period == null) {
			Date now = new Date();
			String dateStr = DateUtil.dateToString(now); // dd-MM-yyyy HH:mm:ss formatted
			
			period = dateStr.substring(3, 10);
		}
		
		List<Draw> draws = drawService.findByPeriod(period);
		return draws.size() > 0 ? draws.get(0) : null;
	}

	public int getDrawStartTimeInt() {
		return drawStartTimeInt;
	}

	public void setDrawStartTimeInt(int drawStartTimeInt) {
		this.drawStartTimeInt = drawStartTimeInt;
	}

	public int getDrawEndTimeInt() {
		return drawEndTimeInt;
	}

	public void setDrawEndTimeInt(int drawEndTimeInt) {
		this.drawEndTimeInt = drawEndTimeInt;
	}
	
	
	
}
