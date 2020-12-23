package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="comentario")
public class Comentario extends BaseEntity {
	
	@NotEmpty
	private String titulo;
	
	@NotEmpty
	private String texto;
	
	@ManyToOne()
	@JoinColumn(name = "cliente_id")
	@NotNull
	@Valid
	private Cliente cliente;
	
	@ManyToOne()
	@JoinColumn(name = "videojuego_id")
	@Valid
	private Videojuego videojuego;
	
	@ManyToOne()
	@JoinColumn(name = "merchandasing_id")
	@Valid
	private Merchandasing merchandasing;
	
	@ManyToOne()
	@JoinColumn(name = "pelicula_id")
	@Valid
	private Pelicula pelicula;
	
	/*
	  
	@ManyToOne(fetch = FetchType.LAZY cascade = CascadeType.ALL)
	@JoinColumn(name = "cliente_id")
	@NotNull
	@Valid
	private Cliente		cliente;
	
	@ManyToOne(fetch = FetchType.LAZY cascade = CascadeType.ALL)
	@JoinColumn(name = "videojuego_id")
	@NotNull
	@Valid
	private Videojuego		videojuego;
	
	@ManyToOne(fetch = FetchType.LAZY cascade = CascadeType.ALL)
	@JoinColumn(name = "pelicula_id")
	@NotNull
	@Valid
	private Pelicula		pelicula;
	
	@ManyToOne(fetch = FetchType.LAZY cascade = CascadeType.ALL)
	@JoinColumn(name = "merchandasing_id")
	@NotNull
	@Valid
	private Merchandasing		merchandasing;
	
	*/

}
