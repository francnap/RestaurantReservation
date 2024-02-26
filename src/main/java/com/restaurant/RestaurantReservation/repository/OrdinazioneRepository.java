package com.restaurant.RestaurantReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.RestaurantReservation.entities.Ordinazione;

public interface OrdinazioneRepository extends JpaRepository<Ordinazione, Integer>{

}