package com.restaurant.RestaurantReservation.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(schema = "", name  = "")
@JsonPropertyOrder({})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tavolo implements Serializable{
	
	private static final long serialVersionUID = -1262311954968463740L;
	
	//variabili
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTavolo;
	
	private String note;
	
	private Integer numeroPosti;
	
	private Boolean utilizzabile = true;
	
	//relazioni
	@OneToMany(mappedBy = "tavolo")
	private List<Prenotazione> prenotazioni;
}