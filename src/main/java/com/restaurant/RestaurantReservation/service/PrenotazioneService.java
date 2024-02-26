package com.restaurant.RestaurantReservation.service;

import java.util.List;

import com.restaurant.RestaurantReservation.dtos.request.PrenotazioneDTODeleteRequest;
import com.restaurant.RestaurantReservation.dtos.request.PrenotazioneDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.PrenotazioneDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;

public interface PrenotazioneService {

	List<PrenotazioneDTOResponse> getAllPrenotazioni();

	PrenotazioneDTOResponse getPrenotazioneById(Integer idPrenotazione) throws NotFoundException;

	PrenotazioneDTOResponse createPrenotazione(PrenotazioneDTORequest prenotazione) throws NotFoundException;

	PrenotazioneDTOResponse updatePrenotazione(Integer idPrenotazione,
			PrenotazioneDTORequest prenotazioneRequest) throws NotFoundException;

	void deletePrenotazioneByNomePrenotante(PrenotazioneDTODeleteRequest deletedPrenotazione) throws NotFoundException;
}