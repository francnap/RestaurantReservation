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
public class AllergeneControllerTest {
	
	private MockMvc mockMvc;

	@Autowired
	public AllergeneControllerTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
	
	
	@Test
	void allergeniTest() throws Exception{
		//		MvcResult insertAllergeni = 
		mockMvc.perform(
				post("/allergeni/salvaAllergeni")
				.contentType(MediaType.APPLICATION_JSON)
				.content(FromFile.fromFile("allergeniTest_01.json", "createAllergeni")))
		.andExpect(status().isCreated())
		.andReturn();
	}
}