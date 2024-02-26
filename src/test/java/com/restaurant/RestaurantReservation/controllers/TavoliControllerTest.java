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
public class TavoliControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private MockMvc mockMvcAll;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	public TavoliControllerTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
	
	
	
	@Test
	void tavoliTest() throws Exception{
		MvcResult insertTavoli = this.mockMvcAll.perform(
				post("/tavoli/salvaTavoli")
				.contentType(MediaType.APPLICATION_JSON)
				.content(FromFile.fromFile("tavoliTest_01.json", "createTavoli")))
				.andExpect(status().isCreated())
				.andReturn();
	}
}