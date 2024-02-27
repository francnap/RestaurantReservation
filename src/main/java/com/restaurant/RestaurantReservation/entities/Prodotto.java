package com.restaurant.RestaurantReservation.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
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
public class Prodotto implements Serializable{

	private static final long serialVersionUID = -7386160623542416671L;
	
	//variabili
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idProdotto;
	
	@Column(unique = true)
	@NotNull
	private String nomeProdotto;

	private String categoria;
	
	private Boolean disponibile = true;
	
	private Double prezzo;
	
	//relazioni
	@OneToMany(mappedBy = "prodotto")
	private List<ProdottiAllergeni> prodottiAllergeni;
	
	@OneToMany(mappedBy = "prodotto")
	private List<Ordinazione> ordinazioni;
	
	@OneToMany(mappedBy = "prodotto")
	private List<SezioneMenuProdotti> sezioni;
}