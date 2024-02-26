package com.restaurant.RestaurantReservation.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.RestaurantReservation.utilities.FromFile;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EventoControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private MockMvc mockMvcAll;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	public EventoControllerTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
	
	
	
	@Test
	void eventoTest() throws Exception {
		MvcResult insertEventi = this.mockMvcAll.perform(
				post("/eventi/salvaEventi")
				.contentType(MediaType.APPLICATION_JSON)
				.content(FromFile.fromFile("eventiTest_01.json", "createEventi")))
				.andExpect(status().isCreated())
				.andReturn();
	}
}