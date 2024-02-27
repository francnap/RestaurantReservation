package com.restaurant.RestaurantReservation.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.RestaurantReservation.dtos.request.SezioneMenuDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.SezioneMenuDTOResponse;
import com.restaurant.RestaurantReservation.entities.Menu;
import com.restaurant.RestaurantReservation.entities.SezioneMenu;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.repository.MenuRepository;
import com.restaurant.RestaurantReservation.repository.SezioneMenuRepository;
import com.restaurant.RestaurantReservation.service.SezioneMenuService;
import com.restaurant.RestaurantReservation.utils.ModelMapperConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SezioneMenuServiceImpl implements SezioneMenuService{
	
	private static final Logger log = LoggerFactory.getLogger(SezioneMenuServiceImpl.class);
	
	@Autowired
	SezioneMenuRepository sezMenuRepo;
	
	@Autowired
	MenuRepository menuRepo;

	
	
	@Override
	public List<SezioneMenuDTOResponse> getAllSezioni() {
		List<SezioneMenu> sezioni = sezMenuRepo.findAll();
		List<SezioneMenuDTOResponse> list = new ArrayList<>();
		for(SezioneMenu items : sezioni) {
			list.add(ModelMapperConverter.conversioneSezioneMenuToSezioneMenuDTOResponse()
					.map(items, SezioneMenuDTOResponse.class));
		}
		return list;
	}
	
	@Override
	public SezioneMenuDTOResponse getSezioneMenuById(Integer idSezioneMenu) throws NotFoundException {
		Optional<SezioneMenu> sezione = sezMenuRepo.findById(idSezioneMenu);
		if(sezione.isEmpty()) {
			throw new NotFoundException("Nessuna sezione trovata con id: " + idSezioneMenu);
		}
		return ModelMapperConverter.conversioneSezioneMenuToSezioneMenuDTOResponse()
				.map(sezione.get(), SezioneMenuDTOResponse.class);
	}

	@Override
	@Transactional
	public List<SezioneMenuDTOResponse> createSezioniMenu(List<SezioneMenuDTORequest> listaSezioniMenu) {
		log.info("Inserimento di una o più sezioni");
		List<SezioneMenuDTOResponse> list = new ArrayList<>();
		
		for(SezioneMenuDTORequest item : listaSezioniMenu) {
			Menu menu = menuRepo.findMenuByIdMenu(item.getMenu());
			
			log.info("Set delle varibili della singola sezione");
			SezioneMenu sezMenu = new SezioneMenu();
			sezMenu.setDescizione(item.getDescizione());
			sezMenu.setMenu(menu);
			
			log.info("Salvataggio della sezione sul DB");
			sezMenuRepo.save(sezMenu);
			
			log.info("Creo il DTO di response");
			SezioneMenuDTOResponse sezMenuResponse = new SezioneMenuDTOResponse(
					sezMenu.getIdSezioneMenu(), 
					sezMenu.getDescizione(), 
					sezMenu.getMenu().getIdMenu(), 
					sezMenu.getMenu().getDescMenu()
					);
			
			log.info("Creo la lista delle sezioni response");
			list.add(sezMenuResponse);
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}