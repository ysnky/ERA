package com.macademia.era.exception;

/**
 * created by ysnky on Jun 15, 2022
 *
 */
public class DrawException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DEC code;
	
	public static enum DEC {
		NOT_START,
		EXPIRED,
		COMPLETED
	}
	
	public DrawException(DEC code, String msg) {
        super(msg);
        this.code = code;
    }

	public DEC getCode() {
		return code;
	}

	public void setCode(DEC code) {
		this.code = code;
	}
}
