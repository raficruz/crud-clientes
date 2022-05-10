package com.raficruz.crudcliente;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.raficruz.crudcliente.model.Customer;
import com.raficruz.crudcliente.model.SexoEnum;
import com.raficruz.crudcliente.model.dto.CustomerDTO;
import com.raficruz.crudcliente.service.CustomerService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class SearchByCPFControllerTest {

	@MockBean
	private CustomerService service;

	@Autowired
	MockMvc mockMvc;

	CustomerDTO unsavedDTO;
	Customer savedCustomer;

	@BeforeEach
	void setup() {
    	unsavedDTO =  new CustomerDTO(null, "Joana da Silva", LocalDate.now(), "89307885052", SexoEnum.FEMININO);
    	savedCustomer = new Customer(1L, "Joana da Silva", LocalDate.now(), "89307885052", SexoEnum.fromValue("F"));
	}

    @Test void whenImSearchingByACustomerSucessfully() throws Exception {
    	Mockito.when(service.findByCPF(savedCustomer.getCpf()))
    		.thenReturn(Optional.of(savedCustomer));

    	mockMvc.perform(get("/v1/clientes/89307885052")
    						.contentType(MediaType.APPLICATION_JSON))
    					.andExpect(status().isOk());
	}

    @Test void whenImSearchingByAnUnexistentCPF() throws Exception {
    	Mockito.when(service.findByCPF(savedCustomer.getCpf()))
    		.thenReturn(Optional.empty());

    	mockMvc.perform(get("/v1/clientes/89307885052")
    						.contentType(MediaType.APPLICATION_JSON))
    					.andExpect(status().isNoContent());
	}
}
