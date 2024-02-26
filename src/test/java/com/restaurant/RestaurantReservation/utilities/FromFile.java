package com.restaurant.RestaurantReservation.utilities;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public class FromFile {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@SneakyThrows
	public static byte[] fromFile(String path, String name) {
		JsonNode jsonNode = objectMapper.readTree(new ClassPathResource(path)
				.getInputStream()
				.readAllBytes());
		
		JsonNode jsonNodeElement = jsonNode.get(name);
		String jsonString = jsonNodeElement.toString();
		return jsonString.getBytes();
	}
}