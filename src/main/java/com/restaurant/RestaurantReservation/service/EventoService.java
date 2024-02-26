package com.restaurant.RestaurantReservation.service;

import java.util.List;

import com.restaurant.RestaurantReservation.dtos.request.EventoDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.EventoDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;

public interface EventoService {

	List<EventoDTOResponse> getAllEventi();

	EventoDTOResponse getEventoById(Integer idEvento) throws NotFoundException;

	List<EventoDTOResponse> createEventi(List<EventoDTORequest> listaEventi) throws MissingData;

	EventoDTOResponse updateEvento(Integer idEvento, EventoDTORequest eventoRequest) throws NotFoundException;

	void deleteEventoById(Integer idEvento) throws NotFoundException;
}