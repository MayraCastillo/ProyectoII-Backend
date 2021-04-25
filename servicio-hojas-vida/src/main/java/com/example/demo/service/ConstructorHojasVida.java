package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.DTO.EstudioDTO;
import com.example.demo.DTO.ExperienciaLaboralDTO;
import com.example.demo.DTO.HojaVidaDTO;
import com.example.demo.DTO.ReferenciaFamiliarDTO;
import com.example.demo.DTO.ReferenciaPersonalDTO;
import com.example.demo.entity.EmpresaExterna;
import com.example.demo.entity.Estudio;
import com.example.demo.entity.ExperienciaLaboral;
import com.example.demo.entity.HojaVida;
import com.example.demo.entity.InstitucionEducativa;
import com.example.demo.entity.ReferenciaFamiliar;
import com.example.demo.entity.ReferenciaPersonal;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Esta clase se encarga de crear hojas de vida en sus diferentes formatos, como entidades o DTO
 * se usa con el objetivo de no exponer directamente las entidades en la API, a cambio, se usa una 
 * clase especifica para transferir datos, con el formato deseado y exponiendo solo los atributos 
 * deseados
 * 
 * @author Ruben
 *
 */


@Component
@Data
@NoArgsConstructor //@AllArgsConstructor // @Builder
public class ConstructorHojasVida {
	
	// hoja de vida DTO a entidad ---------------------------------------------------------
	
	public HojaVida crearEntidadHojaVida(HojaVidaDTO pHojaVidaDTO) {
		HojaVida nuevaHV = new HojaVida();
		
		nuevaHV.setNumeroDocumento(pHojaVidaDTO.getNumeroDocumento());
		nuevaHV.setTipoDocumento(pHojaVidaDTO.getTipoDocumento());
		nuevaHV.setNombres(pHojaVidaDTO.getNombres());
		nuevaHV.setApellidos(pHojaVidaDTO.getApellidos());
		nuevaHV.setTelefono(pHojaVidaDTO.getTelefono());
		nuevaHV.setCorreo(pHojaVidaDTO.getCorreo());
		nuevaHV.setMunicipioId(pHojaVidaDTO.getMunicipioId());
		nuevaHV.setDireccion(pHojaVidaDTO.getDireccion());
		nuevaHV.setCalificacion(pHojaVidaDTO.getCalificacion());
		//nuevaHV.setNitEmpresa(pHojaVidaDTO.getNitEmpresa());
		//nuevaHV.setEstadoPersona(pHojaVidaDTO.getEstadoPersona());
		
		//this.construirReferenciasFamiliares(nuevaHV, pHojaVidaDTO);	
		//this.construirReferenciasPersonales(nuevaHV, pHojaVidaDTO);
		//this.construirEstudios(nuevaHV, pHojaVidaDTO);
		//this.construirExperienciasLaborales(nuevaHV, pHojaVidaDTO);
		
		return nuevaHV;
	}
	
	public List<ReferenciaFamiliar> construirReferenciasFamiliares(HojaVidaDTO pHojaVidaDTO) {
		List<ReferenciaFamiliar> referencias = new ArrayList<ReferenciaFamiliar>();
		
		if(pHojaVidaDTO.getReferenciasFamiliares() != null) {
			for(ReferenciaFamiliarDTO datosRef : pHojaVidaDTO.getReferenciasFamiliares()) {
				if(this.validarReferenciaFamiliarDTO(datosRef)) {
					referencias.add(this.crearReferenciaFamiliar(datosRef, pHojaVidaDTO));
				}
			}			
		}
		
		return referencias;
	}
	private ReferenciaFamiliar crearReferenciaFamiliar(ReferenciaFamiliarDTO pReferencia, HojaVidaDTO pHojaVida) {
		ReferenciaFamiliar referencia = new ReferenciaFamiliar();
		
		referencia.setReferenciaId(pReferencia.getReferenciaId());
		referencia.setNombres(pReferencia.getNombres());
		referencia.setApellidos(pReferencia.getApellidos());
		referencia.setTelefono(pReferencia.getTelefono());
		referencia.setParentesco(pReferencia.getParentesco());
		referencia.setNumeroDocumento(pHojaVida.getNumeroDocumento());
		
		return referencia;
	}
	
