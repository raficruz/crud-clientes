package com.raficruz.crudcliente;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raficruz.crudcliente.model.Customer;
import com.raficruz.crudcliente.model.SexoEnum;
import com.raficruz.crudcliente.model.dto.CustomerDTO;
import com.raficruz.crudcliente.service.CustomerService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class SaveControllerTest {

	@MockBean
	private CustomerService service;

	@Autowired
	MockMvc mockMvc;

	CustomerDTO unsavedDTO;

	private Customer unsavedModel;
	private Customer savedModel;
	
	@BeforeEach
	void setup() {
		unsavedModel = new Customer();
		savedModel = new Customer();

		unsavedDTO =  new CustomerDTO();
    	unsavedDTO.setNome("Joao da Silva");
    	unsavedDTO.setCpf("89307885052");
    	unsavedDTO.setNascimento(LocalDate.now());
    	unsavedDTO.setSexo(SexoEnum.fromValue("M"));

    	unsavedModel = new Customer(null, "Joao da Silva", LocalDate.now(), "89307885052", SexoEnum.fromValue("M"));
    	savedModel = new Customer(1L, "Joao da Silva", LocalDate.now(), "89307885052", SexoEnum.fromValue("M"));
	}

    @Test void whenImSavingACustomerSucessfully() throws Exception {
    	Mockito.when(service.findByCPF(unsavedDTO.getCpf())).thenReturn(Optional.empty());
    	Mockito.when(service.saveCustomer(unsavedModel)).thenReturn(savedModel);

    	mockMvc.perform(post("/v1/clientes")
    					.content(new ObjectMapper().writeValueAsString(unsavedModel))
    					.contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isCreated());
	}

	@Test void whenImSavingACustomerDuplicated() throws Exception {
    	Mockito.when(service.findByCPF(unsavedDTO.getCpf())).thenReturn(Optional.of(savedModel));

    	mockMvc.perform(post("/v1/clientes")
    					.content(new ObjectMapper().writeValueAsString(unsavedModel))
    					.contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isConflict());
	}

	@Test void whenImSavingACustomerWithInvalidName() throws Exception {
    	unsavedDTO.setNome("ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJA");
    	
    	Mockito.when(service.findByCPF(unsavedDTO.getCpf())).thenReturn(Optional.of(savedModel));

    	mockMvc.perform(post("/v1/clientes")
    					.content(new ObjectMapper().writeValueAsString(unsavedModel))
    					.contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isConflict());
	}

}
