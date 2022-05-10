package com.raficruz.crudcliente.service;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.raficruz.crudcliente.handler.exception.NotFoundException;
import com.raficruz.crudcliente.model.Customer;
import com.raficruz.crudcliente.model.SexoEnum;
import com.raficruz.crudcliente.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository repository;
	
	@Transactional
	@Override
	public Customer saveCustomer(Customer customer) {
		return repository.save(customer);
	}

	@Transactional
	@Override
	public void deleteCustomer(Customer customer) throws NotFoundException {
		repository.delete(customer);
	}

	@Override
	public Optional<Customer> findById(final Long id) throws NotFoundException {
		return repository.findById(id);
	}
	
	@Override
	public Optional<Customer> findByCPF(final String cpf) throws NotFoundException {
		return repository.findByCpf(cpf);
	}

	
	@Override
	public Page<Customer> findAll(Pageable page, Long id, String nome, LocalDate nascimento, String cpf,
			String sexo) {
		Customer customer = new Customer(null, nome, nascimento, cpf, SexoEnum.fromValue(sexo));
		ExampleMatcher matcher = ExampleMatcher
									.matching()
									.withMatcher("id", 			ExampleMatcher.GenericPropertyMatchers.exact())
									.withMatcher("nome", 		ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
									.withMatcher("nascimento", 	ExampleMatcher.GenericPropertyMatchers.exact())
									.withMatcher("cpf", 		ExampleMatcher.GenericPropertyMatchers.exact())
									.withMatcher("sexo", 		ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase());

		return repository.findAll(Example.of(customer, matcher), page);
	}

}