	public List<ReferenciaPersonal> construirReferenciasPersonales(HojaVidaDTO pHojaVidaDTO) {
		List<ReferenciaPersonal> referencias = new ArrayList<ReferenciaPersonal>();
		
		if(pHojaVidaDTO.getReferenciasPersonales() != null) {
			for(ReferenciaPersonalDTO datosRef : pHojaVidaDTO.getReferenciasPersonales()) {
				if(this.validarReferenciaPersonalDTO(datosRef)) {
					referencias.add(this.crearReferenciaPersonal(datosRef, pHojaVidaDTO));
				}
			}			
		}
		
		return referencias;
	}
	private ReferenciaPersonal crearReferenciaPersonal(ReferenciaPersonalDTO pReferencia, HojaVidaDTO pHojaVida) {
		ReferenciaPersonal referencia = new ReferenciaPersonal();
		
		referencia.setReferenciaId(pReferencia.getReferenciaId());
		referencia.setNombres(pReferencia.getNombres());
		referencia.setApellidos(pReferencia.getApellidos());
		referencia.setTelefono(pReferencia.getTelefono());
		referencia.setNumeroDocumento(pHojaVida.getNumeroDocumento());
		
		return referencia;
	}
	
	public List<ExperienciaLaboral> construirExperienciasLaborales(HojaVidaDTO pHojaVidaDTO) {
		List<ExperienciaLaboral> experiencias = new ArrayList<ExperienciaLaboral>();
		
		if(pHojaVidaDTO.getExperienciasLaborales() != null) {
			for(ExperienciaLaboralDTO datos : pHojaVidaDTO.getExperienciasLaborales()) {
				if(this.validarExperienciaDTO(datos)) {
					experiencias.add(this.crearExperienciaLaboral(datos, pHojaVidaDTO));
				}
			}			
		}
		
		return experiencias;
	}
	private ExperienciaLaboral crearExperienciaLaboral(ExperienciaLaboralDTO pExperiencia, HojaVidaDTO pHojaVida) {
		ExperienciaLaboral exp = new ExperienciaLaboral();
		
		exp.setExpId(pExperiencia.getExpId());
		exp.setCargo(pExperiencia.getCargoEmpresa());
		exp.setCalificacion(pExperiencia.getCalificacion());
		exp.setTiempo(pExperiencia.getTiempo());
		exp.setNumeroDocumento(pHojaVida.getNumeroDocumento());
		
		EmpresaExterna ee = new EmpresaExterna();
		
		ee.setNombre(pExperiencia.getNombreEmpresa());
		ee.setContacto(pExperiencia.getContacto());
		ee.setTelefono(pExperiencia.getTelefonoEmpresa());
		
		exp.setEmpresaExterna(ee);
		
		return exp;
	}
	
	public List<Estudio> construirEstudios(HojaVidaDTO pHojaVidaDTO) {
		List<Estudio> estudios = new ArrayList<Estudio>();
		
		if(pHojaVidaDTO.getEstudios() != null) {
			for(EstudioDTO datos : pHojaVidaDTO.getEstudios()) {
				if(this.validarEstudioDTO(datos)) {
					estudios.add(this.crearEstudio(datos, pHojaVidaDTO));
				}
			}			
		}
		
		return estudios;
	}
	private Estudio crearEstudio(EstudioDTO pEstudio, HojaVidaDTO pHojaVida) {
		Estudio estudio = new Estudio();
		
		estudio.setEstudioId(pEstudio.getEstudioId());
		estudio.setNombreTitulo(pEstudio.getNombreTitulo());
		estudio.setCalificacion(pEstudio.getCalificacion());
		estudio.setTipo(pEstudio.getTipo());
		estudio.setTiempo(pEstudio.getTiempo());
		estudio.setNumeroDocumento(pHojaVida.getNumeroDocumento());
		
		InstitucionEducativa inst = new InstitucionEducativa();				
		inst.setNombre(pEstudio.getEntidad());
		
		estudio.setInstitucionEducativa(inst);
		
		return estudio;
	}
	
	// ------------------------------------------------------------------------------------
	
	// hoja de vida entidad a DTO ---------------------------------------------------------
	
