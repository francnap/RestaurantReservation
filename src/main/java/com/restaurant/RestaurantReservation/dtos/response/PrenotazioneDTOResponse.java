package com.restaurant.RestaurantReservation.dtos.response;

import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.restaurant.RestaurantReservation.entities.Ordinazione;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class PrenotazioneDTOResponse {
	
	//variabili
	private Integer idPrenotazione;
	private Integer tavolo;
	private String descTavolo;
	private Integer numeroPosti;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss z")
	private ZonedDateTime orarioPrenotazione;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss z")
	private ZonedDateTime orarioPresuntoArrivo;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss z")
	private ZonedDateTime orarioRealeArrivo;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss z")
	private ZonedDateTime orarioChiusuraPrenotazione;
	private Integer evento;
	private String descEvento;
	private String note;
	@JsonInclude(Include.NON_EMPTY)
	private List<Ordinazione> ordinazioni;
}