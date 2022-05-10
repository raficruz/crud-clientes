package com.raficruz.crudcliente.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.raficruz.crudcliente.model.validator.Cpf;

import io.swagger.annotations.ApiModelProperty;

/**
 * Customer
 */
@Entity
public class Customer implements Serializable {

	private static final long serialVersionUID = -8630233981550309829L;

	public Customer() {
		super();
	}

	public Customer(Long id, String nome, LocalDate nascimento, String cpf, SexoEnum sexo) {
		super();
		this.id = id;
		this.nome = nome;
		this.nascimento = nascimento;
		this.cpf = cpf;
		this.sexo = sexo;
	}

	@Id
	@GeneratedValue
	@ApiModelProperty(value = "id")
	@JsonProperty("id")
	private Long id;

	@Valid
	@JsonProperty("nome")
	@ApiModelProperty(example = "Rafael Cruz", required = true)
	@NotNull
	@Size(max = 40, message = "O tamanho máximo deve ser menor ou igual a 40 caracteres")
	@Column(name = "nome", nullable = false)
	private String nome;

	@Valid
	@ApiModelProperty(example = "01/01/1971", required = true)
	@JsonProperty("nascimento")
	@NotNull(message = "A data de nascimento deve ser informada!")
	@Column(name = "nascimento", nullable = false)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate nascimento;

	@Valid
	@Cpf
	@NotNull(message = "CPF deve ser informado. Valores aceitos: M ou F")
	@ApiModelProperty(example = "12345678901", required = true)
	@JsonProperty("cpf")
	@Column(name = "cpf", unique = true, nullable = false)
	private String cpf;

	@Valid
	@NotNull(message = "Sexo deve ser informado. Valores aceitos: M ou F")
	@JsonProperty("sexo")
	@ApiModelProperty(example = "M", required = true)
	@Enumerated(EnumType.STRING)
	@Column(name = "sexo", nullable = false)
	private SexoEnum sexo;

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public LocalDate getNascimento() {
		return nascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public SexoEnum getSexo() {
		return sexo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setSexo(SexoEnum sexo) {
		this.sexo = sexo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, id, nascimento, nome, sexo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(id, other.id)
				&& Objects.equals(nascimento, other.nascimento) && Objects.equals(nome, other.nome)
				&& sexo == other.sexo;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", nome=" + nome + ", nascimento=" + nascimento + ", cpf=" + cpf + ", sexo="
				+ sexo + "]";
	}
}
