package com.restaurant.RestaurantReservation.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.restaurant.RestaurantReservation.dtos.response.PaginazioneDTOResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class RispostaPaginata<T> {
	
	//variabili
	private List<T> contentList;
	private Class<T> contenut;
	private PaginazioneDTOResponse paginazione;
	
}