package com.raficruz.crudcliente.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Customer
 */
@Entity
public class Customer implements Serializable {

	private static final long serialVersionUID = -8630233981550309829L;

	public Customer() {
		super();
	}

	public Customer(Long id, String name, LocalDate birthday, String cpf, SexoEnum gender) {
		super();
		this.id = id;
		this.name = name;
		this.birthday = birthday;
		this.cpf = cpf;
		this.gender = gender;
	}

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "nome", nullable = false)
	private String name;

	@Column(name = "nascimento", nullable = false)
	private LocalDate birthday;

	@Cpf
	@Column(name = "cpf", unique = true, nullable = false)
	private String cpf;

	@Enumerated(EnumType.STRING)
	@Column(name = "sexo", nullable = false)
	private SexoEnum gender;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public String getCpf() {
		return cpf;
	}

	public SexoEnum getGender() {
		return gender;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setGender(SexoEnum gender) {
		this.gender = gender;
	}

	public CustomerDTO toDTO() {
		return new CustomerDTO(this.id, this.name, this.birthday, this.cpf, this.gender);
	}
}
