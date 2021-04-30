package com.example.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import com.example.demo.model.Contrato;
import com.example.demo.model.DetalleNominaPk;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detalle_nominas")
public class DetalleNomina {

	@EmbeddedId
	private DetalleNominaPk detalleNominaPk;
	
	@Transient
	private Contrato contrato;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fac_sal_id")
	@JsonManagedReference
	private FactoresSalariales factoresSalariales;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fac_no_sal_id")
	@JsonManagedReference
	private FactoresNoSalariales factoresNoSalariales;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "reg_horas_id")
	@JsonManagedReference
	private RegistroHorasP registroHoras;

	@Column(name = "basico_devengado")
	@Min(value = 0, message = "El valor del campo BasicoDevengado no puede ser negativo")
	private Double BasicoDevengado;
	
	@Column(name = "auxilio_transporte")
	@Min(value = 0, message = "El valor del campo auxilioTransporte no puede ser negativo")
	private Double auxilioTransporte;

	@Column(name = "horas_extras")
	@Min(value = 0, message = "El valor del campo horasExtras no puede ser negativo")
	private Double horasExtras;

	@Column(name = "recargos")
	@Min(value = 0, message = "El valor del campo recargos no puede ser negativo")
	private Double recargos;

	@Column(name = "bonificaciones")
	@Min(value = 0, message = "El valor del campo comisiones no puede ser negativo")
	private Double comisiones;

	@Column(name = "ingreso_salarial")
	@Min(value = 0, message = "El valor del campo otrosIngresoSalarial no puede ser negativo")
	private Double otrosIngresoSalarial;

	@Column(name = "ingreso_no_salarial")
	@Min(value = 0, message = "El valor del campo otrosIngresoNoSalarial no puede ser negativo")
	private Double otrosIngresoNoSalarial;

	@Column(name = "total_devengado")
	@Min(value = 0, message = "El valor del campo totalDevengado no puede ser negativo")
	private Double totalDevengado;

	@Column(name = "dev_menos_nosal")
	@Min(value = 0, message = "El valor del campo devengadoMenosNoSalariales no puede ser negativo")
	private Double devengadoMenosNoSalariales;

	@Column(name = "dev_menosnosal_menosauxTrans")
	@Min(value = 0, message = "El valor del campo devMenosNoSalMenosAuxTrans no puede ser negativo")
	private Double devMenosNoSalMenosAuxTrans;

	@Column(name = "salud_emp")
	@Min(value = 0, message = "El valor del campo saludEmpleado no puede ser negativo")
	private Double saludEmpleado;

	@Column(name = "pension_emp")
	@Min(value = 0, message = "El valor del campo pensionEmpleado no puede ser negativo")
	private Double pensionEmpleado;

	@Column(name = "fon_sol_pensional")
	@Min(value = 0, message = "El valor del campo fondoSolidaridadPensional no puede ser negativo")
	private Double fondoSolidaridadPensional;

	@Column(name = "total_deducciones")
	@Min(value = 0, message = "El valor del campo totalDeducciones no puede ser negativo")
	private Double totalDeducciones;

	@Column(name = "neto_pagado")
	@Min(value = 0, message = "El valor del campo netoPagado no puede ser negativo")
	private Double netoPagado;

	@Column(name = "estado")
	@NotEmpty(message = "El campo estado no puede ser vacio")
	private String estado;

}
