package com.restaurant.RestaurantReservation.dtos.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class ProdottoDTOResponse {
	
	//variabili
	private Integer idProdotto;
	private String nomeProdotto;
	private String categoria;
	private Boolean disponibile;
	private Double prezzo;
	@JsonInclude(Include.NON_EMPTY)
	private List<AllergeneDTOResponse> allergeni;
}