package com.raficruz.crudcliente.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Customer
 */
@Validated
public class CustomerDTO implements Serializable {

	private static final long serialVersionUID = -7899833547722122701L;

	public CustomerDTO() {
		super();
	}

	public CustomerDTO(final Long id, final String nome, final LocalDate nascimento, final String cpf, final SexoEnum sexo) {
		super();
		this.id = id;
		this.nome = nome;
		this.nascimento = nascimento;
		this.cpf = cpf;
		this.sexo = sexo;
	}

	@ApiModelProperty(value = "id")
	@JsonProperty("id")
	private Long id;

	@Valid
	@NotNull
	@Size(max = 40, message = "O tamanho máximo deve ser menor ou igual a 40 caracteres")
	@JsonProperty("nome")
	@ApiModelProperty(example = "Rafael Cruz", required = true)
	private String nome;

	@Valid
	@NotNull(message = "A data de nascimento deve ser informada!")
	@ApiModelProperty(example = "01/01/1971", required = true)
	@JsonProperty("nascimento")
	private LocalDate nascimento;

	@Valid
	@NotNull(message = "CPF deve ser informado. Valores aceitos: M ou F")
	@Cpf
	@ApiModelProperty(example = "12345678901", required = true)
	@JsonProperty("cpf")
	private String cpf;

	@Valid
	@NotNull(message = "Sexo deve ser informado. Valores aceitos: M ou F")
	@JsonProperty("sexo")
	@ApiModelProperty(example = "M", required = true)
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

	public Customer toEntity() {
		return new Customer(id, nome, nascimento, cpf, sexo);
	}

}
