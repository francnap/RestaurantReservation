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
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.RestaurantReservation.dtos.request.AllergeneDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.AllergeneDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.DatiDuplicatiException;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.service.AllergeneService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/allergeni")
@RequiredArgsConstructor
public class AllergeneController {
	
	private static final Logger log = LoggerFactory.getLogger(AllergeneController.class);
	
	private final AllergeneService allergeneService;
	
	
	
	/**
	 * Get all di tutti gli allergeni presenti nella base dati.
	 * @return lista di allergeni.
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<List<AllergeneDTOResponse>> getAllAllergeni(){
		log.info("Get all di tutti gli allergeni");
		return new ResponseEntity<>(allergeneService.getAllAllergeni(), HttpStatus.OK);
	}
	
	/**
	 * Get allergene by Id. 
	 * @param idAllergene
	 * @return Allergene trovato tramite il suo Id.
	 * @throws NotFoundException
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<AllergeneDTOResponse> getAllergeneById(@PathVariable("id") Integer idAllergene) throws NotFoundException{
		log.info("Get del singolo allergene tramite l'id: " + idAllergene);
		return new ResponseEntity<>(allergeneService.getAllergeneById(idAllergene), HttpStatus.OK);
	}
	
	/**
	 * Insert di uno o più allergeni nella tabella di riferimento presente nel DB.
	 * @param listaAllergeni
	 * @return Lista di allergnei appena inseriti.
	 * @throws MissingData 
	 * @throws DatiDuplicatiException 
	 */
	@PostMapping(value = "/salva-allergeni")
	public ResponseEntity<List<AllergeneDTOResponse>> createAllergene(@RequestBody List<AllergeneDTORequest> listaAllergeni) throws MissingData, DatiDuplicatiException{
		log.info("Salvataggio degli allergeni");
		List<AllergeneDTOResponse> insertAllergeni = allergeneService.createAllergeni(listaAllergeni);
		return new ResponseEntity<>(insertAllergeni, HttpStatus.CREATED);
	}
	
	/**
	 * Update dell'allergene cercato tramite il suo Id.
	 * @param idAllergene
	 * @param allergeneRequest
	 * @return allergene aggiornato.
	 * @throws NotFoundException 
	 */
	@PatchMapping(value = "/{id}")
	public ResponseEntity<AllergeneDTOResponse> updateAllergene(@PathVariable("id") Integer idAllergene, 
																@Valid @RequestBody AllergeneDTORequest allergeneRequest) throws NotFoundException {
		log.info("Update dell'allergene con id: " + idAllergene);
		AllergeneDTOResponse updatedAllergene = allergeneService.updateAllergene(idAllergene, allergeneRequest);
		log.info(String.format("Aggiornato allergene con id: %s con questi valori: " + allergeneRequest.toString(), idAllergene));
		return new ResponseEntity<>(updatedAllergene, HttpStatus.OK);
	}
	
	/**
	 * Eliminazione fisica dell'allergene dalla base dati.
	 * @param idAllergene
	 * @return true in caso di eliminazione corretta.
	 * @throws NotFoundException
	 */
	@DeleteMapping("/elimina-allergene/{id}")
	public ResponseEntity<Boolean> deleteAllergene(@PathVariable("id") Integer idAllergene) throws NotFoundException {
		log.info("Eliminazione di un allergene tramite il suo id: " + idAllergene);
		allergeneService.deleteAllergeneById(idAllergene);
		return ResponseEntity.ok(true);
	}
}