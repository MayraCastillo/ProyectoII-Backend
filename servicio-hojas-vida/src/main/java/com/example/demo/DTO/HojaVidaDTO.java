package com.example.demo.DTO;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class HojaVidaDTO {
	
	// DATOS BASICOS DE LA HOJA DE VIDA ---------------------------------------------
	
	//@Min(1000000)
	//@Max(99999999)
	@NotNull(message = "el numero de documento no puede ser vacio")
	private Long numeroDocumento;
	
	@NotEmpty(message = "el tipo de documento no puede ser vacio")
	private String tipoDocumento;	
	
	@NotEmpty(message = "Los nombres no pueden ser vacios")
	//@Size(min = 1, max = 50)
	private String nombres;
	
	@NotEmpty(message = "Los apellidos no pueden ser vacios")
	//@Size(min = 1, max = 50)
	private String apellidos;
	
	@NotEmpty(message = "El telefono no puede ser vacio")
	//@Size(min = 5, max = 15)
	private String telefono;
	
	@NotEmpty(message = "El correo no puede ser vacio")
	@Email
	private String correo;
	
	@NotEmpty(message = "La direccion no puede ser vacio")
	private String direccion;
	 
	//@Min(0)
	//@Max(10)
	private Double calificacion;
	
	
	// DATOS DE LA EMPRESA A LA QUE PERTENECE LA HOJA DE VIDA ------------------------
	
	//@NotNull
	private String nitEmpresa;
	
	//@NotNull
	// TODO @Pattern(regexp = "PROSPECTO\\CONTRATADO")
    private String estadoPersona;
	
    
	// DATOS DE LA UBICACION DE LA PERSONA -------------------------------------------
    
    @NotNull(message = "El id del municipio no puede ser vacio") 
	private Long municipioId;
    
    private Long departamentoId;
    private Long paisId;
    
    private String nombreMunicipio;
    private String nombreDepartamento;
    private String nombrePais;
    
    
    // ARREGLOS 
	
	@Valid
	private List<ReferenciaFamiliarDTO> referenciasFamiliares;
	
	@Valid
	private List<ReferenciaPersonalDTO> referenciasPersonales;
	
	@Valid
	private List<ExperienciaLaboralDTO> experienciasLaborales;
	
	@Valid
	private List<EstudioDTO> estudios;
	
	
}
