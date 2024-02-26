package com.restaurant.RestaurantReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.RestaurantReservation.entities.Prodotto;

public interface ProdottoRepository extends JpaRepository<Prodotto, Integer>{

	Prodotto findProdottoByIdProdotto(Integer idProdotto);
}