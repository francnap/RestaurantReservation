package com.restaurant.RestaurantReservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.RestaurantReservation.entities.ProdottiAllergeni;
import com.restaurant.RestaurantReservation.entities.Prodotto;

public interface ProdottiAllergeniRepository extends JpaRepository<ProdottiAllergeni, Integer>{

	List<ProdottiAllergeni> findAllByProdotto(Prodotto prodotto);
}