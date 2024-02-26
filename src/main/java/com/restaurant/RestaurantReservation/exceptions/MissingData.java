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
public class MissingData extends Exception{

	private static final long serialVersionUID = -8129394826965147879L;
	
	//variabili
	private String messaggio;
}