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

import com.restaurant.RestaurantReservation.dtos.request.MenuDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.MenuDTOResponse;
import com.restaurant.RestaurantReservation.exceptions.DatiDuplicatiException;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.service.MenuService;

import jakarta.validation.Valid;
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
	
	/**
	 * 
	 * @param idMenu
	 * @return
	 * @throws NotFoundException
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<MenuDTOResponse> getMenuById(@PathVariable("id") Integer idMenu) throws NotFoundException {
		log.info("Get del singolo menu con id: " + idMenu);
		return new ResponseEntity<>(menuService.getMenuById(idMenu), HttpStatus.OK);
	}
	
	/**
	 * Inserimento di un menu nella base dati.
	 * @param menuRequest
	 * @return Menu appena inserito.
	 * @throws MissingData
	 * @throws DatiDuplicatiException 
	 */
	@PostMapping(value = "/salva-menu")
	public ResponseEntity<MenuDTOResponse> createMenu(@RequestBody MenuDTORequest menuRequest) throws MissingData, DatiDuplicatiException {
		log.info("Salavataggio del menu");
		return new ResponseEntity<>(menuService.createMenu(menuRequest), HttpStatus.CREATED);
	}
	
	/**
	 * Aggiornamento del menu cercato tramite il suo Id.
	 * @param idMenu
	 * @param menuRequest
	 * @return Menu aggiornato.
	 * @throws MissingData
	 * @throws DatiDuplicatiException
	 * @throws NotFoundException
	 */
	@PatchMapping(value = "/update-menu/{id}")
	public ResponseEntity<MenuDTOResponse> updateMenu(@PathVariable("id") Integer idMenu, 
													  @Valid @RequestBody MenuDTORequest menuRequest) throws MissingData, DatiDuplicatiException, NotFoundException{
		log.info("Update del menu con id: " + idMenu);
		MenuDTOResponse updatedMenu = menuService.updateMenu(idMenu, menuRequest);
		log.info(String.format("Aggiornato menu con id: %s con questi valori: " + menuRequest.toString(), idMenu));
		return new ResponseEntity<>(updatedMenu, HttpStatus.OK);
	}
	
	/**
	 * Eliminazione fisica di un menu cercato tramite il suo Id. 
	 * @param idMenu
	 * @return true nel caso l'eliminazione abbia successo.
	 * @throws NotFoundException
	 */
	@DeleteMapping(value = "/elimina-menu/{id}")
	public ResponseEntity<Boolean> deleteMenu(@PathVariable("id") Integer idMenu) throws NotFoundException{
		log.info("Eliminazione di un menu con id: " + idMenu);
		menuService.deleteMenuById(idMenu);
		return ResponseEntity.ok(true);
	}
}