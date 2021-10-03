package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import com.example.demo.DAO.HojasVidaDAO;
import com.example.demo.DTO.HojaVidaDTO;
import com.example.demo.entity.HojaVida;

/**
 * Clase que implementa los servicios defindos en la interfaz, concernientes a la 
 * gestion de las hojas de vida
 * 
 * @author Ruben
 *
 */


@Service
@RequiredArgsConstructor
@Data
//@AllArgsConstructor// @NoArgsConstructor @Builder
public class ServicioHojasVidaImpl implements IntServicioHojasVida{	
		
	/**
	 * Referencia a los servicios disponibles sobre las referencias familiares, 
	 * utilizando inyeccion de dependencias
	 */
	@Autowired
    private IntServicioReferenciasFamiliares miServicioReferenciasFamiliares;
	
	/**
	 * Referencia a los servicios disponibles sobre las referencias familiares, 
	 * utilizando inyeccion de dependencias
	 */
	@Autowired
    private IntServicioReferenciasPersonales miServicioReferenciasPersonales;
	
	/**
	 * Referencia a los servicios disponibles sobre las experiencias laborales de una hv, 
	 * utilizando inyeccion de dependencias
	 */
	@Autowired
    private IntServicioExperienciasLaborales miServicioExperienciasLaborales;
		
	/**
	 * Referencia a los servicios disponibles sobre los estudios de una hv, 
	 * utilizando inyeccion de dependencias
	 */
	@Autowired
    private IntServicioEstudios miServicioEstudios;
	
	/**
	 * Se encarga de crear hojas de vida en sus diferentes formatos, como entidades o DTO
	 */
	@Autowired
	private ConstructorHojasVida constructorHV;
	
	/**
	 * Se encarga de la gestion de municipios, departamentos y paises
	 */
	@Autowired
	private IntServicioUbicacion servicioUbicacion;
	
	/**
	 * Se encarga de la gestion de los servicios generales de una empresa, como el estado de un empleado
	 * en la empresa
	 */
	@Autowired
	private IntServicioEmpresa servicioEmpresa;
	
	/**
	 * Abstraccion del medio de almacenamiento de datos, de las entidades mapeadas en la base de datos
	 * Spring boot, mediante inyeccion de dependencias, logra simplificar la comunicacion con
	 * la base de datos (en este caso, sobre las hojas de vida)
	 */
	@Autowired
	private HojasVidaDAO miRepositorioHojasVida;		
	
	
	@Override
	public List<HojaVidaDTO> listarHojasVida() {		
		// Busca todas las hojas de vida del sistema
		List<HojaVida> hojasVidaEncontradas = miRepositorioHojasVida.findAll();
		
		// Convertir las entidades a formato DTO
		List<HojaVidaDTO> hojasVidaDTO = constructorHV.construirHojasVidaDTO(hojasVidaEncontradas);
		
		// Agrega la in formacion de los municipios, proveniente del microservicio de la empresa
		this.servicioUbicacion.agregarUbicacionAHojasVida(hojasVidaDTO);
		
		// Agregar el estado del dueño de la hoja de vida en la empresa especificada
		// TODO por ahora, solo para la empresa con nit 302228024
		this.agregarEstadoAHojasVidaDTO(hojasVidaDTO, "302228024");
		
		return hojasVidaDTO;
	}
	
	@Override
	public HojaVidaDTO buscarHojaVidaPorId(Long pHojaVidaId) {
		HojaVidaDTO hvDTOEncontrada = null;
		if(pHojaVidaId != null) {
			HojaVida hvEncontrada = this.miRepositorioHojasVida.findById(pHojaVidaId).orElse(null);
			
			// Agregar informacion de la ubicacion
			if(hvEncontrada != null) {
				hvDTOEncontrada = this.constructorHV.construirHojaVidaDTO(hvEncontrada);
				this.servicioUbicacion.agregarUbicacionAHojaVida(hvDTOEncontrada);
				
				// Agregar el estado del dueño de la hoja de vida en la empresa especificada
				// TODO por ahora, solo para la empresa con nit 302228024
				this.agregarEstadoAHojaVidaDTO(hvDTOEncontrada, "302228024");
			}
		}
		return hvDTOEncontrada;
	}
	
