package com.example.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrestacionesSociales {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long presCesantiasId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "contrato_id"), @JoinColumn(name = "nomina_id") })
	@JsonBackReference
	private DetalleNomina detalleNomina;

	@Column(name = "prima")
	@Min(value = 0, message = "El valor del campo prima no puede ser negativo")
	private Double prima;

	@Column(name = "cesantias")
	@Min(value = 0, message = "El valor del campo cesantias no pude ser negativo")
	private Double cesantias;

	@Column(name = "vacaciones")
	@Min(value = 0, message = "El valor del campo vacaciones no puede ser negativo")
	private Double vacaciones;

	@Column(name = "interes_cesantias")
	@Min(value = 0, message = "El valor del campo interesCesantias no puede ser negativo")
	private Double interesCesantias;

	@Column(name = "estado")
	private String estado;

	private Double redondeo(Double pValor) {
		return Math.round(pValor * Math.pow(10, 0)) / Math.pow(10, 0);
	}

	public Double getPrima() {
		int horasTrabajadas = this.detalleNomina.getRegistroHoras().getHorasTrabajadas();
		Double devMenosNoSalariales = this.detalleNomina.getDevengadoMenosNoSalariales();
		return redondeo((devMenosNoSalariales * horasTrabajadas) / (360 * 8));
	}

	public Double getCesantias() {
		int horasTrabajadas = this.detalleNomina.getRegistroHoras().getHorasTrabajadas();
		Double devMenosNoSalariales = this.detalleNomina.getDevengadoMenosNoSalariales();
		return redondeo((devMenosNoSalariales * horasTrabajadas) / (360 * 8));
	}

	public Double getVacaciones() {
		int horasTrabajadas = this.detalleNomina.getRegistroHoras().getHorasTrabajadas();
		Double devMenosNoSalariales = this.detalleNomina.getDevengadoMenosNoSalariales();
		return redondeo((devMenosNoSalariales * horasTrabajadas) / (720 * 8));
	}

	/*
	 * public Double getInteresCesantias(){ int horasTrabajadas=
	 * this.detalleNomina.getRegistroHoras().getHorasTrabajadas(); return
	 * redondeo(/(horasTrabajadas/(360*8))); }
	 */

}
