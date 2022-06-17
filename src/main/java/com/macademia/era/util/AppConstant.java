package com.macademia.era.util;

/**
 * created by ysnky on Jun 14, 2022
 *
 */
public class AppConstant {
	
	public final static String DATE_FORMAT = "dd-MM-yyyy";
	public final static String DATE_FORMAT_FULL = "dd-MM-yyyy HH:mm:ss";
	
	
	public final static String RETURN_CODE_SUCCESS 	= "0";
	public final static String RETURN_CODE_ERROR 	= "1";
	
	public final static String RETURN_MSG_SUCCESS_DEFAULT = "operation is completed successfully";
	public final static String RETURN_MSG_ERROR_DEFAULT = "An error occured...";

	
	public enum OPERATION_TYPE {
		INSERT,
		UPDATE,
		DELETE,
		FETCH
	}

}
