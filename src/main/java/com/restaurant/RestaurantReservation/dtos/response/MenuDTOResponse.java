package com.restaurant.RestaurantReservation.dtos.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.restaurant.RestaurantReservation.entities.Prodotto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class MenuDTOResponse {
	
	//variabili
	private Integer idMenu;
	private Map<String, List<Prodotto>> prodottiPerCategoria;
}