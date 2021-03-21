package com.example.demo.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import com.example.demo.entitys.Banco;
import com.example.demo.entitys.Empleado;
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
public class Empleado_banco_pk implements Serializable{

	private static final long serialVersionUID = -4272417946373769628L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="numero_documento",insertable = false, updatable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
	private Empleado empleado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="banco_id",insertable = false, updatable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
	private Banco banco;
	
	@Column(name = "numero_cuenta",insertable = false, updatable = false)
	@NotEmpty
	private String numeroCuenta;

	@Override
	public String toString() {
		return "Empleado_banco_pk [empleado=" + empleado + ", banco=" + banco + ", numeroCuenta=" + numeroCuenta + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado_banco_pk other = (Empleado_banco_pk) obj;
		if (banco == null) {
			if (other.banco != null)
				return false;
		} else if (!banco.equals(other.banco))
			return false;
		if (empleado == null) {
			if (other.empleado != null)
				return false;
		} else if (!empleado.equals(other.empleado))
			return false;
		if (numeroCuenta == null) {
			if (other.numeroCuenta != null)
				return false;
		} else if (!numeroCuenta.equals(other.numeroCuenta))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((banco == null) ? 0 : banco.hashCode());
		result = prime * result + ((empleado == null) ? 0 : empleado.hashCode());
		result = prime * result + ((numeroCuenta == null) ? 0 : numeroCuenta.hashCode());
		return result;
	}
	

}
