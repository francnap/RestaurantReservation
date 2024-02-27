package com.restaurant.RestaurantReservation.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

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
	
	@CreationTimestamp
	private ZonedDateTime orarioPrenotazione;

	private ZonedDateTime orarioPresuntoArrivo;
	
	private ZonedDateTime orarioRealeArrivo;
	
	private ZonedDateTime orarioChiusuraPrenotazione;

	@ManyToOne
	@JoinColumn(name = "id_evento")
	private Evento evento;
	
	private String note;
	
	//relazioni
	@OneToMany(mappedBy = "prenotazione")
	private List<Ordinazione> ordinazioni;
}