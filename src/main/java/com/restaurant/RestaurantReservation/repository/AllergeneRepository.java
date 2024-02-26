package com.restaurant.RestaurantReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.RestaurantReservation.entities.Allergene;

public interface AllergeneRepository extends JpaRepository<Allergene, Integer>{
	
}