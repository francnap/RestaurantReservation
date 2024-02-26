package com.restaurant.RestaurantReservation.dtos.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.restaurant.RestaurantReservation.entities.Prenotazione;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class TavoloDTOResponse {
	
	//variabili
	private Integer idTavolo;
	private String note;
	private Integer numeroPosti;
	private Boolean utilizzabile;
	@JsonInclude(Include.NON_EMPTY)
	private List<Prenotazione> prenotazioni;
}