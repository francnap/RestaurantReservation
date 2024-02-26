package com.restaurant.RestaurantReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.restaurant.RestaurantReservation.entities.Prenotazione;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer>{

	Prenotazione findPrenotazioneByIdPrenotazione(Integer idPrenotazione);
	
	@Query("SELECT p FROM Prenotazione p WHERE UPPER(p.note) = UPPER(:note)")
	Prenotazione findPrenotazioneByNote(String note);
}