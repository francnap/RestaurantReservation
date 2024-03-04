package com.restaurant.RestaurantReservation.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class SezioneMenu implements Serializable{

	private static final long serialVersionUID = -2273524811956225621L;

	//variabili
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idSezioneMenu;
	
	@Column(unique = true)
	@NotNull
	private String descrizione;
	
	@JoinColumn(name = "id_menu")
	@ManyToOne
	private Menu menu;
	
	@OneToMany(mappedBy = "sezione")
	private List<SezioneMenuProdotti> sezioni;
}