package com.example.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SeguridadSocial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seg_social_id")
	private Long seguridaSocialId;

	//@OneToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "nominaId")
	// @NotNull(message = "El valor de la nomina no puede ser nulo")
	//private Nomina nomina;

	@Column(name = "EPS")
	@Min(value = 0, message = "El valor del campo EPS no puede ser negativo")
	// @NotNull(message = "El valor del campo EPS no puede ser nulo")
	private Double EPS;

	@Column(name = "AFP")
	@Min(value = 0, message = "El valor del campo AFP no puede ser negativo")
	// @NotNull(message = "El valor del campo AFP no puede ser nulo")
	private Double AFP;

	@Column(name = "ARL")
	@Min(value = 0, message = "El valor del campo ARL no puede ser negativo")
	// @NotNull(message = "El valor del campo ARL no puede ser nulo")
	private Double ARL;

	@Column(name = "SENA")
	@Min(value = 0, message = "El valor del campo SENA no puede ser negativo")
	// @NotNull(message = "El valor del campo SENA no puede ser nulo")
	private Double SENA;

	@Column(name = "ICBF")
	@Min(value = 0, message = "El valor del campo ICBF no puede ser negativo")
	// @NotNull(message = "El valor del campo ICBF no puede ser nulo")
	private Double ICBF;

	@Column(name = "CCF")
	@Min(value = 0, message = "El valor del campo CCF no puede ser negativo")
	// @NotNull(message = "El valor del campo CCF no puede ser nulo")
	private Double CCF;

	public Double redondeo(Double pValor) {
		return Math.round(pValor * Math.pow(10, 0)) / Math.pow(10, 0);
	}
/*
	public Double getEPS() {
		return redondeo(this.nomina.getDevMenosNoSalMenosAuxTrans() * 0.085);
	}

	public Double getAFP() {
		return redondeo(this.nomina.getDevMenosNoSalMenosAuxTrans() * 0.12);
	}

	public Double getARL() {
		return redondeo(this.ARL);
	}

	public Double getSENA() {
		return redondeo(this.nomina.getDevMenosNoSalMenosAuxTrans() * 0.02);
	}

	public Double getICBF() {
		return redondeo(this.nomina.getDevMenosNoSalMenosAuxTrans() * 0.03);
	}

	public Double getCCF() {
		return redondeo(this.nomina.getDevMenosNoSalMenosAuxTrans() * 0.04);
	}*/

}
