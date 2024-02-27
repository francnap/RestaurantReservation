package com.restaurant.RestaurantReservation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.RestaurantReservation.dtos.request.LogicDeleteTavoloDTORequest;
import com.restaurant.RestaurantReservation.dtos.request.TavoloDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.TavoloDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.service.TavoloService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tavoli")
public class TavoloController {
	
	private static final Logger log = LoggerFactory.getLogger(TavoloController.class);
	
	@Autowired
	private TavoloService tavoloService;
	
	
	
	/**
	 * Get all di tutti i tavoli presenti nella base dati.
	 * @return lista dei tavoli.
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<List<TavoloDTOResponse>> getAllTavoli(){
		log.info("Get all di tutti i tavoli");
		return new ResponseEntity<>(tavoloService.getAllTavoli(), HttpStatus.OK);
	}
	
	/**
	 * Get del singolo tavolo cercato tramite il suo Id.
	 * @param idTavolo
	 * @return Tavolo tramite il suo Id.
	 * @throws NotFoundException
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<TavoloDTOResponse> getTavoloById(@PathVariable("id") Integer idTavolo) throws NotFoundException{
		log.info("Get del singolo tavolo tramite l'id: " + idTavolo);
		return new ResponseEntity<>(tavoloService.getTavoloById(idTavolo), HttpStatus.OK);
	}
	
	/**
	 * Inserimento di uno o più tavoli nella base dati.
	 * @param listaTavoli
	 * @return Lista dei tavoli appena inseriti.
	 * @throws MissingData
	 */
	@PostMapping(value = "/salva-tavoli")
	public ResponseEntity<List<TavoloDTOResponse>> createTavolo(@RequestBody List<TavoloDTORequest> listaTavoli) throws MissingData{
		log.info("Salvataggio dei tavoli");
		List<TavoloDTOResponse> insertTavoli = tavoloService.createTavoli(listaTavoli);
		return new ResponseEntity<>(insertTavoli, HttpStatus.CREATED);
	}
	
	/**
	 * Update del tavolo cercato tramite il suo Id.
	 * @param idTavolo
	 * @param tavoloRequest
	 * @return tavolo aggiornato.
	 * @throws NotFoundException
	 */
	@PatchMapping(value = "/{id}")
	public ResponseEntity<TavoloDTOResponse> updateTavolo(@PathVariable("id") Integer idTavolo, 
														  @Valid @RequestBody TavoloDTORequest tavoloRequest) throws NotFoundException{
		log.info("Update del tavolo con id: " + idTavolo);
		TavoloDTOResponse updatedTavolo = tavoloService.updateTavolo(idTavolo, tavoloRequest);
		log.info(String.format("Aggiornato tavolo con id: %s con i seguenti valori: " + tavoloRequest.toString(), idTavolo));
		return new ResponseEntity<>(updatedTavolo, HttpStatus.OK);
	}
	
	/**
	 * Eliminazione fisica del tavolo dalla base dati.
	 * @param idTavolo
	 * @return true nel caso l'eliminazione abbia successo.
	 * @throws NotFoundException
	 */
	@DeleteMapping("/elimina-tavolo/{id}")
	public ResponseEntity<Boolean> deleteTavolo(@PathVariable("id") Integer idTavolo) throws NotFoundException {
		log.info("Eliminazione del tavolo con id: " + idTavolo);
		tavoloService.deleteTavoloById(idTavolo);
		return ResponseEntity.ok(true);
	}
	
	/**
	 * Eliminazione logica di uno o più tavoli.
	 * @param logicTavoli
	 * @return lista dei tavoli utilizzabili.
	 * @throws NotFoundException
	 */
	@PatchMapping(value = "/elimina-tavoli")
	public ResponseEntity<List<TavoloDTOResponse>> logicDeleteTavoli(@RequestBody List<LogicDeleteTavoloDTORequest> logicTavoli) throws NotFoundException{
		log.info("Eliminazione logica di uno o più tavoli");
		List<TavoloDTOResponse> tavoliEliminati = tavoloService.logicDeleteTavoli(logicTavoli);
		return new ResponseEntity<>(tavoliEliminati, HttpStatus.OK);
	}
}