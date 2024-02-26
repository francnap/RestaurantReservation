package com.restaurant.RestaurantReservation.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.RestaurantReservation.dtos.request.LogicDeleteTavoloDTORequest;
import com.restaurant.RestaurantReservation.dtos.request.TavoloDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.TavoloDTOResponse;
import com.restaurant.RestaurantReservation.entities.Tavolo;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.repository.TavoloRepository;
import com.restaurant.RestaurantReservation.service.TavoloService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TavoloServiceImpl implements TavoloService{
	
	private static final Logger log = LoggerFactory.getLogger(TavoloServiceImpl.class);
	
	private final ModelMapper modelMapper;
	
	@Autowired
	TavoloRepository tavoloRepo;

	
	
	@Override
	public List<TavoloDTOResponse> getAllTavoli() {
		return tavoloRepo.findAll()
				.stream()
				.map(tavolo -> modelMapper.map(tavolo, TavoloDTOResponse.class))
				.collect(Collectors.toList());
	}
	
	@Override
	public TavoloDTOResponse getTavoloById(Integer idTavolo) throws NotFoundException {
		Optional<Tavolo> tavolo = tavoloRepo.findById(idTavolo);
		if(tavolo.isEmpty()) {
			throw new NotFoundException("Nessun tavolo trovato con l'id: " + idTavolo);
		}
		return modelMapper.map(tavolo.get(), TavoloDTOResponse.class);
	}

	@Override
	@Transactional
	public List<TavoloDTOResponse> createTavoli(List<TavoloDTORequest> listaTavoli) throws MissingData {
		log.info("Inserimento dei tavoli");
		List<TavoloDTOResponse> list = new ArrayList<>();
		
		for(TavoloDTORequest tavoloRequest : listaTavoli) {
			
			log.info("Controllo se i dati dei tavoli sono stati inseriti correttamente");
			if(tavoloRequest.getNote().equals(null) || tavoloRequest.getNote().isBlank() || 
					tavoloRequest.getNumeroPosti() < 1) {
				throw new MissingData("Mancato inserimento di dati necessari.");
			}
			
			Tavolo tavolo = new Tavolo();
			log.info("Set delle variabili del singolo tavolo");
			tavolo.setNote(tavoloRequest.getNote());
			tavolo.setNumeroPosti(tavoloRequest.getNumeroPosti());
			
			log.info("Salvataggio del tavolo nel DB");
			tavoloRepo.save(tavolo);
			
			TavoloDTOResponse tavoloResponse = new TavoloDTOResponse(
					tavolo.getIdTavolo(), 
					tavolo.getNote(), 
					tavolo.getNumeroPosti(), 
					true, 
					tavolo.getPrenotazioni()
			);
			
			log.info("Creo la lista dei tavoli response");
			list.add(tavoloResponse);
		}
		return list;
	}

	@Override
	@Transactional
	public TavoloDTOResponse updateTavolo(Integer idTavolo, TavoloDTORequest tavoloRequest) throws NotFoundException {
		Tavolo tavolo = modelMapper.map(getTavoloById(idTavolo), Tavolo.class);
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		modelMapper.map(tavoloRequest, tavolo);
		return modelMapper.map(tavoloRepo.save(tavolo), TavoloDTOResponse.class);
	}

	@Override
	@Transactional
	public void deleteTavoloById(Integer idTavolo) throws NotFoundException {
		tavoloRepo.deleteById(getTavoloById(idTavolo).getIdTavolo());
	}

	@Override
	public List<TavoloDTOResponse> logicDeleteTavoli(List<LogicDeleteTavoloDTORequest> logicTavoli) {
		log.info("Eliminazione logica dei tavoli");
		List<TavoloDTOResponse> list = new ArrayList<>();
		
		List<Tavolo> allTavoli = tavoloRepo.findAll();
		for(Tavolo tavolo : allTavoli) {
			for(LogicDeleteTavoloDTORequest ldtRequest : logicTavoli) {
				if(tavolo.getIdTavolo() == ldtRequest.getIdTavolo()) {
					tavolo.setNote(ldtRequest.getNote());
					tavolo.setUtilizzabile(false);
					tavoloRepo.save(tavolo);
				}
			}
			
			TavoloDTOResponse tavoloResponse = new TavoloDTOResponse(
					tavolo.getIdTavolo(), 
					tavolo.getNote(), 
					tavolo.getNumeroPosti(), 
					tavolo.getUtilizzabile(), 
					tavolo.getPrenotazioni()
			);
			
			log.info("Creo la lista dei tavoli response");
			list.add(tavoloResponse);
		}
		return list;
	}
}