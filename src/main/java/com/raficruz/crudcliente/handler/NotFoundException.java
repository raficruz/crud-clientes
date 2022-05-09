package com.raficruz.crudcliente.handler;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4513357775525967862L;

    public NotFoundException (String msg) {
        super(msg);
    }

}
