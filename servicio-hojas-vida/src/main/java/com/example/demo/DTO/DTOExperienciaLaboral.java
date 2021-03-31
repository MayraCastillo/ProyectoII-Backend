package com.example.demo.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
public class DTOExperienciaLaboral {
	
	@NotNull(message = "El cargo no puede ser nulo")
	private String cargoEmpresa;	
	
	@NotNull(message = "El tiempo no puede ser nulo")
	private Integer tiempo;

	@NotNull
	private Double calificacion;
	
	
	// DATOS DE LA EMPRESA EXTERNA
	
	@NotEmpty (message = "El nombre de la empresa no puede ser vacío")
	private String nombreEmpresa;	
	
	@NotEmpty (message = "El nombre del contacto no puede ser vacío")
	private String contacto;
	
	@NotEmpty (message = "El telefono del contacto de la empresa externa no puede ser vacío")
	private String telefonoEmpresa;
}
