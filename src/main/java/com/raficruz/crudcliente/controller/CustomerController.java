package com.raficruz.crudcliente.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.raficruz.crudcliente.handler.exception.BusinessException;
import com.raficruz.crudcliente.handler.exception.NotFoundException;
import com.raficruz.crudcliente.model.Customer;
import com.raficruz.crudcliente.model.dto.CustomerDTO;
import com.raficruz.crudcliente.service.CustomerService;

@Controller
public class CustomerController implements ClientesApi {

	@Autowired
	private CustomerService service;

	@Override
	public ResponseEntity<Void> saveCustomer(@Valid CustomerDTO customerDTO) {
		Optional<Customer> customer = service.findByCPF(customerDTO.getCpf());

		if(customer.isPresent()) {
			throw new BusinessException("Cliente não pode ser cadastrado", "CPF já cadastrado!");
		}

		Customer customerModel = new Customer();
		BeanUtils.copyProperties(customerDTO, customerModel);
		service.saveCustomer(customerModel);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Void> updateCustomer(Long clienteId, @Valid CustomerDTO customerDTO) {
		Optional<Customer> customerModel = service.findById(clienteId);

		if(!customerModel.isPresent()) {
			throw new BusinessException("Cliente não pode ser atualizado", "Cliente não existe");
		}

		if(!customerDTO.getCpf().equals(customerModel.get().getCpf())) {
			throw new BusinessException("Cliente não pode ser atualizado", "O CPF não pode ser alterado!");
		}

		BeanUtils.copyProperties(customerDTO, customerModel.get());
		customerModel.get().setId(clienteId);
		service.saveCustomer(customerModel.get());
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@Override
	public ResponseEntity<Void> deleteCustomer(Long clienteId) throws NotFoundException {
		Optional<Customer> customerModel = service.findById(clienteId);
		if(!customerModel.isPresent()) {
			throw new BusinessException("Cliente não pode ser deletado", "Cliente não existe");
		}

		service.deleteCustomer(customerModel.get());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Customer>> findAll(Pageable page, Long id, String nome,
			LocalDate nascimento, String cpf, String sexo)
	{
		Page<Customer> customers = service.findAll(page, id, nome, nascimento, cpf, sexo);
		return new ResponseEntity<>(customers.getContent(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Customer> getCustomerByCPF(String cpf) throws NotFoundException {
		Optional<Customer> customer = service.findByCPF(cpf);
		if(!customer.isPresent()) {
			throw new NotFoundException("Cliente não encontrado");
		}
		return new ResponseEntity<>(customer.get(), HttpStatus.OK);
	}
}
