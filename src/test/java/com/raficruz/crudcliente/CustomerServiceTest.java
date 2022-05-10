package com.raficruz.crudcliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.raficruz.crudcliente.model.Customer;
import com.raficruz.crudcliente.model.SexoEnum;
import com.raficruz.crudcliente.repository.CustomerRepository;
import com.raficruz.crudcliente.service.CustomerService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class CustomerServiceTest {

	@Autowired
	private CustomerService service;
	
	@MockBean
	private CustomerRepository repository;

	private Customer unsavedModel;
	private Customer savedModel;
	private ExampleMatcher exampleMatcher;

	@BeforeEach
	void setup() {
    	unsavedModel = new Customer(null, "Joao da Silva", LocalDate.now(), "89307885052", SexoEnum.fromValue("M"));
    	savedModel = new Customer(1L, "Joao da Silva", LocalDate.now(), "89307885052", SexoEnum.fromValue("M"));
		exampleMatcher = ExampleMatcher
				.matching()
				.withMatcher("id", 			ExampleMatcher.GenericPropertyMatchers.exact())
				.withMatcher("nome", 		ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("nascimento", 	ExampleMatcher.GenericPropertyMatchers.exact())
				.withMatcher("cpf", 		ExampleMatcher.GenericPropertyMatchers.exact())
				.withMatcher("sexo", 		ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase());
	}
	
    @Test void whenImSavingACustomerSucessfully() throws Exception {
    	when(repository.save(unsavedModel)).thenReturn(savedModel);
    	
    	Customer saved = service.saveCustomer(unsavedModel);
    	assertEquals(savedModel, saved);
	}

    @Test void whenImSearchingACustomerByIdSucessfully() throws Exception {
    	when(repository.findById(savedModel.getId())).thenReturn(Optional.of(savedModel));
        
    	assertEquals(Optional.of(savedModel), service.findById(1L));
	}

    @Test void whenImSearchingACustomerByCPFSucessfully() throws Exception {
    	when(repository.findByCpf(savedModel.getCpf())).thenReturn(Optional.of(savedModel));
        
    	assertEquals(Optional.of(savedModel), service.findByCPF("89307885052"));
	}

    @Test void whenImSearchingAllCustomerSucessfully() throws Exception {
    	List<Customer> customers = new ArrayList<>();
    	customers.add(new Customer(1L, "Miguel Silva Santos", LocalDate.now(), "74221936037", SexoEnum.fromValue("M")));
    	customers.add(new Customer(2L, "Arthur Santos de Oliveira", LocalDate.now(), "19053145079", SexoEnum.fromValue("M")));
    	customers.add(new Customer(3L, "Gael Oliveira e Souza", LocalDate.now(), "05337508067", SexoEnum.fromValue("M")));

    	Customer customerFilter = new Customer(null, null, null, null, null);
    	PageRequest pageConfig = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC,"id"));
    	Page<Customer> pageResponse = new PageImpl<Customer>(customers, pageConfig, 1);


    	when(repository.findAll(Example.of(customerFilter, exampleMatcher), pageConfig))
    		.thenReturn(pageResponse);
    	Page<Customer> response = service.findAll(pageConfig, null, null, null, null, null);
    	assertEquals( 3, response.getContent().size());
	}

    @Test void whenImSearchingBySilvasCustomerSucessfully() throws Exception {
    	Customer customerFilter = new Customer(null, "Silva", null, null, null);
    	PageRequest pageConfig = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC,"id"));
    	Page<Customer> pageResponse = new PageImpl<Customer>(Stream.of(savedModel).collect(Collectors.toList()), pageConfig, 1);


    	when(repository.findAll(Example.of(customerFilter, exampleMatcher), pageConfig))
		.thenReturn(pageResponse);

    	Page<Customer> response = service.findAll( pageConfig, null, "Silva", null, null, null);
    	assertEquals( 1, response.getContent().size());
	}
}