	@Override
	public HojaVidaDTO buscarHojaVidaPorCorreo(String pCorreo) {
		HojaVidaDTO hvDTOEncontrada = null;
		if(pCorreo != null) {
			HojaVida hvEncontrada = this.miRepositorioHojasVida.findByCorreo(pCorreo);
			
			// Agregar informacion de la ubicacion
			if(hvEncontrada != null) {
				hvDTOEncontrada = this.constructorHV.construirHojaVidaDTO(hvEncontrada);
				this.servicioUbicacion.agregarUbicacionAHojaVida(hvDTOEncontrada);	
				
				// Agregar el estado del dueño de la hoja de vida en la empresa especificada
				// TODO por ahora, solo para la empresa con nit 302228024
				this.agregarEstadoAHojaVidaDTO(hvDTOEncontrada, "302228024");
			}
		}
		return hvDTOEncontrada;
	}

	@Override
	public HojaVidaDTO registrarHojaVida(HojaVidaDTO pHojaVidaDTO) {
		
		// El id del municipio es valido?
		if( ! this.servicioUbicacion.validarUbicacion(pHojaVidaDTO.getMunicipioId())) {
			return null;
		}
		
		// Convertir hoja de vida del formato DTO a entidad
		HojaVida hojaVidaRecibida = this.constructorHV.crearEntidadHojaVida(pHojaVidaDTO);	
		
		//Guardar hoja de vida en la base de datos
		HojaVida nuevaHojaVida = this.guardarNuevaHojaVida(hojaVidaRecibida);
		
		// Valida si se pudo guardar la hoja de vida
		if(nuevaHojaVida != null) {
			
			// Guarda los arreglos de la hoja de vida
			// Se debe convertir cada arreglo de formato DTO a Entidad, usando el constructor
			this.miServicioReferenciasFamiliares.guardarReferenciasFamiliares(constructorHV.construirReferenciasFamiliares(pHojaVidaDTO));
			this.miServicioReferenciasPersonales.guardarReferenciasPersonales(constructorHV.construirReferenciasPersonales(pHojaVidaDTO));
			this.miServicioExperienciasLaborales.guardarExperienciasLaborales(constructorHV.construirExperienciasLaborales(pHojaVidaDTO));
			this.miServicioEstudios.guardarEstudios(constructorHV.construirEstudios(pHojaVidaDTO));
		}
		// TODO revisar
		else {
			pHojaVidaDTO = null;
		}
		
		return pHojaVidaDTO;
	}
	
	@Override
	public HojaVidaDTO actualizarHojaVida(HojaVidaDTO pHojaVidaDTO) {
		
		// El id del municipio es valido?
		if( ! this.servicioUbicacion.validarUbicacion(pHojaVidaDTO.getMunicipioId())) {
			return null;
		}	
		
		if( ! this.miRepositorioHojasVida.existsById(pHojaVidaDTO.getNumeroDocumento())) {
			System.out.println("La hoja de vida no existe");
			return null;
		}
		
		// Convertir hoja de vida del formato DTO a entidad
		HojaVida hojaVidaRecibida = this.constructorHV.crearEntidadHojaVida(pHojaVidaDTO);			
					
		// Convertir los arreglosd e la hoja de vida a entidades
		hojaVidaRecibida.setReferenciasFamiliares(constructorHV.construirReferenciasFamiliares(pHojaVidaDTO));
		hojaVidaRecibida.setReferenciasPersonales(constructorHV.construirReferenciasPersonales(pHojaVidaDTO));
		hojaVidaRecibida.setExperienciasLaborales(constructorHV.construirExperienciasLaborales(pHojaVidaDTO));
		hojaVidaRecibida.setEstudios(constructorHV.construirEstudios(pHojaVidaDTO));
		
		// eliminar los items de los arreglos que no se enviaron en el actualizar, pero que existen en la base de datos
		this.miServicioReferenciasFamiliares.eliminarFaltantes(hojaVidaRecibida.getReferenciasFamiliares(), hojaVidaRecibida.getNumeroDocumento());
		this.miServicioReferenciasPersonales.eliminarFaltantes(hojaVidaRecibida.getReferenciasPersonales(), hojaVidaRecibida.getNumeroDocumento());
		this.miServicioExperienciasLaborales.eliminarFaltantes(hojaVidaRecibida.getExperienciasLaborales(), hojaVidaRecibida.getNumeroDocumento());
		this.miServicioEstudios.eliminarFaltantes(hojaVidaRecibida.getEstudios(), hojaVidaRecibida.getNumeroDocumento());
		
		// Agregar entidades de las empresas externas de las experiencias
		this.miServicioExperienciasLaborales.agregarEntidades(hojaVidaRecibida.getExperienciasLaborales());
		
		// Agregar instituciones educativas de los estudios proporcionados
		this.miServicioEstudios.agregarInstituciones(hojaVidaRecibida.getEstudios());
		
		// Guardar hoja de vida en la base de datos
		if(this.guardarHojaVidaExistente(hojaVidaRecibida) != null) {
			System.out.println("\nDiferente de nulo\n");
		}
		
		return pHojaVidaDTO;
	}

