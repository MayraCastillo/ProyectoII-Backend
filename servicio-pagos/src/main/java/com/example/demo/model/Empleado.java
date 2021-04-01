package com.example.demo.model;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Empleado {
	
	private Long numeroDocumento;
	
	private Municipio municipio;
	
	private String nombres;
	
	private String apellidos;
	
	private Date fechaNacimiento;
	
	private String tipoDocumento;
	
	private String telefono;
	
	private String direccion;
	
	private String estado;
	
	private String correo;

}
