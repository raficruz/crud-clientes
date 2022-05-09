package com.raficruz.crudcliente.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.raficruz.crudcliente.handler.NotFoundException;
import com.raficruz.crudcliente.model.Customer;
import com.raficruz.crudcliente.model.CustomerDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;

@Validated
@Api(value = "clientes")
@RequestMapping(value = "/v1")
public interface ClientesApi {

	@ApiOperation(value = "Add a new customer to the store",
				  nickname = "addCustomer",
				  notes = "",
				  authorizations = {
					  	@Authorization(value = "customerstore_auth", scopes = {
					  	@AuthorizationScope(scope = "write:customers", description = "modify customers in your account"),
					  	@AuthorizationScope(scope = "read:customers", description = "read your customers")})
				  },
				  tags = {"cliente"})
	@ApiResponses(value = {
					@ApiResponse(code = 201, message = "Criado"),
					@ApiResponse(code = 204, message = "Não encontrado"),
					@ApiResponse(code = 409, message = "Dados obrigatórios e inválidos")
					})
	@PostMapping(value = "/clientes",
				consumes = {"application/json" },
				produces = { "application/json" })
	ResponseEntity<Void> addCustomer(
			@Valid
			@ApiParam(value = "Customer object that needs to be added to the store", required = true)
			@RequestBody
			final CustomerDTO customer);


	@ApiOperation(value = "Deletes a customer",
				  nickname = "deleteCustomer",
				  authorizations = {
						  @Authorization(value = "customerstore_auth",
								  		scopes = {
							  				@AuthorizationScope(scope = "write:customers", description = "modify customers in your account"),
							  				@AuthorizationScope(scope = "read:customers", description = "read your customers") 
						  				})},
				  tags = {"cliente"})
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "successful operation"),
					@ApiResponse(code = 400, message = "Invalid ID supplied"),
					@ApiResponse(code = 404, message = "Customer not found") })
	@DeleteMapping(value = "/clientes/{clienteId}",
					produces = { "application/json" })
	ResponseEntity<Void> deleteCustomer(
							@ApiParam(value = "customer id to delete", required = true)
							@PathVariable("clienteId")
							final Long clienteId) throws NotFoundException;


	@ApiOperation(value = "Finds all clients by filters",
					nickname = "findAll",
					notes = "Multiple parameters can be provided to find customers",
					response = Customer.class,
					responseContainer = "List",
					authorizations = {
							@Authorization(value = "customerstore_auth",
											scopes = {
													@AuthorizationScope(scope = "write:customers", description = "modify customers in your account"),
													@AuthorizationScope(scope = "read:customers", description = "read your customers")
											})},
					tags = {"cliente", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "successful operation",response = Customer.class, responseContainer = "List"),
			@ApiResponse(code = 204, message = "Não encontrado"),
			@ApiResponse(code = 400, message = "Invalid status value") })
	@GetMapping(value = "/clientes", produces = { "application/json" })
	ResponseEntity<List<CustomerDTO>> findAll(

			@Valid
			@ApiParam(value = "Id")
			@RequestParam(value = "id", required = false)
			final Long id,
			
			@Valid
			@Size(max = 40)
			@ApiParam(value = "Customer Name")
			@RequestParam(value = "nome", required = false)
			final String nome,
			
			@Valid
			@ApiParam(value = "birthday")
			@RequestParam(value = "nascimento", required = false)
			final LocalDate nascimento,
			
			@Valid
			@Size(max = 11)
			@ApiParam(value = "CPF")
			@RequestParam(value = "cpf", required = false)
			final String cpf,
			
			@Valid
			@ApiParam(value = "Gender", allowableValues = "M, F")
			@RequestParam(value = "sexo", required = false)
			final String sexo);


	@ApiOperation(value = "Find customer by CPF",
					nickname = "getCustomerByCPF",
					notes = "Returns a single customer",
					response = Customer.class,
					authorizations = {@Authorization(value = "api_key")},
					tags = { "cliente", })
	@ApiResponses(value = {
					@ApiResponse(code = 200, message = "successful operation", response = Customer.class),
					@ApiResponse(code = 204, message = "Não encontrado"),
					@ApiResponse(code = 400, message = "Invalid status value"),
					@ApiResponse(code = 404, message = "customer not found") })
	@GetMapping(value = "/clientes/{cpf}",
				produces = { "application/json" })
	ResponseEntity<CustomerDTO> getCustomerByCPF(
			@ApiParam(value = "CPF of customer to return", required = true)
			@PathVariable("cpf")
			final String cpf) throws NotFoundException;

	
	@ApiOperation(value = "Update an existing customer", nickname = "updateCustomer", notes = "", authorizations = {
			@Authorization(value = "customerstore_auth",
							scopes = {
									@AuthorizationScope(scope = "write:customers", description = "modify customers in your account"),
									@AuthorizationScope(scope = "read:customers", description = "read your customers") }) },
					tags = {"cliente", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Atualizado"),
			@ApiResponse(code = 204, message = "Não encontrado"),
			@ApiResponse(code = 409, message = "Dados obrigatórios e inválidos") })
	@PutMapping(value = "/clientes/{clienteId}", produces = { "application/json" }, consumes = {"application/json" })
	ResponseEntity<Void> updateCustomer(
			@ApiParam(value = "customer id to delete", required = true)
			@PathVariable("clienteId")
			final Long clienteId,
			@Valid
			@ApiParam(value = "Customer object that needs to be added to the store", required = true)
			@RequestBody
			final CustomerDTO customer);

}
