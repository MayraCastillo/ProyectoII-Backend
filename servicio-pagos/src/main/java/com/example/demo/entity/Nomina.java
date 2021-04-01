package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.example.demo.model.Contrato;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "nominas")
public class Nomina implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "nomina_id")
	private Long nominaId;
	
	@Column(name = "contrato_id")
	private Long contratoId;
	
	@Transient
	private Contrato contrato;
	
	@NotNull(message = "El campo nomina no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pago_nomina_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Pago_nomina pagoNomina;
	
	@Column(name = "basico_devengado")
	private Double BasicoDevengado;
	
	@Column(name = "auxilio_transporte")
	private Double auxilioTransporte;
	
	@Column(name = "horas_extras")
	private Double horasExtras;
	
	@Column(name = "recargos")
	private Double recargos;
	
	@Column(name = "bonificaciones")
	private Double comisiones;
	
	@Column(name = "ingreso_salarial")
	private Double otrosIngresoSalarial;
	
	@Column(name = "ingreso_no_salarial")
	private Double otrosIngresoNoSalarial;
	
	@Column(name = "total_devengado")
	private Double totalDevengado;
	
	@Column(name = "dev_menos_nosal")
	private Double devengadoMenosNoSalariales;
	
	@Column(name = "dev_menosnosal_menosauxTrans")
	private Double devMenosNoSalMenosAuxTrans;
	
	@Column(name = "salud_emp")
	private Double saludEmpleado;
	
	@Column(name = "pension_emp")
	private Double pensionEmpleado;
	
	@Column(name = "fon_sol_pensional")
	private Double fondoSolidaridadPensional;
	
	@Column(name = "total_deducciones")
	private Double totalDeducciones;
	
	@Column(name = "neto_pagado")
	private Double netoPagado;

	private static final long serialVersionUID = -3184154142559365272L;

}
