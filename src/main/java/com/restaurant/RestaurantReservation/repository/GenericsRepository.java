package com.restaurant.RestaurantReservation.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericsRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
	
	Page<T> findAll(Pageable page);
}