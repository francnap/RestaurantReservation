package com.restaurant.RestaurantReservation.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.RestaurantReservation.dtos.request.PrenotazioneDTODeleteRequest;
import com.restaurant.RestaurantReservation.dtos.request.PrenotazioneDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.PrenotazioneDTOResponse;
import com.restaurant.RestaurantReservation.entities.Evento;
import com.restaurant.RestaurantReservation.entities.Prenotazione;
import com.restaurant.RestaurantReservation.entities.Tavolo;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.repository.EventoRepository;
import com.restaurant.RestaurantReservation.repository.PrenotazioneRepository;
import com.restaurant.RestaurantReservation.repository.TavoloRepository;
import com.restaurant.RestaurantReservation.service.PrenotazioneService;
import com.restaurant.RestaurantReservation.utils.ModelMapperConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrenotazioneServiceImpl implements PrenotazioneService{
	
	private static final Logger log = LoggerFactory.getLogger(PrenotazioneService.class);
	
//	private final ModelMapper modelMapper;
	
	@Autowired
	PrenotazioneRepository prenotazioneRepo;
	
	@Autowired
	TavoloRepository tavoloRepo;
	
	@Autowired
	EventoRepository eventoRepo;

	
	
	@Override
	public List<PrenotazioneDTOResponse> getAllPrenotazioni() {
		List<Prenotazione> prenotazioni = prenotazioneRepo.findAll();
		List<PrenotazioneDTOResponse> list = new ArrayList<>();
		for(Prenotazione p : prenotazioni) {
			ModelMapper mp = ModelMapperConverter.conversionePrenotazioneToPrenotazioneDTOResponse();
			list.add(mp.map(p, PrenotazioneDTOResponse.class));
		}
		return list;
	}
	
	@Override
	public PrenotazioneDTOResponse getPrenotazioneById(Integer idPrenotazione) throws NotFoundException {
		Optional<Prenotazione> prenotazione = prenotazioneRepo.findById(idPrenotazione);
		if(prenotazione.isEmpty()) {
			throw new NotFoundException("Nessuna prenotazione trovata con id: " + idPrenotazione);
		}
		ModelMapper mp = ModelMapperConverter.conversionePrenotazioneToPrenotazioneDTOResponse();
		return mp.map(prenotazione.get(), PrenotazioneDTOResponse.class);
	}

	@Override
	@Transactional
	public PrenotazioneDTOResponse createPrenotazione(PrenotazioneDTORequest prenotazione) throws NotFoundException {
		log.info("Inserimento della prenotazione");
		Tavolo tavolo = tavoloRepo.findTavoloByIdTavolo(prenotazione.getTavolo());
		Evento evento = eventoRepo.findEventoByIdEvento(prenotazione.getEvento());
		
		log.info("Controllo se i dati sono stati inseriti correttamente");
		if(prenotazione.getNote().equals(null) || prenotazione.getNote().isBlank() || 
				prenotazione.getOrarioArrivo().equals(null)) {
			throw new NotFoundException("Mancato inserimento di dati necessari.");
		}
		
		Prenotazione prenota = new Prenotazione();
		log.info("Set delle variabili della prenotazione");
		prenota.setTavolo(tavolo);
		prenota.setOrarioPrenotazione(ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault()));
		prenota.setOrarioPresuntoArrivo(prenotazione.getOrarioArrivo());
		prenota.setEvento(evento);
		prenota.setNote(prenotazione.getNote());
		
		log.info("Salvataggio della prenotazione sul DB");
		prenotazioneRepo.save(prenota);
		
		PrenotazioneDTOResponse prenotazioneResponse = new PrenotazioneDTOResponse(
				prenota.getIdPrenotazione(), 
				prenota.getTavolo().getIdTavolo(), 
				prenota.getTavolo().getNote(), 
				prenota.getTavolo().getNumeroPosti(), 
				prenota.getOrarioPrenotazione().withZoneSameInstant(ZoneId.of("Europe/Rome")), 
				prenota.getOrarioPresuntoArrivo().withZoneSameInstant(ZoneId.of("Europe/Rome")), 
				prenota.getEvento().getIdEvento(), 
				prenota.getEvento().getDescrizione(), 
				prenota.getNote(), 
				prenota.getOrdinazioni()
				);
		
		log.info("Creo la lista delle prenotazioni response");
		return prenotazioneResponse;
	}
	
	@Override
	@Transactional
	public PrenotazioneDTOResponse updatePrenotazione(Integer idPrenotazione,
			PrenotazioneDTORequest prenotazioneRequest) throws NotFoundException {
		Prenotazione prenotazione = prenotazioneRepo.findPrenotazioneByIdPrenotazione(idPrenotazione);
		Tavolo tavolo = null;
		Evento evento = null;
		
		if(prenotazioneRequest.getTavolo() != null) {
			tavolo = tavoloRepo.findTavoloByIdTavolo(prenotazioneRequest.getTavolo());
		}else {
			tavolo = prenotazione.getTavolo();
		}
		if(prenotazioneRequest.getEvento() != null) {
			evento = eventoRepo.findEventoByIdEvento(prenotazioneRequest.getEvento());
		}else {
			evento = prenotazione.getEvento();
		}
		
		prenotazione.setTavolo(tavolo);
		prenotazione.setOrarioPrenotazione(ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault()));
		prenotazione.setOrarioPresuntoArrivo(prenotazione.getOrarioPresuntoArrivo());
		prenotazione.setEvento(evento);
		prenotazione.setNote(prenotazione.getNote());
		
		return ModelMapperConverter.conversionePrenotazioneToPrenotazioneDTOResponse()
				.map(prenotazioneRepo.save(prenotazione), PrenotazioneDTOResponse.class);
	}

	@Override
	@Transactional
	public void deletePrenotazioneByNomePrenotante(PrenotazioneDTODeleteRequest deletedPrenotazione) throws NotFoundException {
		Prenotazione prenotazione = prenotazioneRepo.findPrenotazioneByNote(deletedPrenotazione.getNote());
		if(prenotazione == null) {
			throw new NotFoundException(String.format("Nessuna prenotazione a nome '%s' trovata.", deletedPrenotazione.getNote()));
		}
		prenotazioneRepo.deleteById(prenotazione.getIdPrenotazione());
	}
}