package com.raficruz.crudcliente;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import com.raficruz.crudcliente.model.Customer;
import com.raficruz.crudcliente.model.SexoEnum;
import com.raficruz.crudcliente.service.CustomerService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class SearchByParametersControllerTest {

	@MockBean
	private CustomerService service;

	@Autowired
	MockMvc mockMvc;

	List<Customer> customers;
	PageRequest pageRequest;

	@BeforeEach
	void setup() {
		pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC,"id"));
		customers = new ArrayList<>();
    	customers.add(new Customer(1L, "Miguel Silva Santos", LocalDate.now(), "74221936037", SexoEnum.fromValue("M")));
    	customers.add(new Customer(2L, "Arthur Santos de Oliveira", LocalDate.now(), "19053145079", SexoEnum.fromValue("M")));
    	customers.add(new Customer(3L, "Gael Oliveira e Souza", LocalDate.now(), "05337508067", SexoEnum.fromValue("M")));
    	customers.add(new Customer(4L, "Heitor Souza Rodrigues", LocalDate.now(), "13171604019", SexoEnum.fromValue("M")));
    	customers.add(new Customer(5L, "Helena Rodrigues Ferreira", LocalDate.now(), "41394479077", SexoEnum.fromValue("F")));
    	customers.add(new Customer(6L, "Alice Ferreira Alves", LocalDate.now(), "96842081033", SexoEnum.fromValue("F")));
    	customers.add(new Customer(7L, "Theo Alves Pereira", LocalDate.now(), "09641309048", SexoEnum.fromValue("M")));
    	customers.add(new Customer(8L, "Laura Souza e Silva", LocalDate.now(), "82396328077", SexoEnum.fromValue("F")));
	}

    @Test void whenImSearchingByACustomerSucessfully() throws Exception {
    	Page<Customer> pageResponse = new PageImpl<Customer>(customers, pageRequest, 8);

    	Mockito.when(service.findAll(pageRequest, null, null, null, null, null) )
    		.thenReturn(pageResponse);

    	mockMvc.perform(get("/v1/clientes")
    						.contentType(MediaType.APPLICATION_JSON))
    	.andExpect(jsonPath("$", hasSize(8) ))
    	.andExpect(jsonPath("$[0].id", is(1)))
    	.andExpect(jsonPath("$[7].id", is(8)))
    	.andExpect(status().isOk());
	}

    @Test void whenImSearchingBySilvaFamillySucessfully() throws Exception {
    	List<Customer> silvas = customers.stream()
    							.filter(c->c.getNome().contains("Silva"))
    							.sorted(Comparator.comparingLong(Customer::getId))
    							.collect(Collectors.toList());

    	Page<Customer> pageResponse = new PageImpl<Customer>(silvas, pageRequest, 2);
    	Mockito.when(service.findAll(pageRequest, null, "Silva", null, null, null) )
    		.thenReturn(pageResponse);

    	LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("nome", "Silva");

    	mockMvc.perform(get("/v1/clientes")
    						.contentType(MediaType.APPLICATION_JSON)
    						.params(params))
    	.andExpect(jsonPath("$", hasSize(2) ))
    	.andExpect(jsonPath("$[0].cpf", is("74221936037")))
    	.andExpect(jsonPath("$[1].cpf", is("82396328077")))
    	.andExpect(status().isOk());
	}

}
