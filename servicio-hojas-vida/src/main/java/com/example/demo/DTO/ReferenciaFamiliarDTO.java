package com.example.demo.DTO;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class ReferenciaFamiliarDTO {
	
	private Long referenciaId;
	
	@NotNull(message = "Los nombres de la referencia familiar no pueden ser nulos")
	private String nombres;
	
	private String apellidos;
	
	@NotNull(message = "El telefono no puede ser nulo")
	private String telefono;
	
	@NotNull(message = "El parentesco no puede ser nulo")
	private String parentesco;
}
