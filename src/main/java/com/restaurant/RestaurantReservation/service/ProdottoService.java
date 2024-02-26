package com.restaurant.RestaurantReservation.service;

import java.util.List;

import com.restaurant.RestaurantReservation.dtos.request.AllergeneDeleteDTORequest;
import com.restaurant.RestaurantReservation.dtos.request.LogicDeleteProdottoDTORequest;
import com.restaurant.RestaurantReservation.dtos.request.ProdottoDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.ProdottoDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.DatiDuplicatiException;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;

public interface ProdottoService {

	List<ProdottoDTOResponse> getAllProdotti();

	ProdottoDTOResponse getProdottoById(Integer idProdotto) throws NotFoundException;

	List<ProdottoDTOResponse> createProdotti(List<ProdottoDTORequest> listaProdotti) throws DatiDuplicatiException, NotFoundException;

	ProdottoDTOResponse updateProdotto(Integer idProdotto, ProdottoDTORequest prodottoRequest) throws NotFoundException, DatiDuplicatiException;

	ProdottoDTOResponse deleteAllergeni(Integer idProdotto, AllergeneDeleteDTORequest allergene) throws NotFoundException;

	List<ProdottoDTOResponse> logicDeleteProdotti(List<LogicDeleteProdottoDTORequest> logicProdotti);

	void deleteProdottoById(Integer idProdotto) throws NotFoundException;
}