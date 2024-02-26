package com.restaurant.RestaurantReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.RestaurantReservation.entities.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer>{

	Evento findEventoByIdEvento(Integer evento);
}