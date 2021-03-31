package com.example.demo.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;

import com.example.demo.entity.EmpresaExterna;
import com.example.demo.entity.Estudio;
import com.example.demo.entity.ExperienciaLaboral;
import com.example.demo.entity.HojaVida;
import com.example.demo.entity.InstitucionEducativa;
import com.example.demo.entity.ReferenciaFamiliar;
import com.example.demo.entity.ReferenciaPersonal;

import lombok.Data;

@Data
public class DTOHojaVida {
	
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
	
	@NotNull(message = "El id del municipio no puede ser vacio") 
	private Long municipioId;
	
	@NotEmpty(message = "La direccion no puede ser vacio")
	private String direccion;
	 
	//@Min(0)
	//@Max(10)
	private Double calificacion;
	
	//@NotNull
	private String nitEmpresa;
	
	//@NotNull
	// TODO @Pattern(regexp = "PROSPECTO\\CONTRATADO")
    private String estadoPersona;
	
	
	
	@Valid
	private List<DTOReferenciaFamiliar> referenciasFamiliares;
	
	@Valid
	private List<DTOReferenciaPersonal> referenciasPersonales;
	
	@Valid
	private List<DTOExperienciaLaboral> experienciasLaborales;
	
	@Valid
	private List<DTOEstudio> estudios;
	
	
	public HojaVida construirEntidadHojaVida() {
		HojaVida nuevaHV = new HojaVida();
		
		nuevaHV.setNumeroDocumento(this.numeroDocumento);
		nuevaHV.setTipoDocumento(this.tipoDocumento);
		nuevaHV.setNombres(this.nombres);
		nuevaHV.setApellidos(this.apellidos);
		nuevaHV.setTelefono(this.telefono);
		nuevaHV.setCorreo(this.correo);
		nuevaHV.setMunicipioId(this.municipioId);
		nuevaHV.setDireccion(this.direccion);
		nuevaHV.setCalificacion(this.calificacion);
		nuevaHV.setNitEmpresa(this.nitEmpresa);
		nuevaHV.setEstadoPersona(this.estadoPersona);
		
		this.construirReferenciasFamiliares(nuevaHV);	
		this.construirReferenciasPersonales(nuevaHV);
		this.construirEstudios(nuevaHV);
		this.construirExperienciasLaborales(nuevaHV);
		
		return nuevaHV;
	}
	
	private void construirReferenciasFamiliares(HojaVida hv) {
		List<ReferenciaFamiliar> referencias = new ArrayList<ReferenciaFamiliar>();
		
		if(this.referenciasFamiliares != null) {
			for(DTOReferenciaFamiliar datosRef : this.referenciasFamiliares) {
				referencias.add(this.crearReferenciaFamiliar(datosRef));
			}			
		}
		
		hv.setReferenciasFamiliares(referencias);
	}
	private ReferenciaFamiliar crearReferenciaFamiliar(DTOReferenciaFamiliar pReferencia) {
		ReferenciaFamiliar referencia = new ReferenciaFamiliar();
		
		referencia.setNombres(pReferencia.getNombres());
		referencia.setApellidos(pReferencia.getApellidos());
		referencia.setTelefono(pReferencia.getTelefono());
		referencia.setParentesco(pReferencia.getParentesco());
		
		return referencia;
	}
	
	private void construirReferenciasPersonales(HojaVida hv) {
		List<ReferenciaPersonal> referencias = new ArrayList<ReferenciaPersonal>();
		
		if(this.referenciasPersonales != null) {
			for(DTOReferenciaPersonal datosRef : this.referenciasPersonales) {
				referencias.add(this.crearReferenciaPersonal(datosRef));
			}			
		}
		
		hv.setReferenciasPersonales(referencias);
	}
	private ReferenciaPersonal crearReferenciaPersonal(DTOReferenciaPersonal pReferencia) {
		ReferenciaPersonal referencia = new ReferenciaPersonal();
		
		referencia.setNombres(pReferencia.getNombres());
		referencia.setApellidos(pReferencia.getApellidos());
		referencia.setTelefono(pReferencia.getTelefono());
		
		return referencia;
	}
	
	private void construirExperienciasLaborales(HojaVida hv) {
		List<ExperienciaLaboral> experiencias = new ArrayList<ExperienciaLaboral>();
		
		if(this.experienciasLaborales != null) {
			for(DTOExperienciaLaboral datos : this.experienciasLaborales) {
				experiencias.add(this.crearExperienciaLaboral(datos));
			}			
		}
		
		hv.setExperienciasLaborales(experiencias);
	}
	private ExperienciaLaboral crearExperienciaLaboral(DTOExperienciaLaboral pExperiencia) {
		ExperienciaLaboral exp = new ExperienciaLaboral();
		
		exp.setCargo(pExperiencia.getCargoEmpresa());
		exp.setCalificacion(pExperiencia.getCalificacion());
		exp.setTiempo(pExperiencia.getTiempo());
		
		EmpresaExterna ee = new EmpresaExterna();
		ee.setNombre(pExperiencia.getNombreEmpresa());
		ee.setContacto(pExperiencia.getContacto());
		ee.setTelefono(pExperiencia.getTelefonoEmpresa());
		
		exp.setEmpresaExterna(ee);
		
		return exp;
	}
	
	private void construirEstudios(HojaVida hv) {
		List<Estudio> estudios = new ArrayList<Estudio>();
		
		if(this.estudios != null) {
			for(DTOEstudio datos : this.estudios) {
				estudios.add(this.crearEstudio(datos));
			}			
		}
		
		hv.setEstudios(estudios);
	}
	private Estudio crearEstudio(DTOEstudio pEstudio) {
		Estudio estudio = new Estudio();
		
		estudio.setNombreTitulo(pEstudio.getNombreTitulo());
		estudio.setCalificacion(pEstudio.getCalificacion());
		estudio.setTipo(pEstudio.getTipo());
		estudio.setTiempo(pEstudio.getTiempo());
		
		InstitucionEducativa inst = new InstitucionEducativa();
		inst.setNombre(pEstudio.getEntidad());
		
		estudio.setInstitucionEducativa(inst);
		
		return estudio;
	}
	
	
}
