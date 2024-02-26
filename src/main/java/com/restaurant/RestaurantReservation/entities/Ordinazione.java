package com.restaurant.RestaurantReservation.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
@JsonPropertyOrder({})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ordinazione implements Serializable{

	private static final long serialVersionUID = -4846162387534016683L;
	
	//variabili
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idOrdine;
	
	@ManyToOne
	@JoinColumn(name = "id_prenotazione")
	private Prenotazione prenotazione;
	
	@ManyToOne
	@JoinColumn(name = "id_prodotto")
	private Prodotto prodotto;
	
	private String stato;
	
	private String note;
}