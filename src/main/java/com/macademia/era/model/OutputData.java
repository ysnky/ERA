package com.macademia.era.model;

import com.macademia.era.util.AppConstant;

import lombok.Getter;
import lombok.Setter;

/**
 * created by ysnky on Jun 14, 2022
 *
 */

@Getter
@Setter
public class OutputData<T> {

	private String returnCode;
	private String returnMessage;
	private T data;

	
	public OutputData() {
	}
	
	public OutputData(boolean success) {
		super();
		if (success) {
			initSuccess();
		} else {
			initError();
		}
	}
	
	public OutputData(T data) {
		super();
		initSuccess();		
		this.data = data;
	}
	
	private void initSuccess() {
		this.returnCode = AppConstant.RETURN_CODE_SUCCESS;
		this.returnMessage = AppConstant.RETURN_MSG_SUCCESS_DEFAULT;
	}
	
	private void initError() {
		this.returnCode = AppConstant.RETURN_CODE_ERROR;
		this.returnMessage = AppConstant.RETURN_MSG_ERROR_DEFAULT;
	}
	
}
