package com.example.demo.entitys;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "terceros")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tercero {

	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "terc_id")
	//private Long terceroId;
	
	@Id
	@Column(name = "nit")
	@Positive(message = "El valor del campo nit no puede ser negativo")
	private Long nit;
	
	@NotNull(message = "El municipio no puede ser nulo")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "mun_id")
	private Municipio municipio;

	@Column(name = "nombre", unique = true)
	@NotEmpty(message = "El campo nombre no puede ser vacio")
	private String nombre;

	@Column(name = "direccion")
	@NotEmpty(message = "El campo direccion no puede ser vacio")
	private String direccion;

	@NotEmpty(message = "El campo correo no puede ser vacio")
	@Column(name = "correo", unique = true)
	@Email(message = "Formato email incorrecto")
	private String correo;

	@NotEmpty(message = "El campo telefono no puede ser vacio")
	@Column(name = "telefono")
	@Size(min = 7,max = 10,message = "El numero de telefono deber ser mayor a 6 y menor a 11")
	@Positive(message = "El mumero de telefono debe ser positivo")
	private String telefono;

	@NotNull(message = "El campo tipoTercero no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_tercero_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private TipoTercero tipoTercero;
	
	@Column(name = "estado")
	@NotEmpty(message = "El campo estado no puede ser vacio")
	private String estado;

	@JsonIgnore
	@OneToMany(mappedBy = "empleadoTeceroPk.tercero", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Empleado_tercero> listaEmp_terceros;

}