	public List<HojaVidaDTO> construirHojasVidaDTO(List<HojaVida> pEntidadesHV){
		List<HojaVidaDTO> hojasVidaDTO = new ArrayList<HojaVidaDTO>();
		
		if(pEntidadesHV != null) {
			for(HojaVida hv : pEntidadesHV) {
				hojasVidaDTO.add(this.construirHojaVidaDTO(hv));
			}
		}
		
		return hojasVidaDTO;
	}
	
	public HojaVidaDTO construirHojaVidaDTO(HojaVida pHojaVida) {
		HojaVidaDTO nuevaHojaVidaDTO = new HojaVidaDTO();
		
		if(pHojaVida != null) {
			nuevaHojaVidaDTO.setApellidos(pHojaVida.getApellidos());
			nuevaHojaVidaDTO.setCalificacion(pHojaVida.getCalificacion());
			nuevaHojaVidaDTO.setCorreo(pHojaVida.getCorreo());
			nuevaHojaVidaDTO.setDireccion(pHojaVida.getDireccion());
			//nuevaHojaVidaDTO.setEstadoPersona(pHojaVida.getEstadoPersona());
			//nuevaHojaVidaDTO.setNitEmpresa(pHojaVida.getNitEmpresa());
			nuevaHojaVidaDTO.setNombres(pHojaVida.getNombres());
			nuevaHojaVidaDTO.setNumeroDocumento(pHojaVida.getNumeroDocumento());
			nuevaHojaVidaDTO.setTelefono(pHojaVida.getTelefono());
			nuevaHojaVidaDTO.setTipoDocumento(pHojaVida.getTipoDocumento());
			nuevaHojaVidaDTO.setMunicipioId(pHojaVida.getMunicipioId());
			
			this.agregarReferenciasFamiliaresAHojaVidaDTO(nuevaHojaVidaDTO, pHojaVida.getReferenciasFamiliares());
			this.agregarReferenciasPersonalesAHojaVidaDTO(nuevaHojaVidaDTO, pHojaVida.getReferenciasPersonales());
			this.agregarExperienciasLaboralesAHojaVidaDTO(nuevaHojaVidaDTO, pHojaVida.getExperienciasLaborales());
			this.agregarEstudiosAHojaVidaDTO(nuevaHojaVidaDTO, pHojaVida.getEstudios());
		}
		
		return nuevaHojaVidaDTO;
	}
	
	private void agregarReferenciasFamiliaresAHojaVidaDTO(HojaVidaDTO pHojaVidaDTO, List<ReferenciaFamiliar> pReferencias) {
		List<ReferenciaFamiliarDTO> referencias = new ArrayList<ReferenciaFamiliarDTO>();
		if(pHojaVidaDTO != null && pReferencias != null) {
			for(ReferenciaFamiliar ref : pReferencias) {
				referencias.add(this.crearReferenciaFamiliarDTO(ref));
			}
		}
		pHojaVidaDTO.setReferenciasFamiliares(referencias);
	}
	private ReferenciaFamiliarDTO crearReferenciaFamiliarDTO(ReferenciaFamiliar pReferencia) {
		ReferenciaFamiliarDTO referencia = new ReferenciaFamiliarDTO();
		
		referencia.setReferenciaId(pReferencia.getReferenciaId());
		referencia.setNombres(pReferencia.getNombres());
		referencia.setApellidos(pReferencia.getApellidos());
		referencia.setTelefono(pReferencia.getTelefono());
		referencia.setParentesco(pReferencia.getParentesco());
		
		return referencia;
	}
		
	private void agregarReferenciasPersonalesAHojaVidaDTO(HojaVidaDTO pHojaVidaDTO, List<ReferenciaPersonal> pReferencias) {
		List<ReferenciaPersonalDTO> referencias = new ArrayList<ReferenciaPersonalDTO>();
		if(pHojaVidaDTO != null && pReferencias != null) {
			for(ReferenciaPersonal ref : pReferencias) {
				referencias.add(this.crearReferenciaPersonalDTO(ref));
			}
		}
		pHojaVidaDTO.setReferenciasPersonales(referencias);
	}
	private ReferenciaPersonalDTO crearReferenciaPersonalDTO(ReferenciaPersonal pReferencia) {
		ReferenciaPersonalDTO referencia = new ReferenciaPersonalDTO();
		
		referencia.setReferenciaId(pReferencia.getReferenciaId());
		referencia.setNombres(pReferencia.getNombres());
		referencia.setApellidos(pReferencia.getApellidos());
		referencia.setTelefono(pReferencia.getTelefono());
		
		return referencia;
	}
	
