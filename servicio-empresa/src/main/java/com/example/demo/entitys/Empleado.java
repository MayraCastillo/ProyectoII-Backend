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
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
	@Positive(message = "El valor del campo numeroDocumento no puede ser negativo")
	private Long numeroDocumento;

	@NotNull(message = "El campo municipio no puede ser nulo")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mun_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Municipio municipio;

	@OneToMany(mappedBy = "contratoPk.empleado", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Contrato> listaContratos;

	@Column(name = "nombres")
	@NotEmpty(message = "El campo nombres no puede ser vacio")
	private String nombres;

	@Column(name = "apellidos")
	@NotEmpty(message = "El campo apellidos no puede ser vacio")
	private String apellidos;

	@Column(name = "fecha_nacimiento")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private Date fechaNacimiento;

	@Column(name = "tipo_documento")
	@NotEmpty(message = "El campo tipoDocumento no puede ser vacio")
	private String tipoDocumento;

	@Column(name = "telefono")
	@NotEmpty(message = "El campo telefono no puede ser vacio")
	@Positive(message = "El valor del campo telefono no puede ser negativo")
	@Size(min = 7,max = 10,message = "El numero de telefono deber ser mayor a 6 y menor a 11")
	private String telefono;

	@Column(name = "direccion")
	@NotEmpty(message = "El campo direccion no puede ser vacio")
	private String direccion;

	@Column(name = "estado")
	@NotEmpty(message = "El campo estado no puede ser vacio")
	private String estado;

	@Column(name = "correo", unique = true)
	@Email(message = "Formato email inconrrecto")
	@NotEmpty(message = "El campo correo no puede ser vacio")
	private String correo;

	private static final long serialVersionUID = -52362901760841182L;

}
