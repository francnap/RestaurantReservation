package com.restaurant.RestaurantReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.restaurant.RestaurantReservation.entities.SezioneMenu;

public interface SezioneMenuRepository extends JpaRepository<SezioneMenu, Integer>{

	SezioneMenu findSezioneMenuByIdSezioneMenu(Integer integer);

	@Query("SELECT s "
			+ "FROM SezioneMenu s "
			+ "WHERE LOWER(REPLACE(s.descrizione, ' ', '')) = LOWER(:nomeSezione)")
	SezioneMenu findSezioneMenuByDescrizione(String nomeSezione);
}