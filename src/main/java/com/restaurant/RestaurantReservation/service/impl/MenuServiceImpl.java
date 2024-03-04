package com.restaurant.RestaurantReservation.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.RestaurantReservation.dtos.request.MenuDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.MenuDTOResponse;
import com.restaurant.RestaurantReservation.entities.Menu;
import com.restaurant.RestaurantReservation.exceptions.DatiDuplicatiException;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.repository.MenuRepository;
import com.restaurant.RestaurantReservation.service.MenuService;
import com.restaurant.RestaurantReservation.utils.StaticModelMapper;
import com.restaurant.RestaurantReservation.utils.Utilities;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{
	
	private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Autowired
	MenuRepository menuRepo;

	
	
	@Override
	public List<MenuDTOResponse> getAllMenu() {
		List<Menu> menus = menuRepo.findAll();
		return Utilities.objectListMenuCompleto(menus);
	}
	
	@Override
	public MenuDTOResponse getMenuById(Integer idMenu) throws NotFoundException {
		Optional<Menu> menu = menuRepo.findById(idMenu);
		if(menu.isEmpty()) throw new NotFoundException("Nessun menù trovato con id: " + idMenu);
		return Utilities.objectMenuCompleto(menu.get());
	}

	@Override
	public MenuDTOResponse createMenu(MenuDTORequest menuRequest) throws MissingData, DatiDuplicatiException {
		log.info("Inserimento del menu");
		log.info("Set delle variabili del menu");
		Menu menu = new Menu();
		menu.setDescMenu(menuRequest.getDescMenu());
		
		log.info("salvataggio del menu nel DB");
		try {
			menuRepo.save(menu);
		} catch (ConstraintViolationException e) {
			throw new MissingData("Mancato inserimento di dati necessari.");
		} catch (DataIntegrityViolationException e) {
			throw new DatiDuplicatiException(String.format("Il menù con il nome: '%s' esiste già.", menuRequest.getDescMenu()));
		}
		
		log.info("Creo il menu di response");
		MenuDTOResponse menuResponse = MenuDTOResponse.builder()
				.idMenu(menu.getIdMenu())
				.descMenu(menu.getDescMenu())
			.build();
		
		return menuResponse;
	}

	@Override
	public MenuDTOResponse updateMenu(Integer idMenu, MenuDTORequest menuRequest) throws MissingData, DatiDuplicatiException, NotFoundException {
		log.info("Cerco il menù tramite il suo id");
		Menu menu = menuRepo.findMenuByIdMenu(getMenuById(idMenu).getIdMenu());
		
		log.info("Set delle variabili da modificare");
		menu.setDescMenu(menuRequest.getDescMenu());
		
		log.info("salvataggio del menu nel DB");
		try {
			menuRepo.save(menu);
		} catch (TransactionSystemException e) {
			throw new MissingData("Mancato inserimento di dati necessari.");
		} catch (DataIntegrityViolationException e) {
			throw new DatiDuplicatiException(String.format("Il menù con il nome: '%s' esiste già.", menuRequest.getDescMenu()));
		}
		
		return StaticModelMapper.convert(menu, MenuDTOResponse.class);
	}

	@Override
	@Transactional
	public void deleteMenuById(Integer idMenu) throws NotFoundException {
		menuRepo.deleteById(getMenuById(idMenu).getIdMenu());
	}
}