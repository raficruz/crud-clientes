package com.raficruz.crudcliente.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.raficruz.crudcliente.handler.NotFoundException;
import com.raficruz.crudcliente.model.Customer;
import com.raficruz.crudcliente.model.CustomerDTO;
import com.raficruz.crudcliente.model.SexoEnum;
import com.raficruz.crudcliente.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository repository;
	
	@Override
	public void addCustomer(CustomerDTO customer) {
		repository.save(customer.toEntity());
	}

	@Override
	public void deleteCustomer(Long clienteId) throws NotFoundException {
		Optional<Customer> customer = repository.findById(clienteId);
		if(customer.isPresent()) {
			repository.delete(customer.get());
			return;
		}
		throw new NotFoundException("Customer not found!");
	}

	@Override
	public List<CustomerDTO> findAll(final Long id, final String nome, final LocalDate nascimento, final String cpf, final String sexo) {
		Customer customer = new Customer(null, nome, nascimento, cpf, SexoEnum.fromValue(sexo));
		ExampleMatcher matcher = ExampleMatcher
									.matching()
									.withMatcher("id", 	ExampleMatcher.GenericPropertyMatchers.exact())
									.withMatcher("nome", 		ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
									.withMatcher("nascimento", 	ExampleMatcher.GenericPropertyMatchers.exact())
									.withMatcher("cpf", 		ExampleMatcher.GenericPropertyMatchers.exact())
									.withMatcher("sexo", 		ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase());

		return repository.findAll(Example.of(customer, matcher))
				.stream()
				.map( Customer::toDTO )
				.collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerByCPF(final String cpf) throws NotFoundException {
		Optional<Customer> customer = repository.findByCpf(cpf);
		if(customer.isPresent()) {
			return customer.get().toDTO();
		}
		throw new NotFoundException("Customer not found!");
	}

	@Override
	public void updateCustomer(Customer customer) {
		repository.save(customer);
	}

}
