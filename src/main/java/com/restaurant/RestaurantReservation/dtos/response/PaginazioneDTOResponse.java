package com.restaurant.RestaurantReservation.dtos.response;

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
public class PaginazioneDTOResponse {
	
	//varibili
	private Integer pageNumber;
	private Long totalElements;
	private Integer totalPages;
	private Integer numberOfElements;
}