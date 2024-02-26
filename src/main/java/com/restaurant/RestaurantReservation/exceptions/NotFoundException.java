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
public class NotFoundException extends Exception{

	private static final long serialVersionUID = -4476046531022918516L;
	
	//variabili
	private String messaggio;
}
