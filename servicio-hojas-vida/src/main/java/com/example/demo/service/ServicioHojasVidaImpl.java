package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.example.demo.entity.Estudio;
import com.example.demo.entity.ExperienciaLaboral;
import com.example.demo.entity.HojaVida;
import com.example.demo.entity.ReferenciaFamiliar;
import com.example.demo.entity.ReferenciaPersonal;
import com.example.demo.feign_client.EmpresaClient;
import com.example.demo.model.Municipio;
import com.example.demo.repository.EstudiosDAO;
import com.example.demo.repository.ExperienciasLaboralesDAO;
import com.example.demo.repository.HojasVidaDAO;
import com.example.demo.repository.ReferenciasFamiliaresDAO;
import com.example.demo.repository.ReferenciasPersonalesDAO;

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
	 * Referencia al servicio de la empresa, utilizando inyeccion de dependencias
	 * automatizado por el framework 
	 */
	@Autowired	
	EmpresaClient clienteEmpresa;
	
	
	
	
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
	public List<HojaVida> listarHojasVida() {		
		// Busca todas las hojas de vida del sistema
		List<HojaVida> hojasVidaEncontradas = miRepositorioHojasVida.findAll();
		
		// Agrega la in formacion de los municipios, proveniente del microservicio de la empresa
		this.agregarUbicacion(hojasVidaEncontradas);
		
		return hojasVidaEncontradas;
	}
	
	@Override
	public HojaVida buscarHojaVidaPorId(Long pHojaVidaId) {
		HojaVida hvEncontrada = null;
		if(pHojaVidaId != null) {
			hvEncontrada = this.miRepositorioHojasVida.findById(pHojaVidaId).orElse(null);
			
			// Agregar informacion de la ubicacion
			if(hvEncontrada != null) {
				hvEncontrada.setMunicipio(this.consultarMunicipio(hvEncontrada.getMunicipioId()));
			}
		}
		return hvEncontrada;
	}

	@Override
	public HojaVida registrarHojaVida(HojaVida pHojaVida) {			
		HojaVida nuevaHojaVida = this.guardarHojaVida(pHojaVida);
		
		if(nuevaHojaVida != null) {
			this.guardarReferenciasFamiliares(nuevaHojaVida, pHojaVida.getReferenciasFamiliares());
			this.guardarReferenciasPersonales(nuevaHojaVida, pHojaVida.getReferenciasPersonales());
			this.guardarExperienciasLaborales(nuevaHojaVida, pHojaVida.getExperienciasLaborales());
			this.guardarEstudios(nuevaHojaVida, pHojaVida.getEstudios());
			
			return pHojaVida;
		}
		return nuevaHojaVida;
	}

	
	
	//====== Metodos privados ===========================================================================
	 
	
	private HojaVida guardarHojaVida(HojaVida pHojaVida) {		
		// El id del municipio es valido?
		if(this.validarUbicacion(pHojaVida.getMunicipioId())) {		
			
			// validamos si existe en la base de datos
			HojaVida nuevaHojaVida = miRepositorioHojasVida.findById(pHojaVida.getNumeroDocumento()).orElse(null);
			
			if(nuevaHojaVida == null) {		
				nuevaHojaVida= HojaVida.builder()
						.numeroDocumento(pHojaVida.getNumeroDocumento())
						.tipoDocumento(pHojaVida.getTipoDocumento())
						.nombres(pHojaVida.getNombres())
						.apellidos(pHojaVida.getApellidos())
						.telefono(pHojaVida.getTelefono())
						.municipioId(pHojaVida.getMunicipioId())
						.direccion(pHojaVida.getDireccion())
						.calificacion(pHojaVida.getCalificacion())
						.build();
				
				// Guardar la hoja de vida con sus atributos basicos en la BD
				return miRepositorioHojasVida.save(nuevaHojaVida);	
			}
		}
		
		return null;				
	}
	private void guardarReferenciasFamiliares(HojaVida pHojaVida, List<ReferenciaFamiliar> referencias) {		
		
		if(referencias != null) {			
			for(ReferenciaFamiliar ref : referencias) {
				ReferenciaFamiliar nuevaReferencia = ReferenciaFamiliar.builder()
						.numeroDocumento(pHojaVida.getNumeroDocumento())
						.nombres(ref.getNombres())
						.apellidos(ref.getApellidos())
						.telefono(ref.getTelefono())
						.parentesco(ref.getParentesco())
						.build();
				
				// Guardar referencia 
				try {
					miRepositorioReferenciasFamiliares.save(nuevaReferencia);
				}
				catch(Exception e) {
					System.out.println("Error al agregar referencia familiar");
				}
			}
		}
	}
	private void guardarReferenciasPersonales(HojaVida pHojaVida, @Valid List<ReferenciaPersonal> referencias) {
		//referencias = pHojaVida.getReferenciasPersonales();
		
		if(referencias != null) {			
			for(ReferenciaPersonal ref : referencias) {
				ReferenciaPersonal nuevaReferencia = ReferenciaPersonal.builder()
						.numeroDocumento(pHojaVida.getNumeroDocumento())
						.nombres(ref.getNombres())
						.apellidos(ref.getApellidos())
						.telefono(ref.getTelefono())
						.build();
				
				// Guardar referencia 				
				try {
					miRepositorioReferenciasPersonales.save(nuevaReferencia);
				}
				catch(Exception e) {
					System.out.println("Error al agregar referencia personal");
				}
			}
		}
	}
	private void guardarExperienciasLaborales(HojaVida pHojaVida,  @Valid List<ExperienciaLaboral> experiencias) {
		//List<ExperienciaLaboral> experiencias = pHojaVida.getExperienciasLaborales();
		
		if(experiencias != null) {			
			for(ExperienciaLaboral exp : experiencias) {
				ExperienciaLaboral nuevaExperiencia = ExperienciaLaboral.builder()
						.numeroDocumento(pHojaVida.getNumeroDocumento())
						.cargo(exp.getCargo())
						.tiempo(exp.getTiempo())
						.calificacion(exp.getCalificacion())						
						.empresaExterna(miServicioEmpresasExternas.crearEmpresaExterna(exp.getEmpresaExterna()))
						.build();
				
				// Guardar experiencia laboral 				
				try {
					miRepositorioExperienciasLaborales.save(nuevaExperiencia);
				}
				catch(Exception e) {
					System.out.println("Error al agregar referencia personal");
				}
			}
		}
	}
	private void guardarEstudios(HojaVida pHojaVida,  @Valid List<Estudio> estudios) {
		//List<Estudio> estudios = pHojaVida.getEstudios();
		
		if(estudios != null) {			
			for(Estudio est : estudios) {
				Estudio nuevoEstudio = Estudio.builder()
						.numeroDocumento(pHojaVida.getNumeroDocumento())
						.nombreTitulo(est.getNombreTitulo())
						.calificacion(est.getCalificacion())
						.tipo(est.getTipo())
						.tiempo(est.getTiempo())
						.institucionEducativa(miServicioInstitucionesEducativas.crearInstitucionEducativa(est.getInstitucionEducativa()))
						.build();
				
				// Guardar estudio				
				try {
					miRepositorioEstudios.save(nuevoEstudio);
				}
				catch(Exception e) {
					System.out.println("Error al agregar referencia personal");
				}
			}
		}
	}
		
	private boolean validarUbicacion(Long pIdMunicipio) {
		boolean esValido = false;
		
		if(pIdMunicipio != null) {
			Municipio municipioEncontrado = this.consultarMunicipio(pIdMunicipio);
			if(municipioEncontrado != null) {
				esValido = true;
			}
		}
		
		return esValido;
	}
	private Municipio consultarMunicipio(Long pIdMunicipio) {
		if(pIdMunicipio != null) {
			
			Municipio municipioEncontrado = null;			
			try {
				municipioEncontrado = this.clienteEmpresa.buscarMunPorId(pIdMunicipio);
				return municipioEncontrado;
			}catch(Exception e){
				System.out.println("Error al buscar el municipio");
			}			
		}
		return null;
	}	
	private void agregarUbicacion(List<HojaVida> pHojasVida){
		// Almacena los municipios diferentes encontrados en cada hoja de vida
		// TODO ¿como variable global?
		ArrayList<Municipio> cacheMunicipios = new ArrayList<Municipio>();
				
		for(HojaVida hv: pHojasVida) {
			//Si el municipio ya se encontro para otra hoja de vida, no se tiene que pedir de nuevo			 			
			for(Municipio mun: cacheMunicipios) {
				if(hv.getMunicipioId().equals(mun.getMunicipio_id())) {
					hv.setMunicipio(mun);
					break;
				}
			}
			// no se ha encontrado ese municipio
			if(hv.getMunicipio() == null){
				Municipio munEncontrado = this.consultarMunicipio(hv.getMunicipioId());
				
				// Lo agrego a la lista de municipios encontrados
				if(munEncontrado != null) {
					cacheMunicipios.add(munEncontrado);
					hv.setMunicipio(munEncontrado);
				}
			}
		}
		
	}

}
