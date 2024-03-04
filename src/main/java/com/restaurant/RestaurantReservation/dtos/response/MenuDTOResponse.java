package com.restaurant.RestaurantReservation.dtos.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
	private String descMenu;
	@JsonInclude(Include.NON_EMPTY)
	private List<SezioneMenuDTOResponse> sezioni;
}