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

import com.restaurant.RestaurantReservation.dtos.request.PrenotazioneDTODeleteRequest;
import com.restaurant.RestaurantReservation.dtos.request.PrenotazioneDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.PrenotazioneDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.service.PrenotazioneService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {
	
	private static final Logger log = LoggerFactory.getLogger(PrenotazioneController.class);
	
	@Autowired
	private PrenotazioneService prenotazioneService;
	
	
	
	/**
	 * Get all di tutte le prenotazioni presenti.
	 * @return lista delle prenotazioni.
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<List<PrenotazioneDTOResponse>> getAllPrenotazioni(){
		log.info("Get all di tutte le prenotazioni");
		return new ResponseEntity<>(prenotazioneService.getAllPrenotazioni(), HttpStatus.OK);
	}
	
	/**
	 * Get della singola prenotazione cercata tramite il suo Id.
	 * @param idPrenotazione
	 * @return Prenotazione.
	 * @throws NotFoundException
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<PrenotazioneDTOResponse> getPrenotazioneById(@PathVariable("id") Integer idPrenotazione) throws NotFoundException {
		log.info("Get della singola prenotazione tramite l'id: " + idPrenotazione);
		return new ResponseEntity<>(prenotazioneService.getPrenotazioneById(idPrenotazione), HttpStatus.OK);
	}
	
	/**
	 * Inserimento di una prenotazione nella base dati.
	 * @param prenotazione
	 * @return Lista delle prenotazioni inserite.
	 * @throws MissingData
	 * @throws NotFoundException 
	 */
	@PostMapping(value = "/salva-prenotazione")
	public ResponseEntity<PrenotazioneDTOResponse> createPrenotazione(@RequestBody PrenotazioneDTORequest prenotazione) throws MissingData, NotFoundException {
		log.info("Salvataggio della prenotazione");
		PrenotazioneDTOResponse insertPrenotazione = prenotazioneService.createPrenotazione(prenotazione);
		return new ResponseEntity<>(insertPrenotazione, HttpStatus.CREATED);
	}
	
	/**
	 * Aggiornamento della prenotazione cercata tramite il suo Id.
	 * @param idPrenotazione
	 * @param prenotazioneRequest
	 * @return Prenotazione aggiornata.
	 * @throws NotFoundException
	 */
	@PatchMapping(value = "/update-prenotazione/{id}")
	public ResponseEntity<PrenotazioneDTOResponse> updatePrenotazione(@PathVariable("id") Integer idPrenotazione, 
																	  @Valid @RequestBody PrenotazioneDTORequest prenotazioneRequest) throws NotFoundException {
		log.info("Update della prenotazione con id: " + idPrenotazione);
		PrenotazioneDTOResponse updatedPrenotazione = prenotazioneService.updatePrenotazione(idPrenotazione, prenotazioneRequest);
		log.info(String.format("Aggionrata la prenotazione con id: %s con questi valori: " + prenotazioneRequest.toString(), idPrenotazione));
		return new ResponseEntity<>(updatedPrenotazione, HttpStatus.OK);
	}
	
	/**
	 * Eliminazione fisica della prenotazione tramite il nome del prenotante.
	 * @param deletedPrenotazione
	 * @return true nel caso l'eliminazione abbia successo.
	 * @throws NotFoundException
	 */
	@DeleteMapping("/elimina-prenotazione")
	public ResponseEntity<Boolean> deletePrenotazione(@RequestBody PrenotazioneDTODeleteRequest deletedPrenotazione) throws NotFoundException {
		log.info("Eliminazione di una prenotazione a nome di: " + deletedPrenotazione.getNote());
		prenotazioneService.deletePrenotazioneByNomePrenotante(deletedPrenotazione);
		return ResponseEntity.ok(true);
	}
}