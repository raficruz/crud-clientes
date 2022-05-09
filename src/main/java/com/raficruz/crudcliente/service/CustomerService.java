package com.raficruz.crudcliente.service;

import java.time.LocalDate;
import java.util.List;

import com.raficruz.crudcliente.handler.NotFoundException;
import com.raficruz.crudcliente.model.Customer;
import com.raficruz.crudcliente.model.CustomerDTO;


public interface CustomerService {

	public void addCustomer(final CustomerDTO customer);
	public void deleteCustomer(final Long clienteId) throws NotFoundException;
	public List<CustomerDTO> findAll(final Long id, final String nome, final LocalDate nascimento, final String cpf, final String sexo);
	public CustomerDTO getCustomerByCPF(final String cpf) throws NotFoundException;
	public void updateCustomer(final Customer customer);

}
