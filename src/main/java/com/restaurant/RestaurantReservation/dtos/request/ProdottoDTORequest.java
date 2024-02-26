package com.restaurant.RestaurantReservation.dtos.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class ProdottoDTORequest {
	
	//variabili
	@NotNull
	private String nomeProdotto;
	private String categoria;
	private Double prezzo;
	private List<Integer> idAllergeni;
}