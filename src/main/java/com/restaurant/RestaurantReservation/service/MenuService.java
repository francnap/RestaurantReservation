package com.restaurant.RestaurantReservation.service;

import java.util.List;

import com.restaurant.RestaurantReservation.dtos.request.MenuDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.MenuDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.DatiDuplicatiException;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;

public interface MenuService {

	List<MenuDTOResponse> getAllMenu();

	MenuDTOResponse getMenuById(Integer idMenu) throws NotFoundException;

	MenuDTOResponse createMenu(MenuDTORequest menuRequest) throws MissingData, DatiDuplicatiException;

	MenuDTOResponse updateMenu(Integer idMenu, MenuDTORequest menuRequest) throws MissingData, DatiDuplicatiException, NotFoundException;

	void deleteMenuById(Integer idMenu) throws NotFoundException;
}