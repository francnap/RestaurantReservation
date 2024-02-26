package com.restaurant.RestaurantReservation.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.RestaurantReservation.dtos.request.AllergeneDeleteDTORequest;
import com.restaurant.RestaurantReservation.dtos.request.LogicDeleteProdottoDTORequest;
import com.restaurant.RestaurantReservation.dtos.request.ProdottoDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.AllergeneDTOResponse;
import com.restaurant.RestaurantReservation.dtos.response.ProdottoDTOResponse;
import com.restaurant.RestaurantReservation.entities.Allergene;
import com.restaurant.RestaurantReservation.entities.ProdottiAllergeni;
import com.restaurant.RestaurantReservation.entities.Prodotto;
import com.restaurant.RestaurantReservation.exceptions.DatiDuplicatiException;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.repository.AllergeneRepository;
import com.restaurant.RestaurantReservation.repository.ProdottiAllergeniRepository;
import com.restaurant.RestaurantReservation.repository.ProdottoRepository;
import com.restaurant.RestaurantReservation.service.AllergeneService;
import com.restaurant.RestaurantReservation.service.ProdottoService;
import com.restaurant.RestaurantReservation.utils.StaticModelMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdottoServiceImpl implements ProdottoService{
	
	private static final Logger log = LoggerFactory.getLogger(ProdottoServiceImpl.class);
	
	@Autowired
	ProdottoRepository prodottoRepo;
	
	@Autowired
	AllergeneRepository allergeneRepo;
	
	@Autowired
	ProdottiAllergeniRepository prodAllergeniRepo;
	
	@Autowired
	private AllergeneService allergeneService;

	
	
	@Override
	public List<ProdottoDTOResponse> getAllProdotti() {
		return prodottoRepo.findAll()
				.stream()
				.map(prodotto -> StaticModelMapper.convert(prodotto, ProdottoDTOResponse.class))
				.collect(Collectors.toList());
	}
	
	@Override
	public ProdottoDTOResponse getProdottoById(Integer idProdotto) throws NotFoundException {
		Optional<Prodotto> prodotto = prodottoRepo.findById(idProdotto);
		if(prodotto.isEmpty()) {
			throw new NotFoundException("Nessun prodotto trovato con id: " + idProdotto);
		}
		return StaticModelMapper.convert(prodotto.get(), ProdottoDTOResponse.class);
	}

	@Override
	public List<ProdottoDTOResponse> createProdotti(List<ProdottoDTORequest> listaProdotti) throws DatiDuplicatiException, NotFoundException {
		log.info("Inserimento dei prodotti");
		List<ProdottoDTOResponse> list = new ArrayList<>();
		
		for(ProdottoDTORequest prodottoRequest : listaProdotti) {
			
			log.info("Set delle variabili del singolo prodotto");
			Prodotto prodotto = new Prodotto();
			prodotto.setNomeProdotto(prodottoRequest.getNomeProdotto());
			prodotto.setCategoria(prodottoRequest.getCategoria());
			prodotto.setPrezzo(prodottoRequest.getPrezzo());
			
			log.info("Salvataggio del prodotto sul DB");
			try {
				prodottoRepo.save(prodotto);
			} catch (DataIntegrityViolationException e) {
				throw new DatiDuplicatiException(String.format("Errore: Dati duplicati, '%s' esiste già.", prodotto.getNomeProdotto()));
			}
			
			List<ProdottiAllergeni> listaAllergeni = new ArrayList<>();
			for(Integer loopItem : prodottoRequest.getIdAllergeni()) {
				listaAllergeni.add(ProdottiAllergeni
						.builder().allergene(
								Allergene.builder()
										 .idAllergene(allergeneService.getAllergeneById(loopItem).getIdAllergene())
										 .nomeAllergene(allergeneService.getAllergeneById(loopItem).getNomeAllergene())
										 .build())
								  .prodotto(
								Prodotto.builder()
										.idProdotto(prodotto.getIdProdotto())
										.build())
						.build());
			}
			
			log.info("Salvataggio lista prodotti e allergeni");
			prodAllergeniRepo.saveAll(listaAllergeni);
			
			
			log.info("Creo il DTO prodotto di response");
			ProdottoDTOResponse prodottoResponse = new ProdottoDTOResponse(
					prodotto.getIdProdotto(), 
					prodotto.getNomeProdotto(), 
					prodotto.getCategoria(), 
					prodotto.getDisponibile(),
					prodotto.getPrezzo(), 
					StaticModelMapper.convertList(
							listaAllergeni
							.stream()
							.map(ProdottiAllergeni::getAllergene)
							.toList(), AllergeneDTOResponse.class)
					);
			
			log.info("Creo la lista dei prodotti response");
			list.add(prodottoResponse);
		}
		return list;
	}

	@Override
	public ProdottoDTOResponse updateProdotto(Integer idProdotto, ProdottoDTORequest prodottoRequest) throws NotFoundException, DatiDuplicatiException {
		Prodotto prodotto = prodottoRepo.findProdottoByIdProdotto(idProdotto);
		List<ProdottiAllergeni> prodottiAllergeni = prodotto.getProdottiAllergeni();
		List<Integer> collect = prodottiAllergeni.stream().map(t -> t.getAllergene().getIdAllergene()).collect(Collectors.toList());
		
		prodotto.setNomeProdotto(prodottoRequest.getNomeProdotto().isBlank() ? prodotto.getNomeProdotto() : prodottoRequest.getNomeProdotto());
		prodotto.setCategoria(prodottoRequest.getCategoria());
		prodotto.setPrezzo(prodottoRequest.getPrezzo() == null ? prodotto.getPrezzo() : prodottoRequest.getPrezzo());
		
		log.info("Salvataggio del prodotto sul DB");
		try {
			prodottoRepo.save(prodotto);
		} catch (DataIntegrityViolationException e) {
			throw new DatiDuplicatiException(String.format("Errore: Dati duplicati, '%s' esiste già.", prodotto.getNomeProdotto()));
		}
		
		log.info("Creo i set per evitare ripetizioni di allergeni già inseriti");
		Set<Integer> allergeniA = new HashSet<>(prodottoRequest.getIdAllergeni());
		Set<Integer> allergeniB = new HashSet<>(collect);
		
		log.info("Controllo e aggiungo gli allergeni al prodotto");
		for(Integer loopItem: allergeniA) {
			if(!allergeniB.contains(loopItem)) {
				prodottiAllergeni.add(ProdottiAllergeni
						.builder().allergene(
								Allergene.builder()
										 .idAllergene(allergeneService.getAllergeneById(loopItem).getIdAllergene())
										 .nomeAllergene(allergeneService.getAllergeneById(loopItem).getNomeAllergene())
										 .build())
								  .prodotto(
								Prodotto.builder()
										.idProdotto(prodotto.getIdProdotto())
										.build())
						.build());
			}
		}
		
		log.info("Salvataggio lista prodotti e allergeni");
		prodAllergeniRepo.saveAll(prodottiAllergeni);
		
		log.info("Creo il DTO prodotto di response");
		ProdottoDTOResponse prodottoResponse = new ProdottoDTOResponse(
				prodotto.getIdProdotto(), 
				prodotto.getNomeProdotto(), 
				prodotto.getCategoria(), 
				prodotto.getDisponibile(), 
				prodotto.getPrezzo(), 
				StaticModelMapper.convertList(
						prodottiAllergeni
						.stream()
						.map(ProdottiAllergeni::getAllergene)
						.toList(), AllergeneDTOResponse.class)
				);
		
		return prodottoResponse;
	}

	@Override
	@Transactional
	public ProdottoDTOResponse deleteAllergeni(Integer idProdotto, AllergeneDeleteDTORequest allergene)
			throws NotFoundException {
		Prodotto prodotto = prodottoRepo.findProdottoByIdProdotto(idProdotto);
		
		for(ProdottiAllergeni pa : prodotto.getProdottiAllergeni()) {
			if(pa.getAllergene().getIdAllergene() == allergene.getIdAllergene()) {
				prodAllergeniRepo.deleteById(pa.getId());
			}
		}
		
		List<ProdottiAllergeni> prodottiAllergeni = prodAllergeniRepo.findAllByProdotto(prodotto);
		
		log.info("Creo il DTO prodotto di response");
		ProdottoDTOResponse prodottoResponse = new ProdottoDTOResponse(
				prodotto.getIdProdotto(), 
				prodotto.getNomeProdotto(), 
				prodotto.getCategoria(), 
				prodotto.getDisponibile(), 
				prodotto.getPrezzo(), 
				StaticModelMapper.convertList(
						prodottiAllergeni
						.stream()
						.map(ProdottiAllergeni::getAllergene)
						.toList(), AllergeneDTOResponse.class)
				);
		
		return prodottoResponse;
	}

	@Override
	public List<ProdottoDTOResponse> logicDeleteProdotti(List<LogicDeleteProdottoDTORequest> logicProdotti) {
		log.info("Eliminazione logica di uno o più prodotti");
		List<ProdottoDTOResponse> list = new ArrayList<>();
		
		List<Prodotto> prodotti = prodottoRepo.findAll();
		for(Prodotto prodotto : prodotti) {
			for(LogicDeleteProdottoDTORequest loopItem : logicProdotti) {
				if(prodotto.getIdProdotto() == loopItem.getIdProdotto()) {
					prodotto.setDisponibile(false);
					prodottoRepo.save(prodotto);
				}
			}
			
			ProdottoDTOResponse prodottoResponse = new ProdottoDTOResponse(
			prodotto.getIdProdotto(), 
			prodotto.getNomeProdotto(), 
			prodotto.getCategoria(), 
			prodotto.getDisponibile(), 
			prodotto.getPrezzo(), 
			StaticModelMapper.convertList(
					prodotto.getProdottiAllergeni()
					.stream()
					.map(ProdottiAllergeni::getAllergene)
					.toList(), AllergeneDTOResponse.class)
			);
			
			log.info("Creo la lista response dei prodotti disponibili");
			if(prodottoResponse.getDisponibile()) {
				list.add(prodottoResponse);
			}
		}
		return list;
	}

	@Override
	@Transactional
	public void deleteProdottoById(Integer idProdotto) throws NotFoundException {
		prodottoRepo.deleteById(getProdottoById(idProdotto).getIdProdotto());
	}
}