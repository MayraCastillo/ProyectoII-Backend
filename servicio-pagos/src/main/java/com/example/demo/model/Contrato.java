package com.example.demo.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contrato {

	private ContratoPk contratoPk;

	private Long contratoId;

	private TarifaArl tarifaArl;

	private Date fechaFinContrato;

	private Date fechaIncioPrueba;

	private Date fechaFinPrueba;

	private String tipo;

	private String estado;
	
	private Double salarioBase;

}
