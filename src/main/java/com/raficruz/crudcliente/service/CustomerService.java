package com.raficruz.crudcliente.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.raficruz.crudcliente.handler.exception.NotFoundException;
import com.raficruz.crudcliente.model.Customer;

public interface CustomerService {

	public Customer saveCustomer(final Customer customer);
	public void deleteCustomer(final Customer customer) throws NotFoundException;
	public Page<Customer> findAll(final Pageable page, final Long id, final String nome, final LocalDate nascimento, final String cpf, final String sexo);
	public Optional<Customer> findById(final Long id) throws NotFoundException;
	public Optional<Customer> findByCPF(final String cpf) throws NotFoundException;

}
