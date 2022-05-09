package com.raficruz.crudcliente.handler;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_EMPTY)
public class ApiErrorResponse implements Serializable {

	private static final long serialVersionUID = 9168436522866252499L;

	private int code;
	private String message;
	private String description;
	private List<String> errors;

	public ApiErrorResponse(int code, String message, String description, List<String> errors) {
		super();
		this.code = code;
		this.message = message;
		this.description = description;
		this.errors = errors;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
