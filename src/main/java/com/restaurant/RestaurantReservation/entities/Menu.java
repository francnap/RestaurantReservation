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
public class Menu implements Serializable{

	private static final long serialVersionUID = 6971014865310729263L;

	//varibili
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMenu;
	
	@Column(unique = true)
	@NotNull
	private String descMenu;
	
	@OneToMany(mappedBy = "menu")
	private List<SezioneMenu> sezioni;
}