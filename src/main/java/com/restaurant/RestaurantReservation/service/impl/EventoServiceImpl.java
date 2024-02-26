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

import com.restaurant.RestaurantReservation.dtos.request.EventoDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.EventoDTOResponse;
import com.restaurant.RestaurantReservation.entities.Evento;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.repository.EventoRepository;
import com.restaurant.RestaurantReservation.service.EventoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventoServiceImpl implements EventoService{
	
	private static final Logger log = LoggerFactory.getLogger(EventoServiceImpl.class);
	
	private final ModelMapper modelMapper;
	
	@Autowired
	EventoRepository eventoRepo;

	
	
	@Override
	public List<EventoDTOResponse> getAllEventi() {
		return eventoRepo.findAll()
				.stream()
				.map(evento -> modelMapper.map(evento, EventoDTOResponse.class))
				.collect(Collectors.toList());
	}
	
	@Override
	public EventoDTOResponse getEventoById(Integer idEvento) throws NotFoundException {
		Optional<Evento> evento = eventoRepo.findById(idEvento);
		if(evento.isEmpty()) {
			throw new NotFoundException("Nessun evento trovato con questo id: " + idEvento);
		}
		return modelMapper.map(evento.get(), EventoDTOResponse.class);
	}

	@Override
	@Transactional
	public List<EventoDTOResponse> createEventi(List<EventoDTORequest> listaEventi) throws MissingData {
		log.info("Inserimento degli eventi");
		List<EventoDTOResponse> list = new ArrayList<>();
		
		for(EventoDTORequest eventoRequest : listaEventi) {
			log.info("Controllo se i dati sono stati inseriti correttamente");
			if(eventoRequest.getDescrizione().equals(null) || eventoRequest.getDescrizione().isBlank() || 
					eventoRequest.getMinDurataEvento() == 0 || eventoRequest.getMinDurataEvento() == null) {
				throw new MissingData("Mancato inserimento di dati necessari.");
			}
			
			Evento evento = new Evento();
			log.info("Set delle variabili del singolo evento");
			evento.setDescrizione(eventoRequest.getDescrizione());
			evento.setMinDurataEvento(eventoRequest.getMinDurataEvento());
			
			log.info("Salvataggio dell'evento sul DB");
			eventoRepo.save(evento);
			
			EventoDTOResponse eventoResponse = new EventoDTOResponse(
					evento.getIdEvento(),
					evento.getMinDurataEvento(), 
					evento.getDescrizione(), 
					evento.getPrenotazioni()
			);
			
			log.info("Creo la lista degli eventi response");
			list.add(eventoResponse);
		}
		return list;
	}

	@Override
	@Transactional
	public EventoDTOResponse updateEvento(Integer idEvento, EventoDTORequest eventoRequest) throws NotFoundException {
		Evento evento = modelMapper.map(getEventoById(idEvento), Evento.class);
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		modelMapper.map(eventoRequest, evento);
		return modelMapper.map(eventoRepo.save(evento), EventoDTOResponse.class);
	}

	@Override
	@Transactional
	public void deleteEventoById(Integer idEvento) throws NotFoundException {
		eventoRepo.deleteById(getEventoById(idEvento).getIdEvento());
	}
}