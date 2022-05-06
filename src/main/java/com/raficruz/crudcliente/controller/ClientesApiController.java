package com.raficruz.crudcliente.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.threeten.bp.LocalDate;

import com.raficruz.crudcliente.model.Customer;

import io.swagger.annotations.ApiParam;

@Controller
public class ClientesApiController implements ClientesApi {


	public ResponseEntity<Void> addCustomer(
			@Valid
			@ApiParam(value = "Customer object that needs to be added to the store", required = true)
			@RequestBody Customer body)
	{
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<Void> deleteCustomer(
			@ApiParam(value = "customer id to delete", required = true)
			@PathVariable("clienteId")
			final Long clienteId)
	{
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<List<Customer>> findAll(
			@Size(max = 40)
			@ApiParam(value = "Customer Name")
			@Valid @RequestParam(value = "nome", required = false)
			final String nome,
			
			@ApiParam(value = "Customer birthday")
			@Valid
			@RequestParam(value = "nascimento", required = false)
			final LocalDate nascimento,
			
			@Size(max = 11)
			@ApiParam(value = "CPF")
			@Valid @RequestParam(value = "cpf", required = false)
			final String cpf,
			
			@ApiParam(value = "Gender", allowableValues = "M, F")
			@Valid
			@RequestParam(value = "sexo", required = false)
			final String sexo)
	{
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<Customer> getCustomerById(
			@ApiParam(value = "ID of customer to return", required = true)
			@PathVariable("clienteId")
			Long clienteId)
	{
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<Void> updateCustomer(
			@Valid
			@ApiParam(value = "Customer object that needs to be added to the store", required = true)
			@RequestBody Customer body)
	{
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

}