	private void agregarExperienciasLaboralesAHojaVidaDTO(HojaVidaDTO pHojaVidaDTO, List<ExperienciaLaboral> pExperiencias) {
		List<ExperienciaLaboralDTO> experiencias = new ArrayList<ExperienciaLaboralDTO>();
		if(pHojaVidaDTO != null && pExperiencias != null) {
			for(ExperienciaLaboral exp : pExperiencias) {
				experiencias.add(this.crearExperienciaLaboralDTO(exp));
			}
		}
		pHojaVidaDTO.setExperienciasLaborales(experiencias);
	}
	private ExperienciaLaboralDTO crearExperienciaLaboralDTO(ExperienciaLaboral pExperiencia) {
		ExperienciaLaboralDTO experiencia = new ExperienciaLaboralDTO();
		
		experiencia.setExpId(pExperiencia.getExpId());
		experiencia.setCalificacion(pExperiencia.getCalificacion());
		experiencia.setCargoEmpresa(pExperiencia.getCargo());
		experiencia.setTiempo(pExperiencia.getTiempo());
		
		EmpresaExterna ee = pExperiencia.getEmpresaExterna();
		if(ee != null) {
			experiencia.setNombreEmpresa(ee.getNombre());
			experiencia.setContacto(ee.getContacto());
			experiencia.setTelefonoEmpresa(ee.getTelefono());
		}
		
		return experiencia;
	}
		
	private void agregarEstudiosAHojaVidaDTO(HojaVidaDTO pHojaVidaDTO, List<Estudio> pEstudios) {
		List<EstudioDTO> estudios = new ArrayList<EstudioDTO>();
		if(pHojaVidaDTO != null && pEstudios != null) {
			for(Estudio est : pEstudios) {
				estudios.add(this.crearEstudioDTO(est));
			}
		}
		pHojaVidaDTO.setEstudios(estudios);
	}
	private EstudioDTO crearEstudioDTO(Estudio p) {
		EstudioDTO estudio = new EstudioDTO();
		
		estudio.setEstudioId(p.getEstudioId());
		estudio.setCalificacion(p.getCalificacion());
		estudio.setNombreTitulo(p.getNombreTitulo());
		estudio.setTiempo(p.getTiempo());
		estudio.setTipo(p.getTipo());
		
		InstitucionEducativa inst = p.getInstitucionEducativa();
		if(inst != null) {
			estudio.setEntidad(inst.getNombre());
		}
		
		return estudio;
	}
	
	// ------------------------------------------------------------------------------------
	
	// Validaciones -----------------------------------------------------------------------
	
	public boolean validarEstudioDTO(EstudioDTO estudio) {
		if(estudio.getNombreTitulo().isEmpty() ||
				estudio.getTipo().isEmpty() || 
					estudio.getTiempo().isEmpty()) {
			
			return false;
		}
		return true;
	}
	public boolean validarExperienciaDTO(ExperienciaLaboralDTO exp) {
		if(exp.getContacto().isEmpty() || exp.getCargoEmpresa().isEmpty() || exp.getTiempo().isEmpty()) {
			return false;			
		}
		return true;
	}
	public boolean validarReferenciaFamiliarDTO(ReferenciaFamiliarDTO referencia) {
		if(referencia.getNombres().isEmpty() || referencia.getApellidos().isEmpty() || referencia.getParentesco().isEmpty()
				|| referencia.getTelefono().isEmpty()) {
			return false;
		}
		return true;
	}
	public boolean validarReferenciaPersonalDTO(ReferenciaPersonalDTO referencia) {
		if(referencia.getNombres().isEmpty() || referencia.getApellidos().isEmpty() || referencia.getTelefono().isEmpty()) {
			return false;
		}
		return true;
	}
	
	
	// ------------------------------------------------------------------------------------
}
