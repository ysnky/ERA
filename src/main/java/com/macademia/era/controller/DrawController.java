package com.macademia.era.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.macademia.era.business.DrawBusiness;
import com.macademia.era.db.entity.Draw;
import com.macademia.era.exception.DrawException;
import com.macademia.era.model.OutputData;
import com.macademia.era.util.GlobalUtil;

/**
 * created by ysnky on Jun 14, 2022
 *
 */

@RestController
@RequestMapping("/api/prize")
public class DrawController extends BaseController {

	@Autowired
	DrawBusiness drawBusiness;

	
	@GetMapping("/draw")
	public OutputData<Draw> draw() { // draw for the current month
		
		try {
			Draw draw = drawBusiness.draw();
			return new OutputData<Draw>(draw);
		} catch (DrawException e) {
			logger.error(GlobalUtil.exceptionToString(e));
			OutputData<Draw> output = new OutputData<Draw>();
			output.setReturnCode(e.getCode().toString());
			output.setReturnMessage(e.getMessage());
			return output;
		} catch (Exception e) {
			String msg = GlobalUtil.exceptionToString(e);
			logger.error(msg);
			OutputData<Draw> output = new OutputData<Draw>(false);
			output.setReturnMessage(msg);
			return output;
		}
	}

	@GetMapping("/winner")
	public OutputData<Draw> winner(@RequestParam String period) {  //find the winner of any time, null for current month, period format mm-yyyy for march 2022, 03-2022
		
		try {
			Draw draw = drawBusiness.findWinner(period);
			return new OutputData<Draw>(draw);
		} catch (Exception e) {
			return new OutputData<Draw>(false);
		}
	}
	
	

}
