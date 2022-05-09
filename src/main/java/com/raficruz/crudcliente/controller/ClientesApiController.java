package com.raficruz.crudcliente.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.raficruz.crudcliente.handler.BusinessException;
import com.raficruz.crudcliente.handler.NotFoundException;
import com.raficruz.crudcliente.model.CustomerDTO;
import com.raficruz.crudcliente.service.CustomerService;

@Controller
public class ClientesApiController implements ClientesApi {

	@Autowired
	private CustomerService service;

	@Override
	public ResponseEntity<Void> addCustomer(@Valid CustomerDTO customer) {
		service.addCustomer(customer);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Void> deleteCustomer(Long clienteId) throws NotFoundException {
		List<CustomerDTO> customers = service.findAll(clienteId, null, null, null, null);
		if(CollectionUtils.isEmpty(customers)) {
			throw new BusinessException("Cliente não pode ser deletado", "Cliente não existe");
		}
		service.deleteCustomer(clienteId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<CustomerDTO>> findAll(@Valid Long id, @Valid @Size(max = 40) String nome,
			@Valid LocalDate nascimento, @Valid @Size(max = 11) String cpf, @Valid String sexo) {
		List<CustomerDTO> customers = service.findAll(id, nome, nascimento, cpf, sexo);
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CustomerDTO> getCustomerByCPF(String cpf) throws NotFoundException {
		return new ResponseEntity<>(service.getCustomerByCPF(cpf), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> updateCustomer(Long clienteId, @Valid CustomerDTO customer) {
		List<CustomerDTO> customers = service.findAll(clienteId, null, null, null, null);
		if(CollectionUtils.isEmpty(customers)) {
			throw new BusinessException("Cliente não pode ser atualizado", "Cliente não existe");
		}
		service.addCustomer(customer);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
