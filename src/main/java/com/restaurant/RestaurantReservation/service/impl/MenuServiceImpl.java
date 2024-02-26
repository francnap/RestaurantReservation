package com.restaurant.RestaurantReservation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.RestaurantReservation.dtos.response.MenuDTOResponse;
import com.restaurant.RestaurantReservation.repository.MenuRepository;
import com.restaurant.RestaurantReservation.service.MenuService;
import com.restaurant.RestaurantReservation.utils.StaticModelMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{
	
	private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Autowired
	MenuRepository menuRepo;

	
	
	@Override
	public List<MenuDTOResponse> getAllMenu() {
		return menuRepo.findAll()
				.stream()
				.map(menu -> StaticModelMapper.convert(menu, MenuDTOResponse.class))
				.collect(Collectors.toList());
	}
}