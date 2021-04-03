package com.example.demo.DTO;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class EstudioDTO {
	
	private Long estudioId;
	
	@NotNull(message = "El nombre del titulo no puede ser nulo")
	private String nombreTitulo;
	
	//@NotNull
	private Double calificacion;
	
	@NotNull(message = "El tipo de estudio no puede ser nulo")
	private String tipo;
	
	@NotNull(message = "El tiempo de estudio no puede ser nulo")
	private String tiempo;
	
	
	// DATOS DE LA INSTITUCION EDUCATIVA
	
	@NotNull
	private String entidad;
}
