package com.restaurant.RestaurantReservation.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogicDeleteProdottoDTORequest {
	
	//variabili
	private Integer idProdotto;
	private String nomeProdotto;
}