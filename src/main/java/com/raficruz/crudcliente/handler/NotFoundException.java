package com.raficruz.crudcliente.handler;

public class NotFoundException extends ApiException {

	private static final long serialVersionUID = 4513357775525967862L;

	private final int code;

    public NotFoundException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }

    public int getCode() {
		return code;
	}
}
