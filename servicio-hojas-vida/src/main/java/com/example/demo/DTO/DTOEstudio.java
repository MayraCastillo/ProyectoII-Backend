package com.example.demo.DTO;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DTOEstudio {
	
	@NotNull(message = "El nombre del titulo no puede ser nulo")
	private String nombreTitulo;
	
	@NotNull
	private Double calificacion;
	
	@NotNull(message = "El tipo de estudio no puede ser nulo")
	private String tipo;
	
	@NotNull(message = "El tiempo de estudio no puede ser nulo")
	private Integer tiempo;
	
	
	// DATOS DE LA INSTITUCION EDUCATIVA
	
	@NotNull
	private String entidad;
}
