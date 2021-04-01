package com.example.demo.model;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContratoPk {
	
	private Empleado empleado;
	private Empresa empresa;
	private Date fechaInicioContrato;

}
