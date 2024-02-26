package com.restaurant.RestaurantReservation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.RestaurantReservation.dtos.response.MenuDTOResponse;
import com.restaurant.RestaurantReservation.service.MenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
	
	private static final Logger log = LoggerFactory.getLogger(MenuController.class);
	
	private final MenuService menuService;
	
	
	
	/**
	 * Get all di tutti i menu con le relative informazioni.
	 * @return Lista dei menu.
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<List<MenuDTOResponse>> getAllMenu(){
		log.info("Get all di tutti i menu");
		return new ResponseEntity<>(menuService.getAllMenu(), HttpStatus.OK);
	}
}