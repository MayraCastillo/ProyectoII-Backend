package com.example.demo.entitys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleados")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Empleado implements Serializable {
	@Id
	@Column(name = "numero_documento")
	@NotNull
	private Long numeroDocumento;
	
	@NotNull(message = "El campo municipio no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mun_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Municipio municipio;
	
	@NotNull(message = "El campo banco no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banco_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Banco banco;
	
	@OneToMany(mappedBy = "contratoPk.empleado",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Contrato> listaCotratos;
	
	@Column(name = "nombres")
	@NotEmpty
	private String nombres;
	
	@Column(name = "apellidos")
	@NotEmpty
	private String apellidos;
	
	@Column(name = "fecha_nacimiento")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private Date fechaNacimiento;
	
	@Column(name = "tipo_documento")
	@NotEmpty
	private String tipoDocumento;
	
	@Column(name = "telefono")
	@NotEmpty
	private String telefono;
	
	@Column(name = "direccion")
	@NotEmpty
	private String direccion;
	
	@Column(name = "estado")
	@NotEmpty
	private String estado;
	
	@Column(name = "correo",unique = true)
	@Email
	@NotEmpty
	private String correo;

	private static final long serialVersionUID = -52362901760841182L;

}
