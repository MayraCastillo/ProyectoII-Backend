package com.example.demo.DTO;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class ExperienciaLaboralDTO {
	
	private Long expId;
	
	@NotNull(message = "El cargo no puede ser nulo")
	private String cargoEmpresa;	
	
	@NotNull(message = "El tiempo no puede ser nulo")
	private String tiempo;

	//@NotNull
	private Double calificacion;
	
	
	// DATOS DE LA EMPRESA EXTERNA
	
	@NotNull (message = "El nombre de la empresa no puede ser vacío")
	private String nombreEmpresa;	
	
	@NotNull (message = "El nombre del contacto no puede ser vacío")
	private String contacto;
	
	@NotNull (message = "El telefono del contacto de la empresa externa no puede ser vacío")
	private String telefonoEmpresa;
}
