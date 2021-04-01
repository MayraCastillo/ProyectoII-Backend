package com.example.demo.model;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DatosReferenciaFamiliar {
	
	@Column(name = "nombres")
	@NotNull(message = "Los nombres de la referencia familiar no pueden ser nulos")
	private String nombres;
	
	private String apellidos;
	
	@NotNull(message = "El telefono no puede ser nulo")
	private String telefono;
	
	@NotNull(message = "El parentesco no puede ser nulo")
	private String parentesco;
}
