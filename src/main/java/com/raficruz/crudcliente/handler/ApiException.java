package com.raficruz.crudcliente.handler;

public class ApiException extends Exception{

	private static final long serialVersionUID = 9168436522866252499L;
	private final int code;

    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
		return code;
	}
}
