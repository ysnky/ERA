package com.macademia.era.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
* created by ysnky on Jun 14, 2022
*
*/

@Component
public class BaseController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

//	protected Employee	toEmployee(InputData inputData) {
//		return GlobalUtil.toModel(inputData.getData(), Employee.class);
//	}
//	
//	protected Department	toDepartment(InputData inputData) {
//		return GlobalUtil.toModel(inputData.getData(), Department.class);
//	}
}
