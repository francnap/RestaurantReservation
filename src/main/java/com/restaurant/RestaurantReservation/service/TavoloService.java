package com.restaurant.RestaurantReservation.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.restaurant.RestaurantReservation.dtos.request.LogicDeleteTavoloDTORequest;
import com.restaurant.RestaurantReservation.dtos.request.TavoloDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.TavoloDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;

public interface TavoloService {

	List<TavoloDTOResponse> getAllTavoli();

	TavoloDTOResponse getTavoloById(Integer idTavolo) throws NotFoundException;

	List<TavoloDTOResponse> createTavoli(List<TavoloDTORequest> listaTavoli) throws MissingData;

	TavoloDTOResponse updateTavolo(Integer idTavolo, TavoloDTORequest tavoloRequest) throws NotFoundException;

	void deleteTavoloById(Integer idTavolo) throws NotFoundException;

	List<TavoloDTOResponse> logicDeleteTavoli(List<LogicDeleteTavoloDTORequest> logicTavoli);

	Page<TavoloDTOResponse> getTavoliDisponibili(Boolean utilizzabile, Integer page, Integer size);
}