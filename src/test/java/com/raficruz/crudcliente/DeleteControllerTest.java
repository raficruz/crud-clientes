package com.raficruz.crudcliente;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
class DeleteControllerTest {

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

    @Test void whenImDeletingACustomerSucessfully() throws Exception {
    	Mockito.when(service.findById(savedCustomer.getId()))
    		.thenReturn(Optional.of(savedCustomer));

    	mockMvc.perform(delete("/v1/clientes/1")
    						.content(new ObjectMapper().writeValueAsString(savedCustomer))
    						.contentType(MediaType.APPLICATION_JSON))
    					.andExpect(status().isOk());
	}

    @Test void whenImDeletingAnUnexistentCustomer() throws Exception {
    	Mockito.when(service.findById(savedCustomer.getId()))
    		.thenReturn(Optional.empty());

    	mockMvc.perform(delete("/v1/clientes/1")
    						.content(new ObjectMapper().writeValueAsString(savedCustomer))
    						.contentType(MediaType.APPLICATION_JSON))
    					.andExpect(status().isConflict());
	}

    
    /*
	@Test void whenImUpdatingAUnexistentCustomer() throws Exception {
		Mockito.when( service.findById(1L) ).thenReturn( Optional.empty() );
		Customer customer = new Customer();
		customer.setId(1L);

    	mockMvc.perform(put("/v1/clientes/1")
    					.content(new ObjectMapper().writeValueAsString(customer))
    					.contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isConflict());
	}

	@Test void whenImUpdatingACustomerWithCPFDuplicated() throws Exception {
		Mockito.when( service.findById(1L) ).thenReturn( Optional.of(savedCustomer) );

		Customer updatedCustomer =  new Customer();
		BeanUtils.copyProperties(savedCustomer, updatedCustomer);
		updatedCustomer.setCpf("10826180043");
		
    	mockMvc.perform(put("/v1/clientes/1")
    					.content(new ObjectMapper().writeValueAsString(updatedCustomer))
    					.contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isConflict());
	}

	@Test void whenImUpdatingACustomerWithInvalidName() throws Exception {
		savedCustomer.setNome("ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJA");
    	mockMvc.perform(put("/v1/clientes/1")
    					.content(new ObjectMapper().writeValueAsString(savedCustomer))
    					.contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isConflict());
	}

	@Test void whenImUpdatingACustomerWithCPFInvalid() throws Exception {
		savedCustomer.setCpf("12345678901");
    	mockMvc.perform(put("/v1/clientes/1")
    					.content(new ObjectMapper().writeValueAsString(savedCustomer))
    					.contentType(MediaType.APPLICATION_JSON))
    			.andExpect(status().isConflict());
	}
	*/
}
