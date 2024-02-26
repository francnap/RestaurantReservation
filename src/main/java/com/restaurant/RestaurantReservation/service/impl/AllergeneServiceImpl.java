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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.RestaurantReservation.dtos.request.AllergeneDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.AllergeneDTOResponse;
import com.restaurant.RestaurantReservation.entities.Allergene;
import com.restaurant.RestaurantReservation.exceptions.DatiDuplicatiException;
import com.restaurant.RestaurantReservation.exceptions.MissingData;
import com.restaurant.RestaurantReservation.exceptions.NotFoundException;
import com.restaurant.RestaurantReservation.repository.AllergeneRepository;
import com.restaurant.RestaurantReservation.service.AllergeneService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AllergeneServiceImpl implements AllergeneService{
	
	private static final Logger log = LoggerFactory.getLogger(AllergeneServiceImpl.class);
	
	private final ModelMapper modelMapper;
	
	@Autowired
	AllergeneRepository allergeneRepo;

	
	
	@Override
	public List<AllergeneDTOResponse> getAllAllergeni() {
		return allergeneRepo.findAll()
				.stream()
				.map(allergene -> modelMapper.map(allergene, AllergeneDTOResponse.class))
				.collect(Collectors.toList());
	}
	
	@Override
	public AllergeneDTOResponse getAllergeneById(Integer idAllergene) throws NotFoundException {
		Optional<Allergene> allergene = allergeneRepo.findById(idAllergene);
		if(allergene.isEmpty()) {
			throw new NotFoundException("Nessun allergene trovato con questo id: " + idAllergene);
		}
		return modelMapper.map(allergene.get(), AllergeneDTOResponse.class);
	}
	
	@Override
	public List<AllergeneDTOResponse> createAllergeni(List<AllergeneDTORequest> listaAllergeni) throws MissingData, DatiDuplicatiException {
		log.info("Inserimento degli allergeni");
		List<AllergeneDTOResponse> list = new ArrayList<>();
		
		for(AllergeneDTORequest allergeneRequest : listaAllergeni) {
			
			log.info("Controllo se il nome dell'allergene è stato inserito correttamente");
			if(allergeneRequest.getNomeAllergene().equals(null) || 
					allergeneRequest.getNomeAllergene().isBlank()) {
				throw new MissingData("Mancato inserimento di dati necessari.");
			}
			
			Allergene allergene = new Allergene();
			log.info("Set delle variabili del singolo allergene");
			allergene.setNomeAllergene(allergeneRequest.getNomeAllergene());
			
			try {
				log.info("Salvataggio dell'allergene sul DB");
				allergeneRepo.save(allergene);
			} catch (DataIntegrityViolationException e) {
				throw new DatiDuplicatiException(String.format("Errore: Dati duplicati, l'entità con questo valore '%s' già esiste.", allergene.getNomeAllergene()));
			}
			
			AllergeneDTOResponse allergeneResponse = new AllergeneDTOResponse(
					allergene.getIdAllergene(),
					allergene.getNomeAllergene()
			);
			
			log.info("Creo la lista degli allergeni response");
			list.add(allergeneResponse);
		}
		return list;
	}

	@Override
	@Transactional
	public void deleteAllergeneById(Integer idAllergene) throws NotFoundException {
		allergeneRepo.deleteById(getAllergeneById(idAllergene).getIdAllergene());
	}

	@Override
	@Transactional
	public AllergeneDTOResponse updateAllergene(Integer idAllergene, AllergeneDTORequest allergeneRequest) throws NotFoundException {
		Allergene allergene = modelMapper.map(getAllergeneById(idAllergene), Allergene.class);
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		modelMapper.map(allergeneRequest, allergene);
		return modelMapper.map(allergeneRepo.save(allergene), AllergeneDTOResponse.class);
	}
}