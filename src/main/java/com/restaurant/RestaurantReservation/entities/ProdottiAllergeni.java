package com.restaurant.RestaurantReservation.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(schema = "", name = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdottiAllergeni implements Serializable{

	private static final long serialVersionUID = -1688582870769716069L;

	//variabili
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JoinColumn(name = "id_prodotto")
	@ManyToOne
	private Prodotto prodotto;
	
	@JoinColumn(name = "id_allergene")
	@ManyToOne
	private Allergene allergene;
}