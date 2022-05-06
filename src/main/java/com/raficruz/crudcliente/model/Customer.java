package com.raficruz.crudcliente.model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Customer
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Validated
public class Customer implements Serializable {

	private static final long serialVersionUID = -8630233981550309829L;

	@ApiModelProperty(value = "id")
	@JsonProperty("id")
	private Long id;

	@NotNull
	@Size(max = 40)
	@JsonProperty("nome")
	@ApiModelProperty(example = "Rafael Cruz", required = true)
	private String nome;

	@Valid
	@NotNull
	@ApiModelProperty(example = "01/01/1971", required = true)
	@JsonProperty("nascimento")
	private LocalDate nascimento;

	@Valid
	@NotNull
	@ApiModelProperty(example = "12345678901", required = true)
	@JsonProperty("cpf")
	@Size(max = 11)
	private String cpf;

	@Valid
	@NotNull
	@JsonProperty("sexo")
	@ApiModelProperty(example = "M", required = true)
	private SexoEnum sexo;

}
