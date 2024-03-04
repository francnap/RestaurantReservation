package com.restaurant.RestaurantReservation.utils;

import java.util.ArrayList;
import java.util.List;

import com.restaurant.RestaurantReservation.dtos.response.MenuDTOResponse;
import com.restaurant.RestaurantReservation.dtos.response.ProdottoDTOResponse;
import com.restaurant.RestaurantReservation.dtos.response.SezioneMenuDTOResponse;
import com.restaurant.RestaurantReservation.entities.Menu;
import com.restaurant.RestaurantReservation.entities.SezioneMenu;
import com.restaurant.RestaurantReservation.entities.SezioneMenuProdotti;

public class Utilities {
	
	public static List<MenuDTOResponse> objectListMenuCompleto(List<Menu> menus) {
		List<MenuDTOResponse> list = new ArrayList<>();
		List<SezioneMenuDTOResponse> listSez = new ArrayList<>();
		for(Menu item : menus) {
			for(SezioneMenu loop : item.getSezioni()) {
				listSez.add(SezioneMenuDTOResponse
						.builder()
							.idSezioneMenu(loop.getIdSezioneMenu())
							.descrizione(loop.getDescrizione())
							.prodotti(StaticModelMapper.convertList(
											loop.getSezioni()
											.stream()
											.map(SezioneMenuProdotti::getProdotto)
											.toList(), ProdottoDTOResponse.class))
						.build());
			}
			
			MenuDTOResponse menuResponse = MenuDTOResponse
					.builder()
						.idMenu(item.getIdMenu())
						.descMenu(item.getDescMenu())
						.sezioni(listSez)
					.build();
			
			list.add(menuResponse);
		}
		return list;
	}
	
	public static MenuDTOResponse objectMenuCompleto(Menu menu) {
		List<SezioneMenuDTOResponse> listSez = new ArrayList<>();
		for(SezioneMenu loop : menu.getSezioni()) {
			listSez.add(SezioneMenuDTOResponse
					.builder()
						.idSezioneMenu(loop.getIdSezioneMenu())
						.descrizione(loop.getDescrizione())
						.prodotti(StaticModelMapper.convertList(
										loop.getSezioni()
										.stream()
										.map(SezioneMenuProdotti::getProdotto)
										.toList(), ProdottoDTOResponse.class))
					.build());
		}
		
		MenuDTOResponse menuResponse = MenuDTOResponse
				.builder()
					.idMenu(menu.getIdMenu())
					.descMenu(menu.getDescMenu())
					.sezioni(listSez)
				.build();
		
		return menuResponse;
	}
}