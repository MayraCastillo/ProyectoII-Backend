package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Empresa {

	private Long nit;

	private String nombre;

	private String telefono;

	private String direccion;

	private String frecuenciaPago;

}
