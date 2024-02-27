package com.restaurant.RestaurantReservation.service;

import java.util.List;

import com.restaurant.RestaurantReservation.dtos.request.SezioneMenuDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.SezioneMenuDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;

public interface SezioneMenuService {

	List<SezioneMenuDTOResponse> getAllSezioni();

	SezioneMenuDTOResponse getSezioneMenuById(Integer idSezioneMenu) throws NotFoundException;

	List<SezioneMenuDTOResponse> createSezioniMenu(List<SezioneMenuDTORequest> listaSezioniMenu);
}