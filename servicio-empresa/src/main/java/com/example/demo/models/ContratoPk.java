package com.example.demo.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.example.demo.entitys.Empleado;
import com.example.demo.entitys.Empresa;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ContratoPk implements Serializable {
	private static final long serialVersionUID = 3087859331724741572L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="numero_documento",insertable = false, updatable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Empleado empleado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nit", insertable = false, updatable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Empresa empresa;
	
	@Column(name = "fecha_inicio_contrato")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private Date fechaInicioContrato;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContratoPk other = (ContratoPk) obj;
		if (empleado == null) {
			if (other.empleado != null)
				return false;
		} else if (!empleado.equals(other.empleado))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (fechaInicioContrato == null) {
			if (other.fechaInicioContrato != null)
				return false;
		} else if (!fechaInicioContrato.equals(other.fechaInicioContrato))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empleado == null) ? 0 : empleado.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((fechaInicioContrato == null) ? 0 : fechaInicioContrato.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "ContratoPk [empleado=" + empleado + ", empresa=" + empresa + ", fechaInicioContrato="
				+ fechaInicioContrato + "]";
	}
	
	


}
