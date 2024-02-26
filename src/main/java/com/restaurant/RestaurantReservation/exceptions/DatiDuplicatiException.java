package com.restaurant.RestaurantReservation.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class DatiDuplicatiException extends Exception{

	private static final long serialVersionUID = -6301479859240730291L;
	
	//variabili
	private String messaggio;
}