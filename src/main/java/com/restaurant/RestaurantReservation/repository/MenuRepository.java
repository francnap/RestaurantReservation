package com.restaurant.RestaurantReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.RestaurantReservation.entities.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer>{

}