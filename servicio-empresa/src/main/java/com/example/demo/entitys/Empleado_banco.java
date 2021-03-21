package com.example.demo.entitys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.example.demo.models.Empleado_banco_pk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empleados_bancos")
public class Empleado_banco implements Serializable {

	private static final long serialVersionUID = -5036324325601200467L;
	@EmbeddedId
	private Empleado_banco_pk empleado_banco_pk;
	
	@Column(name = "tipo_cuenta")
	@NotEmpty
	private String tipoCuenta;

}
