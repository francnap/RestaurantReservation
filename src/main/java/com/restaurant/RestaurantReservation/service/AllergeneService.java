package com.restaurant.RestaurantReservation.service;

import java.util.List;

import com.restaurant.RestaurantReservation.dtos.request.AllergeneDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.AllergeneDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.DatiDuplicatiException;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;

public interface AllergeneService {

	List<AllergeneDTOResponse> createAllergeni(List<AllergeneDTORequest> listaAllergeni) throws MissingData, DatiDuplicatiException;

	AllergeneDTOResponse getAllergeneById(Integer idAllergene) throws NotFoundException;

	List<AllergeneDTOResponse> getAllAllergeni();

	void deleteAllergeneById(Integer idAllergene) throws NotFoundException;

	AllergeneDTOResponse updateAllergene(Integer idAllergene, AllergeneDTORequest allergeneRequest) throws NotFoundException;
}