package com.restaurant.RestaurantReservation.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Prenotazione implements Serializable{

	private static final long serialVersionUID = -2684309364454639644L;
	
	//variabili
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPrenotazione;
	
	@ManyToOne
	@JoinColumn(name = "id_tavolo")
	private Tavolo tavolo;
	
	//TODO cercare annotation per la crezione automatica della prenotazione progetto vecchio
	private ZonedDateTime orarioPrenotaizone; //TODO cambiare nome

	private ZonedDateTime orarioPresuntoArrivo;
	
	private ZonedDateTime orarioRealeArrivo;
	
	private ZonedDateTime orarioChiusuraPrenotazione;

	@ManyToOne
	@JoinColumn(name = "id_evento")
	private Evento evento;
	
	private String note;
	
	//relaizoni
	@OneToMany(mappedBy = "prenotazione")
	private List<Ordinazione> ordinazioni;
}