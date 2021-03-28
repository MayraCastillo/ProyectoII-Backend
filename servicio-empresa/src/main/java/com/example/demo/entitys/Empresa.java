package com.example.demo.entitys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empresas")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empresa implements Serializable{
	@Id
	@NotNull
	@Column(name = "nit")
	private Long nit;
	
	@Column(name = "nombre",unique = true)
	@NotEmpty
	private String nombre;
	
	@Column(name = "telefono")
	@NotEmpty
	private String telefono;
	
	@Column(name = "direccion")
	@NotEmpty
	private String direccion;
		
	@Column(name = "frecuencia_pago")
	@NotEmpty
	private String frecuenciaPago;
	
	private static final long serialVersionUID = -9046961404139632023L;

}
