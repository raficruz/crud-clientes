package com.raficruz.crudcliente.handler;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 5278681842883771286L;

    final String description;
    
	public BusinessException (String msg, String description) {
        super(msg);
        this.description = description;
    }

	public String getDescription() {
		return description;
	}

}
