package com.restaurant.RestaurantReservation.dtos.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SezioneMenuDTORequest {
	
	//variabili
	private String descrizione;
	private Integer menu;
	private List<Integer> idProdotti;
}