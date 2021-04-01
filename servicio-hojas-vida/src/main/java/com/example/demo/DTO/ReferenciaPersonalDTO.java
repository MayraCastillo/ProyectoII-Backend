package com.example.demo.DTO;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class ReferenciaPersonalDTO {
	
	private Long referenciaId;
	
	@NotNull(message = "Los nombres de la referencia personal no pueden ser nulos")
	private String nombres;
	
	private String apellidos;
	
	@NotNull(message = "El telefono no puede ser nulo")
	private String telefono;
}
