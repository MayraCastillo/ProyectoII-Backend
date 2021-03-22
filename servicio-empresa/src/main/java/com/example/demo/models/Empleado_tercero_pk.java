package com.example.demo.models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import com.example.demo.entitys.Empleado;
import com.example.demo.entitys.Tercero;
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
public class Empleado_tercero_pk implements Serializable {

	private static final long serialVersionUID = -8915056491685118435L;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "terc_id",insertable = false,updatable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Tercero tercero;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "numero_documento",insertable = false,updatable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Empleado empleado;

	@Override
	public String toString() {
		return "Empleado_tercero_pk [tercero=" + tercero + ", empleado=" + empleado + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado_tercero_pk other = (Empleado_tercero_pk) obj;
		if (empleado == null) {
			if (other.empleado != null)
				return false;
		} else if (!empleado.equals(other.empleado))
			return false;
		if (tercero == null) {
			if (other.tercero != null)
				return false;
		} else if (!tercero.equals(other.tercero))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empleado == null) ? 0 : empleado.hashCode());
		result = prime * result + ((tercero == null) ? 0 : tercero.hashCode());
		return result;
	}
	
	

}
