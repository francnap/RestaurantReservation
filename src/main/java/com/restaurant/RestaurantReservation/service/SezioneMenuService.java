package com.restaurant.RestaurantReservation.service;

import java.util.List;

import com.restaurant.RestaurantReservation.dtos.request.SezioneMenuDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.SezioneMenuDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.DatiDuplicatiException;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;

public interface SezioneMenuService {

	List<SezioneMenuDTOResponse> getAllSezioni();

	SezioneMenuDTOResponse getSezioneMenuById(Integer idSezioneMenu) throws NotFoundException;

	SezioneMenuDTOResponse createSezioneMenu(SezioneMenuDTORequest insertSezione) throws MissingData, DatiDuplicatiException, NotFoundException;

	SezioneMenuDTOResponse updateSezione(Integer idSezione, SezioneMenuDTORequest sezioneRequest) throws NotFoundException, DatiDuplicatiException, MissingData;

	void deleteSezioneMenu(Integer idSezione, String nomeSezione) throws NotFoundException;
}