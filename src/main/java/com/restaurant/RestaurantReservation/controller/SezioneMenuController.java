package com.restaurant.RestaurantReservation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.RestaurantReservation.dtos.request.SezioneMenuDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.SezioneMenuDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.service.SezioneMenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sezioni")
@RequiredArgsConstructor
public class SezioneMenuController {
	
	private static final Logger log = LoggerFactory.getLogger(SezioneMenuController.class);
	
	private final SezioneMenuService sezMenuService;
	
	
	
	/**
	 * Get all di tutte le sezioni del menu presenti.
	 * @return Lista delle sezioni del menu.
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<List<SezioneMenuDTOResponse>> getAllSezioniMenu(){
		log.info("Get all di tutte le sezioni del menu");
		return new ResponseEntity<>(sezMenuService.getAllSezioni(), HttpStatus.OK);
	}
	
	/**
	 * Get della singola sezione del menu.
	 * @param idSezioneMenu
	 * @return Sezione del menu cercata tramite il suo Id.
	 * @throws NotFoundException
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<SezioneMenuDTOResponse> getSezioniMenuById(@PathVariable("id") Integer idSezioneMenu) throws NotFoundException {
		log.info("Get della singola sezione del menu con id: " + idSezioneMenu);
		return new ResponseEntity<>(sezMenuService.getSezioneMenuById(idSezioneMenu), HttpStatus.OK);
	}
	
	/**
	 * Inserimento di una o più sezioni nella base dati.
	 * @param listaSezioniMenu
	 * @return Lista delle sezioni appena inserite.
	 */
	@PostMapping(value = "/salva-sezioni")
	public ResponseEntity<List<SezioneMenuDTOResponse>> createSezioneMenu(@RequestBody List<SezioneMenuDTORequest> listaSezioniMenu) {
		log.info("Salvataggio di una o più sezioni");
		return new ResponseEntity<>(sezMenuService.createSezioniMenu(listaSezioniMenu), HttpStatus.CREATED);
	}
}