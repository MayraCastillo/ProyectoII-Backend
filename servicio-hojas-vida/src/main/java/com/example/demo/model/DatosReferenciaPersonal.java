package com.example.demo.model;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;


import lombok.Data;

@Data
public class DatosReferenciaPersonal {
	
	@NotNull(message = "Los nombres de la referencia personal no pueden ser nulos")
	private String nombres;
	
	@Column(name = "apellidos")
	private String apellidos;
	
	@NotNull(message = "El telefono no puede ser nulo")
	private String telefono;
}
