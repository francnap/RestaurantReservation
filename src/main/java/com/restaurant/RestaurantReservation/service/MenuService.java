package com.restaurant.RestaurantReservation.service;

import java.util.List;

import com.restaurant.RestaurantReservation.dtos.response.MenuDTOResponse;

public interface MenuService {

	List<MenuDTOResponse> getAllMenu();
}