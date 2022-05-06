package com.raficruz.crudcliente.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum SexoEnum {
	MASCULINO("M"),
	FEMININO("F");

	private String value;

	SexoEnum(String value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static SexoEnum fromValue(String text) {
		for (SexoEnum b : SexoEnum.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}
}
