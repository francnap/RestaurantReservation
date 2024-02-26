package com.restaurant.RestaurantReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.RestaurantReservation.entities.Tavolo;

public interface TavoloRepository extends JpaRepository<Tavolo, Integer>{

	Tavolo findTavoloByIdTavolo(Integer tavolo);
}