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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
	@NotNull(message = "El valor del campo contratoId no puede ser nulo")
	private Long contratoId;
	
	@Transient
	private Contrato contrato;
	
	@NotNull(message = "El valor del campo pagoNomina no puede ser nulo")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pago_nomina_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Pago_nomina pagoNomina;
	
	@Column(name = "basico_devengado")
	@Positive(message = "El valor del campo BasicoDevengado no puede ser negativo")
	private Double BasicoDevengado;
	
	@Column(name = "auxilio_transporte")
	@Positive(message = "El valor del campo auxilioTransporte no puede ser negativo")
	private Double auxilioTransporte;
	
	@Column(name = "horas_extras")
	@Positive(message = "El valor del campo horasExtras no puede ser negativo")
	private Double horasExtras;
	
	@Column(name = "recargos")
	@Positive(message = "El valor del campo recargos no puede ser negativo")
	private Double recargos;
	
	@Column(name = "bonificaciones")
	@Positive(message = "El valor del campo comisiones no puede ser negativo")
	private Double comisiones;
	
	@Column(name = "ingreso_salarial")
	@Positive(message = "El valor del campo otrosIngresoSalarial no puede ser negativo")
	private Double otrosIngresoSalarial;
	
	@Column(name = "ingreso_no_salarial")
	@Positive(message = "El valor del campo otrosIngresoNoSalarial no puede ser negativo")
	private Double otrosIngresoNoSalarial;
	
	@Column(name = "total_devengado")
	@Positive(message = "El valor del campo totalDevengado no puede ser negativo")
	private Double totalDevengado;
	
	@Column(name = "dev_menos_nosal")
	@Positive(message = "El valor del campo devengadoMenosNoSalariales no puede ser negativo")
	private Double devengadoMenosNoSalariales;
	
	@Column(name = "dev_menosnosal_menosauxTrans")
	@Positive(message = "El valor del campo devMenosNoSalMenosAuxTrans no puede ser negativo")
	private Double devMenosNoSalMenosAuxTrans;
	
	@Column(name = "salud_emp")
	@Positive(message = "El valor del campo saludEmpleado no puede ser negativo")
	private Double saludEmpleado;
	
	@Column(name = "pension_emp")
	@Positive(message = "El valor del campo pensionEmpleado no puede ser negativo")
	private Double pensionEmpleado;
	
	@Column(name = "fon_sol_pensional")
	@Positive(message = "El valor del campo fondoSolidaridadPensional no puede ser negativo")
	private Double fondoSolidaridadPensional;
	
	@Column(name = "total_deducciones")
	@Positive(message = "El valor del campo totalDeducciones no puede ser negativo")
	private Double totalDeducciones;
	
	@Column(name = "neto_pagado")
	@Positive(message = "El valor del campo netoPagado no puede ser negativo")
	private Double netoPagado;
	
	@Column(name = "estado")
	@NotEmpty(message = "El campo estado no puede ser vacio")
	private String estado;

	private static final long serialVersionUID = -3184154142559365272L;

}
