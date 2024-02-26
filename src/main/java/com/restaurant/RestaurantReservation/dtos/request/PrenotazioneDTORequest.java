package com.restaurant.RestaurantReservation.dtos.request;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrenotazioneDTORequest {
	
	private String note;
	private Integer numeroPosti;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss z")
	private ZonedDateTime orarioArrivo;
	private Integer tavolo;
	private Integer evento;
}