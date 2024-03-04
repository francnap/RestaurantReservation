package com.restaurant.RestaurantReservation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.RestaurantReservation.dtos.request.SezioneMenuDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.SezioneMenuDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.DatiDuplicatiException;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.service.SezioneMenuService;

import jakarta.validation.Valid;
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
	 * @throws DatiDuplicatiException 
	 * @throws MissingData 
	 * @throws NotFoundException 
	 */
	@PostMapping(value = "/salva-sezioni")
	public ResponseEntity<SezioneMenuDTOResponse> createSezioneMenu(@RequestBody SezioneMenuDTORequest insertSezione) throws MissingData, DatiDuplicatiException, NotFoundException {
		log.info("Salvataggio di una o più sezioni");
		return new ResponseEntity<>(sezMenuService.createSezioneMenu(insertSezione), HttpStatus.CREATED);
	}
	
	/**
	 * Aggiornamento della sezione menù cercata tramite il suo Id.
	 * @param idSezione
	 * @param sezioneRequest
	 * @return Sezione del menù aggiornata.
	 * @throws NotFoundException
	 * @throws DatiDuplicatiException
	 * @throws MissingData
	 */
	@PatchMapping(value = "/update-sezione/{id}")
	public ResponseEntity<SezioneMenuDTOResponse> updateSezioneMenu(@PathVariable("id") Integer idSezione, 
																	@Valid @RequestBody SezioneMenuDTORequest sezioneRequest) throws NotFoundException, DatiDuplicatiException, MissingData {
		log.info("Update della sezione con id: " + idSezione);
		SezioneMenuDTOResponse updatedSezione = sezMenuService.updateSezione(idSezione, sezioneRequest);
		log.info(String.format("Aggiornata la sezione con id: %s con questi valori: " + sezioneRequest.toString(), idSezione));
		return new ResponseEntity<>(updatedSezione, HttpStatus.OK);
	}
	
	/**
	 * Eliminazione fisica di una sezione del menù tramite il suo Id o tramite la sua descrizione.
	 * @param idSezione
	 * @param nomeSezione
	 * @return true in caso l'eliminazione abbia successo.
	 * @throws NotFoundException
	 */
	@DeleteMapping(value = "/elimina-sezione")
	public ResponseEntity<Boolean> deleteSezione(@RequestParam(required = false) Integer idSezione,
            									 @RequestParam(required = false) String nomeSezione) throws NotFoundException {
		log.info("Eliminazione di una sezione del menù tramite il suo id o la sua descrizione");
		sezMenuService.deleteSezioneMenu(idSezione, nomeSezione);
		return ResponseEntity.ok(true);
	}
}