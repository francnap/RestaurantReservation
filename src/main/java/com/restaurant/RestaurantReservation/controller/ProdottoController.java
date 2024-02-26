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

import com.restaurant.RestaurantReservation.dtos.request.AllergeneDeleteDTORequest;
import com.restaurant.RestaurantReservation.dtos.request.LogicDeleteProdottoDTORequest;
import com.restaurant.RestaurantReservation.dtos.request.ProdottoDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.ProdottoDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.DatiDuplicatiException;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.service.ProdottoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController {
	
	private static final Logger log = LoggerFactory.getLogger(ProdottoController.class);
	
	@Autowired
	private ProdottoService prodottoService;
	
	
	
	/**
	 * Get all di tutti i prodotti presenti.
	 * @return lista dei prodotti.
	 */
	@GetMapping(value = "/all")
	public ResponseEntity<List<ProdottoDTOResponse>> getAllProdotti(){
		log.info("Get all di tutti i prodotti");
		return new ResponseEntity<>(prodottoService.getAllProdotti(), HttpStatus.OK);
	}
	
	/**
	 * Get del singolo prodotto cercato tramite il suo Id.
	 * @param idProdotto
	 * @return Prodotto.
	 * @throws NotFoundException
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProdottoDTOResponse> getProdottoById(@PathVariable("id") Integer idProdotto) throws NotFoundException {
		log.info("Get del singolo prodotto tramite l'id: " + idProdotto);
		return new ResponseEntity<>(prodottoService.getProdottoById(idProdotto), HttpStatus.OK);
	}
	
	/**
	 * Inserimento di uno o più prodotti nella base dati.
	 * @param prodottoRequest
	 * @return Lista dei prodotti appena aggiunti.
	 * @throws NotFoundException 
	 * @throws MissingData
	 */
	@PostMapping(value = "/salvaProdotti")
	public ResponseEntity<List<ProdottoDTOResponse>> createProdotto(@RequestBody List<ProdottoDTORequest> listaProdotti) throws DatiDuplicatiException, NotFoundException {
		log.info("Salvataggio dei prodotti");
		List<ProdottoDTOResponse> prodotti = prodottoService.createProdotti(listaProdotti);
		return new ResponseEntity<>(prodotti, HttpStatus.CREATED);
	}
	
	/**
	 * Aggiornamento del prodotto cercato tramite il suo Id.
	 * @param idProdotto
	 * @param prodottoRequest
	 * @return Prodotto aggiornato.
	 * @throws NotFoundException
	 * @throws DatiDuplicatiException 
	 */
	@PatchMapping(value = "/updateProdotto/{id}")
	public ResponseEntity<ProdottoDTOResponse> updateProdotto(@PathVariable("id") Integer idProdotto, 
															  @Valid @RequestBody ProdottoDTORequest prodottoRequest) throws NotFoundException, DatiDuplicatiException {
		log.info("Update del prodotto con id: " + idProdotto);
		ProdottoDTOResponse updateProdotto = prodottoService.updateProdotto(idProdotto, prodottoRequest);
		log.info(String.format("Aggiornato il prodotto con id: %s con questi valori: " + prodottoRequest.toString(), idProdotto));
		return new ResponseEntity<>(updateProdotto, HttpStatus.OK);
	}
	
	/**
	 * Eliminazione di un allergene dal prodotto cercato tramite il suo Id.
	 * @param idProdotto
	 * @param idAllergene
	 * @return Prodotto aggiornato senza allergene eliminato.
	 * @throws NotFoundException
	 */
	@PatchMapping(value = "/deleteAllergeneDalProdotto/{id}")
	public ResponseEntity<ProdottoDTOResponse> deleteAllergene(@PathVariable("id") Integer idProdotto, 
															   @Valid @RequestBody AllergeneDeleteDTORequest allergene) throws NotFoundException {
		log.info("Eliminazione di un allergene da un prodotto");
		ProdottoDTOResponse deletedAllergene = prodottoService.deleteAllergeni(idProdotto, allergene);
		log.info(String.format("Eliminato l'allergene del prodotto con id: %s", idProdotto));
		return new ResponseEntity<>(deletedAllergene, HttpStatus.OK);
	}
	
	/**
	 * Eliminazione logica di uno o più prodotti.
	 * @param logicProdotti
	 * @return Lista prodotti disponibili.
	 * @throws NotFoundException
	 */
	@PatchMapping(value = "/disattivaProdotti")
	public ResponseEntity<List<ProdottoDTOResponse>> logicDeleteProdotto(@RequestBody List<LogicDeleteProdottoDTORequest> logicProdotti) {
		log.info("Eliminazione logica di uno o più prodotti");
		List<ProdottoDTOResponse> prodottiEliminati = prodottoService.logicDeleteProdotti(logicProdotti);
		return new ResponseEntity<>(prodottiEliminati, HttpStatus.OK);
	}
	
	/**
	 * Eliminazione fisica di un prodotto dalla base dati.
	 * @param idProdotto
	 * @return true nel caso l'eliminazione abbia successo.
	 * @throws NotFoundException
	 */
	@DeleteMapping(value = "/eliminaProdotto/{id}")
	public ResponseEntity<Boolean> deleteProdotto(@PathVariable("id") Integer idProdotto) throws NotFoundException {
		log.info("Eliminazione di un prodotto con id: " + idProdotto);
		prodottoService.deleteProdottoById(idProdotto);
		return ResponseEntity.ok(true);
	}
}