package com.macademia.era.model;

import com.macademia.era.util.AppConstant.OPERATION_TYPE;

import lombok.Getter;
import lombok.Setter;

/**
 * created by ysnky on Jun 14, 2022
 *
 */

@Getter
@Setter
public class InputData {
	
	private OPERATION_TYPE operationType;
	private Object data;
}
