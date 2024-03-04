package com.restaurant.RestaurantReservation.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.RestaurantReservation.dtos.request.SezioneMenuDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.ProdottoDTOResponse;
import com.restaurant.RestaurantReservation.dtos.response.SezioneMenuDTOResponse;
import com.restaurant.RestaurantReservation.entities.Menu;
import com.restaurant.RestaurantReservation.entities.Prodotto;
import com.restaurant.RestaurantReservation.entities.SezioneMenu;
import com.restaurant.RestaurantReservation.entities.SezioneMenuProdotti;
import com.restaurant.RestaurantReservation.exceptions.DatiDuplicatiException;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.repository.MenuRepository;
import com.restaurant.RestaurantReservation.repository.ProdottoRepository;
import com.restaurant.RestaurantReservation.repository.SezioneMenuProdottiRepository;
import com.restaurant.RestaurantReservation.repository.SezioneMenuRepository;
import com.restaurant.RestaurantReservation.service.MenuService;
import com.restaurant.RestaurantReservation.service.SezioneMenuService;
import com.restaurant.RestaurantReservation.utils.StaticModelMapper;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SezioneMenuServiceImpl implements SezioneMenuService{
	
	private static final Logger log = LoggerFactory.getLogger(SezioneMenuServiceImpl.class);
	
	@Autowired
	SezioneMenuRepository sezMenuRepo;
	
	@Autowired
	MenuRepository menuRepo;
	
	@Autowired
	ProdottoRepository prodRepo;
	
	@Autowired
	SezioneMenuProdottiRepository sezMenuProdRepo;
	
	@Autowired
	private MenuService menuService;

	
	
	@Override
	public List<SezioneMenuDTOResponse> getAllSezioni() {
		List<SezioneMenu> sezioni = sezMenuRepo.findAll();
		List<SezioneMenuDTOResponse> list = new ArrayList<>();
		for(SezioneMenu items : sezioni) {
			SezioneMenuDTOResponse sezione = new SezioneMenuDTOResponse(
					items.getIdSezioneMenu(),
					items.getDescrizione(), 
					items.getMenu().getIdMenu(), 
					items.getMenu().getDescMenu(), 
					StaticModelMapper.convertList(
							items.getSezioni()
							.stream()
							.map(SezioneMenuProdotti::getProdotto)
							.toList(), ProdottoDTOResponse.class)
					);
			list.add(sezione);
		}
		return list;
	}
	
	@Override
	public SezioneMenuDTOResponse getSezioneMenuById(Integer idSezioneMenu) throws NotFoundException {
		Optional<SezioneMenu> sezione = sezMenuRepo.findById(idSezioneMenu);
		if(sezione.isEmpty()) {
			throw new NotFoundException("Nessuna sezione trovata con id: " + idSezioneMenu);
		}
		SezioneMenuDTOResponse sezioneResponse = new SezioneMenuDTOResponse(
				sezione.get().getIdSezioneMenu(),
				sezione.get().getDescrizione(), 
				sezione.get().getMenu().getIdMenu(), 
				sezione.get().getMenu().getDescMenu(), 
				StaticModelMapper.convertList(
						sezione.get().getSezioni()
						.stream()
						.map(SezioneMenuProdotti::getProdotto)
						.toList(), ProdottoDTOResponse.class)
				);
		return sezioneResponse;
	}

	@Override
	public SezioneMenuDTOResponse createSezioneMenu(SezioneMenuDTORequest insertSezione) throws MissingData, DatiDuplicatiException, NotFoundException {
		log.info("Inserimento di una sezione del menù");
		Menu menu = menuRepo.findMenuByIdMenu(menuService.getMenuById(insertSezione.getMenu()).getIdMenu());
		
		log.info("Set delle variabili della sezione menu");
		SezioneMenu sezMenu = new SezioneMenu();
		sezMenu.setDescrizione(insertSezione.getDescrizione());
		sezMenu.setMenu(menu);
		
		log.info("Savataggio della sezione sul DB");
		try {
			sezMenuRepo.save(sezMenu);
		} catch (ConstraintViolationException e) {
			throw new MissingData("Mancato inserimento di dati necessari.");
		} catch (DataIntegrityViolationException e) {
			throw new DatiDuplicatiException(String.format("La sezione con il nome: '%s' esiste già.", insertSezione.getDescrizione()));
		}
		
		List<SezioneMenuProdotti> listaProdotti = new ArrayList<>();
		for(Integer id : insertSezione.getIdProdotti()) {
			Prodotto prodotto = prodRepo.findProdottoByIdProdotto(id);
			listaProdotti.add(SezioneMenuProdotti
					.builder().prodotto(
							Prodotto.builder()
									.idProdotto(prodotto.getIdProdotto())
									.nomeProdotto(prodotto.getNomeProdotto())
									.categoria(prodotto.getCategoria())
									.prezzo(prodotto.getPrezzo())
									.disponibile(prodotto.getDisponibile())
									.prodottiAllergeni(prodotto.getProdottiAllergeni())
									.build())
							  .sezione(
							SezioneMenu.builder()
									   .idSezioneMenu(sezMenu.getIdSezioneMenu())
									   .build())
					.build());
		}
		
		log.info("Salvataggio dei prodotti nella sezione menù");
		sezMenuProdRepo.saveAll(listaProdotti);
		
		log.info("Creo la response della sezione menu");
		SezioneMenuDTOResponse sezMenuResponse = new SezioneMenuDTOResponse(
				sezMenu.getIdSezioneMenu(), 
				sezMenu.getDescrizione(), 
				sezMenu.getMenu().getIdMenu(), 
				sezMenu.getMenu().getDescMenu(), 
				StaticModelMapper.convertList(
						listaProdotti
						.stream()
						.map(SezioneMenuProdotti::getProdotto)
						.toList(), ProdottoDTOResponse.class)
				);
		
		return sezMenuResponse;
	}

	@Override
	public SezioneMenuDTOResponse updateSezione(Integer idSezione, SezioneMenuDTORequest sezioneRequest) throws NotFoundException, DatiDuplicatiException, MissingData {
		log.info("Aggionamento di una sezione del menù");
		SezioneMenu sezioneMenu = sezMenuRepo.findSezioneMenuByIdSezioneMenu(idSezione);
		if(sezioneMenu == null) throw new NotFoundException("Nessuna sezione menù trovata con id: " + idSezione);
		
		log.info("Cerco il menù tramite il suo id");
		Menu menu = menuRepo.findMenuByIdMenu(menuService.getMenuById(sezioneRequest.getMenu()).getIdMenu());
		
		log.info("Set delle varibili da modificare");
		sezioneMenu.setDescrizione(sezioneRequest.getDescrizione());
		sezioneMenu.setMenu(menu);
		
		log.info("Salvataggio della sezione menu nel DB");
		try {
			sezMenuRepo.save(sezioneMenu);
		} catch (TransactionSystemException e) {
			throw new MissingData("Mancato inserimento di dati necessari.");
		} catch (DataIntegrityViolationException e) {
			throw new DatiDuplicatiException(String.format("La sezione con il nome: '%s' esiste già.", sezioneRequest.getDescrizione()));
		}
		
		
		log.info("Creo la response della sezione menu");
		SezioneMenuDTOResponse sezMenuResponse = new SezioneMenuDTOResponse(
				sezioneMenu.getIdSezioneMenu(), 
				sezioneMenu.getDescrizione(), 
				sezioneMenu.getMenu().getIdMenu(), 
				sezioneMenu.getMenu().getDescMenu(), 
				StaticModelMapper.convertList(
						sezioneMenu.getSezioni()
						.stream()
						.map(SezioneMenuProdotti::getProdotto)
						.toList(), ProdottoDTOResponse.class)
				);
		
		return sezMenuResponse;
	}

	@Override
	@Transactional
	public void deleteSezioneMenu(Integer idSezione, String nomeSezione) throws NotFoundException {
		if(idSezione != null) {
			sezMenuRepo.deleteById(getSezioneMenuById(idSezione).getIdSezioneMenu());
		}
		if(nomeSezione != null) {
			SezioneMenu sezioneMenu = sezMenuRepo.findSezioneMenuByDescrizione(nomeSezione);
			if(sezioneMenu == null) {
				throw new NotFoundException(String.format("Nessuna sezione con nome '%s' trovata.", nomeSezione));
			}
			sezMenuRepo.deleteById(sezioneMenu.getIdSezioneMenu());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	//metodi di utilità
	public SezioneMenuDTOResponse getSezioneById(Integer idSezioneMenu) {
		Optional<SezioneMenu> sezione = sezMenuRepo.findById(idSezioneMenu);
		SezioneMenuDTOResponse sezioneResponse = new SezioneMenuDTOResponse(
				sezione.get().getIdSezioneMenu(),
				sezione.get().getDescrizione(), 
				sezione.get().getMenu().getIdMenu(), 
				sezione.get().getMenu().getDescMenu(), 
				StaticModelMapper.convertList(
						sezione.get().getSezioni()
						.stream()
						.map(SezioneMenuProdotti::getProdotto)
						.toList(), ProdottoDTOResponse.class)
				);
		return sezioneResponse;
	}
}