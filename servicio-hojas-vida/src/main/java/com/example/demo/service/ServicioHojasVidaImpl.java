package com.example.demo.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.example.demo.DAO.EstudiosDAO;
import com.example.demo.DAO.ExperienciasLaboralesDAO;
import com.example.demo.DAO.HojasVidaDAO;
import com.example.demo.DAO.ReferenciasFamiliaresDAO;
import com.example.demo.DAO.ReferenciasPersonalesDAO;
import com.example.demo.DTO.HojaVidaDTO;
import com.example.demo.entity.EmpresaExterna;
import com.example.demo.entity.Estudio;
import com.example.demo.entity.ExperienciaLaboral;
import com.example.demo.entity.HojaVida;
import com.example.demo.entity.InstitucionEducativa;
import com.example.demo.entity.ReferenciaFamiliar;
import com.example.demo.entity.ReferenciaPersonal;

/**
 * Clase que implementa los servicios defindos en la interfaz, concernientes a la 
 * gestion de las hojas de vida
 * 
 * @author Ruben
 *
 */


@Service
@RequiredArgsConstructor
public class ServicioHojasVidaImpl implements ServicioHojasVida{

	/**
	 * Referencia a los servicios disponibles sobre las empresas externas, 
	 * utilizando inyeccion de dependencias, automatizado por el framework con la decoracion @Autowired
	 */
	@Autowired
    private ServicioEmpresasExternas miServicioEmpresasExternas;	
	
	/**
	 * Referencia a los servicios disponibles sobre los estudios cursados, 
	 * utilizando inyeccion de dependencias, automatizado por el framework con la decoracion @Autowired
	 */
	@Autowired
    private ServicioInstitucionesEducativas miServicioInstitucionesEducativas;	
		
	/**
	 * Se encarga de crear hojas de vida en sus diferentes formatos, como entidades o DTO
	 */
	@Autowired
	private ConstructorHojasVida constructorHV;
	
	/**
	 * Se encarga de la gestion de municipios, departamentos y paises
	 */
	@Autowired
	private ServicioUbicacion servicioUbicacion;
	
	
	/**
	 * Abstraccion del medio de almacenamiento de datos, de las entidades mapeadas en la base de datos
	 * Spring boot, mediante inyeccion de dependencias, logra simplificar la comunicacion con
	 * la base de datos
	 */
	private final HojasVidaDAO miRepositorioHojasVida;		
	private final ReferenciasFamiliaresDAO miRepositorioReferenciasFamiliares;
	private final ReferenciasPersonalesDAO miRepositorioReferenciasPersonales;
	private final ExperienciasLaboralesDAO miRepositorioExperienciasLaborales;
	private final EstudiosDAO miRepositorioEstudios;
		
	
	@Override
	public List<HojaVidaDTO> listarHojasVida() {		
		// Busca todas las hojas de vida del sistema
		List<HojaVida> hojasVidaEncontradas = miRepositorioHojasVida.findAll();
		
		// Convertir las entidades a formato DTO
		List<HojaVidaDTO> hojasVidaDTO = constructorHV.construirHojasVidaDTO(hojasVidaEncontradas);
		
		// Agrega la in formacion de los municipios, proveniente del microservicio de la empresa
		this.servicioUbicacion.agregarUbicacionAHojasVida(hojasVidaDTO);
		
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
			}
		}
		return hvDTOEncontrada;
	}

	@Override
	public HojaVidaDTO registrarHojaVida(HojaVidaDTO pHojaVidaDTO) {
		
		// Convertir hoja de vida del formato DTO a entidad
		HojaVida hojaVidaRecibida = this.constructorHV.crearEntidadHojaVida(pHojaVidaDTO);	
		
		//Guardar hoja de vida en la base de datos
		HojaVida nuevaHojaVida = this.guardarHojaVida(hojaVidaRecibida);
		
		// Valida si se pudo guardar la hoja de vida
		if(nuevaHojaVida != null) {
			this.guardarReferenciasFamiliares(nuevaHojaVida, constructorHV.construirReferenciasFamiliares(pHojaVidaDTO));
			this.guardarReferenciasPersonales(nuevaHojaVida, constructorHV.construirReferenciasPersonales(pHojaVidaDTO));
			this.guardarExperienciasLaborales(nuevaHojaVida, constructorHV.construirExperienciasLaborales(pHojaVidaDTO));
			this.guardarEstudios(nuevaHojaVida, constructorHV.construirEstudios(pHojaVidaDTO));
		}
		return pHojaVidaDTO;
	}

	
	
	//====== Metodos privados ===========================================================================
	 
	
	private HojaVida guardarHojaVida(HojaVida pHojaVida) {		
		// El id del municipio es valido?
		if(this.servicioUbicacion.validarUbicacion(pHojaVida.getMunicipioId())) {		
			
			// validamos si existe en la base de datos
			HojaVida hojaVidaEncontrada = miRepositorioHojasVida.findById(pHojaVida.getNumeroDocumento()).orElse(null);
			
			if(hojaVidaEncontrada == null) {
				// Guardar la hoja de vida con sus atributos basicos en la BD
				return miRepositorioHojasVida.save(pHojaVida);	
			}
		}
		
		return null;				
	}
	private void guardarReferenciasFamiliares(HojaVida pHojaVida, List<ReferenciaFamiliar> referencias) {		
		if(referencias != null) {			
			for(ReferenciaFamiliar ref : referencias) {
				// Guardar referencia 
				try {
					miRepositorioReferenciasFamiliares.save(ref);
				}
				catch(Exception e) {
					System.out.println("Error al agregar referencia familiar:  " + e.getMessage());
				}
			}
		}
	}
	private void guardarReferenciasPersonales(HojaVida pHojaVida, @Valid List<ReferenciaPersonal> referencias) {
		//referencias = pHojaVida.getReferenciasPersonales();		
		if(referencias != null) {			
			for(ReferenciaPersonal ref : referencias) {				
				// Guardar referencia 				
				try {
					miRepositorioReferenciasPersonales.save(ref);
				}
				catch(Exception e) {
					System.out.println("Error al agregar referencia personal:  " + e.getMessage());
				}
			}
		}
	}
	private void guardarExperienciasLaborales(HojaVida pHojaVida,  @Valid List<ExperienciaLaboral> experiencias) {
		//List<ExperienciaLaboral> experiencias = pHojaVida.getExperienciasLaborales();
		
		if(experiencias != null) {			
			for(ExperienciaLaboral exp : experiencias) {				
				// Guardar experiencia laboral 				
				try {
					EmpresaExterna ee = miServicioEmpresasExternas.crearEmpresaExterna(exp.getEmpresaExterna());
					exp.setEmpresaExterna(ee);
					miRepositorioExperienciasLaborales.save(exp);
				}
				catch(Exception e) {
					System.out.println("Error al agregar experiencia laboral:  " + e.getMessage());
				}
			}
		}
	}
	private void guardarEstudios(HojaVida pHojaVida,  @Valid List<Estudio> estudios) {
		//List<Estudio> estudios = pHojaVida.getEstudios();		
		if(estudios != null) {			
			for(Estudio est : estudios) {				
				// Guardar estudio				
				try {
					InstitucionEducativa inst = miServicioInstitucionesEducativas.crearInstitucionEducativa(est.getInstitucionEducativa());
					est.setInstitucionEducativa(inst);
					miRepositorioEstudios.save(est);
				}
				catch(Exception e) {
					System.out.println("Error al agregar estudio:  " + e.getMessage());
				}
			}
		}
	}
		
}