	@Override
	public HojaVidaDTO eliminarHojaVida(Long pNumeroDocumento) {
		HojaVida hv = this.miRepositorioHojasVida.findById(pNumeroDocumento).orElse(null);
		
		if(hv != null) {
			// validar que no se elimine una hoja de vida de empleado que no sea prospecto
			String estado = this.servicioEmpresa.consultarEstadoPersona(hv.getNumeroDocumento(), "302228024");
			if( ! estado.equals("PROSPECTO")) {
				System.out.println("Error al eliminar hv, la persona debe ser un prospecto para poder eliminar");
				return null;
			}
			
			if(hv.getReferenciasFamiliares() != null) {
				System.out.println("\nEliminando referencias familiares");
				this.miServicioReferenciasFamiliares.eliminarReferenciasFamiliares(hv.getReferenciasFamiliares());
			}
			
			if(hv.getReferenciasPersonales() != null) {
				System.out.println("\nEliminando referencias personales");
				this.miServicioReferenciasPersonales.eliminarReferenciasPersonaleses(hv.getReferenciasPersonales());
			}
			
			if(hv.getExperienciasLaborales() != null) {
				System.out.println("\nEliminando experiencias");
				this.miServicioExperienciasLaborales.eliminarExperienciasLaborales(hv.getExperienciasLaborales());
			}
			
			if(hv.getEstudios() != null) {
				System.out.println("\nEliminando estudios");
				this.miServicioEstudios.eliminarEstudios(hv.getEstudios());
			}
			//this.miRepositorioHojasVida.flush();
			
			
			System.out.println("\nEliminando hoja de vida --> \n" + this.miRepositorioHojasVida.findById(hv.getNumeroDocumento()).orElse(null));			
			this.miRepositorioHojasVida.deleteById(pNumeroDocumento);
			
			return this.constructorHV.construirHojaVidaDTO(hv);
		}
		
		return null;
	}
	
	
	//====== Metodos privados ===========================================================================
		
	private HojaVida guardarNuevaHojaVida(HojaVida pHojaVida) {		
			
			
		// validamos si existe en la base de datos
		HojaVida hojaVidaEncontrada = miRepositorioHojasVida.findById(pHojaVida.getNumeroDocumento()).orElse(null);
		
		if(hojaVidaEncontrada == null) {
			// Guardar la hoja de vida con sus atributos basicos en la BD
			return miRepositorioHojasVida.save(pHojaVida);	
		}		
		
		return null;				
	}
	private HojaVida guardarHojaVidaExistente(HojaVida pHojaVida) {				
			
		// validamos si existe en la base de datos
		HojaVida hojaVidaEncontrada = miRepositorioHojasVida.findById(pHojaVida.getNumeroDocumento()).orElse(null);
		
		if(hojaVidaEncontrada != null) {
			// Guardar la hoja de vida con sus atributos basicos en la BD
			//return miRepositorioHojasVida.saveAndFlush(pHojaVida);
			return miRepositorioHojasVida.save(pHojaVida);
		}		
		
		return null;				
	}

	private void agregarEstadoAHojasVidaDTO(List<HojaVidaDTO> pHojasVida, String pNitEmpresa) {
		if(pHojasVida != null && pNitEmpresa != null) {
			for(HojaVidaDTO hvDTO : pHojasVida) {
				this.agregarEstadoAHojaVidaDTO(hvDTO, pNitEmpresa);
			}
		}
	}
	private void agregarEstadoAHojaVidaDTO(HojaVidaDTO pHojaVida, String pNitEmpresa) {
		String estado = this.servicioEmpresa.consultarEstadoPersona(pHojaVida.getNumeroDocumento(), pNitEmpresa);
		pHojaVida.setEstadoPersona(estado);
	}

	
	
	
	
}
