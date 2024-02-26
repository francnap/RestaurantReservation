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

import com.restaurant.RestaurantReservation.dtos.request.EventoDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.EventoDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.DatiDuplicatiException;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.service.EventoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/eventi")
public class EventoController {
	
	private static final Logger log = LoggerFactory.getLogger(EventoController.class);
	
	@Autowired
	private EventoService eventoService;
	
	
	
	/**
	 * Get all di tutti gli eventi presenti.
	 * @return lista degli eventi.
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<List<EventoDTOResponse>> getAllEventi(){
		log.info("Get all di tutti gli eventi");
		return new ResponseEntity<>(eventoService.getAllEventi(), HttpStatus.OK);
	}
	
	/**
	 * Get del singolo evento cercato tramite il suo Id.
	 * @param idEvento
	 * @return Evento tramite il suo Id.
	 * @throws NotFoundException
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<EventoDTOResponse> getEventoById(@PathVariable("id") Integer idEvento) throws NotFoundException{
		log.info("Get del singolo evento tramite l'id: " + idEvento);
		return new ResponseEntity<>(eventoService.getEventoById(idEvento), HttpStatus.OK);
	}
	
	/**
	 * Inserimento di uno o più eventi nella base dati.
	 * @param listaEventi
	 * @return Lista degli eventi appena inseriti.
	 * @throws MissingData
	 * @throws DatiDuplicatiException 
	 */
	@PostMapping(value = "/salvaEventi")
	public ResponseEntity<List<EventoDTOResponse>> createEvento(@RequestBody List<EventoDTORequest> listaEventi) throws MissingData{
		log.info("Salvataggio degli eventi");
		List<EventoDTOResponse> insertEventi = eventoService.createEventi(listaEventi);
		return new ResponseEntity<>(insertEventi, HttpStatus.CREATED);
	}
	
	/**
	 * Aggiornamento dell'evento cercato tramite il suo Id.
	 * @param idEvento
	 * @param eventoRequest
	 * @return evento aggiornato.
	 * @throws NotFoundException
	 */
	@PatchMapping(value = "/updateEvento/{id}")
	public ResponseEntity<EventoDTOResponse> updateEvento(@PathVariable("id") Integer idEvento, 
														  @Valid @RequestBody EventoDTORequest eventoRequest) throws NotFoundException{
		log.info("Update dell'evento con id: " + idEvento);
		EventoDTOResponse updatedEvento = eventoService.updateEvento(idEvento, eventoRequest);
		log.info(String.format("Aggiornato evento con id: %s con questi valori: " + eventoRequest.toString(), idEvento));
		return new ResponseEntity<>(updatedEvento, HttpStatus.OK);
	}
	
	/**
	 * Eliminazione fisica di un evento dalla base dati.
	 * @param idEvento
	 * @return true nel caso l'eliminazione abbia successo.
	 * @throws NotFoundException
	 */
	@DeleteMapping(value = "/eliminaEvento/{id}")
	public ResponseEntity<Boolean> deleteEvento(@PathVariable("id") Integer idEvento) throws NotFoundException {
		log.info("Eliminazione di un evento tramite il suo id: " + idEvento);
		eventoService.deleteEventoById(idEvento);
		return ResponseEntity.ok(true);
	}	
}