package com.restaurant.RestaurantReservation.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import com.restaurant.RestaurantReservation.dtos.request.PrenotazioneDTORequest;
import com.restaurant.RestaurantReservation.dtos.response.PrenotazioneDTOResponse;
import com.restaurant.RestaurantReservation.dtos.response.SezioneMenuDTOResponse;
import com.restaurant.RestaurantReservation.entities.Prenotazione;
import com.restaurant.RestaurantReservation.entities.SezioneMenu;

public class ModelMapperConverter {
	
	public static ModelMapper conversionePrenotazioneToPrenotazioneDTOResponse() {
		ModelMapper mp = new ModelMapper();
		
		TypeMap<Prenotazione, PrenotazioneDTOResponse> propertyMapper = 
				mp.createTypeMap(Prenotazione.class, PrenotazioneDTOResponse.class);
		
		propertyMapper.addMappings(
				mapper -> mapper.map(
						src -> src.getTavolo().getNote(), PrenotazioneDTOResponse::setDescTavolo));
		
		propertyMapper.addMappings(
				mapper -> mapper.map(
						src -> src.getEvento().getDescrizione(), PrenotazioneDTOResponse::setDescEvento));
		
		propertyMapper.addMappings(
				mapper -> mapper.map(
						src -> src.getTavolo().getNumeroPosti(), PrenotazioneDTOResponse::setNumeroPosti));
		
		return mp;
	}
	
	public static void conversioneRequestToEntity(PrenotazioneDTORequest prenotazioneRequest, Prenotazione prenotazione) {
		ModelMapper mp = new ModelMapper();
		mp.map(prenotazioneRequest, prenotazione);
	}
	
	public static ModelMapper conversioneSezioneMenuToSezioneMenuDTOResponse() {
		ModelMapper mp = new ModelMapper();
		
		TypeMap<SezioneMenu, SezioneMenuDTOResponse> propertyMapper = 
				mp.createTypeMap(SezioneMenu.class, SezioneMenuDTOResponse.class);
		
		propertyMapper.addMappings(
				mapper -> mapper.map(
						src -> src.getMenu().getIdMenu(), SezioneMenuDTOResponse::setIdMenu));
		
		propertyMapper.addMappings(
				mapper -> mapper.map(
						src -> src.getMenu().getDescMenu(), SezioneMenuDTOResponse::setDescMenu));
		
		return mp;
	}
}