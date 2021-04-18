package com.example.demo.HojasVida;

//import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Assertions;
//import org.assertj.core.api.Assertions;

import com.example.demo.DAO.EmpresasExternasDAO;
import com.example.demo.DAO.EstudiosDAO;
import com.example.demo.DAO.ExperienciasLaboralesDAO;
import com.example.demo.DAO.HojasVidaDAO;
import com.example.demo.DAO.InstitucionesEducativasDAO;
import com.example.demo.DAO.ReferenciasFamiliaresDAO;
import com.example.demo.DAO.ReferenciasPersonalesDAO;
import com.example.demo.entity.EmpresaExterna;
import com.example.demo.entity.Estudio;
import com.example.demo.entity.ExperienciaLaboral;
import com.example.demo.entity.HojaVida;
import com.example.demo.entity.InstitucionEducativa;
import com.example.demo.entity.ReferenciaFamiliar;
import com.example.demo.entity.ReferenciaPersonal;

/**
 * Pruebas unitarias para el acceso a base de datos * 
 * 
 * @author Ruben
 *
 */

@DataJpaTest
class HojasVidaDAOTest {

	@Autowired
	private HojasVidaDAO miRepositorioHojasVida;
	
	@Autowired
	private ReferenciasFamiliaresDAO miRepositorioReferenciasFamiliares;
	
	@Autowired
	private ReferenciasPersonalesDAO miRepositorioReferenciasPersonales;
	
	@Autowired
	private ExperienciasLaboralesDAO miRepositorioExperienciasLaborales;
	
	@Autowired
	private EstudiosDAO miRepositorioEstudios;
	
	@Autowired 
	private EmpresasExternasDAO miRepositorioEmpresasExternas;
	
	@Autowired 
	private InstitucionesEducativasDAO miRepositorioInstituciones;
	
	
		
	/**
	 * Deberia agregarse sin problema
	 */
	@Test
	public void agregarHVCorrectaSinRelaciones() {
				
		// Hojas de vida antes de insertar
		List<HojaVida> hvEncontradas = this.miRepositorioHojasVida.findAll();
		int cantidadHVAntes = hvEncontradas.size();
		
		// Insertar hoja vida
		this.agregarHV();
		
		// Hojas de vida despues de insertar
		hvEncontradas = this.miRepositorioHojasVida.findAll();
		int cantidadHVDespues = hvEncontradas.size();
		
		Assertions.assertEquals(cantidadHVAntes, cantidadHVDespues - 1);
	}
			
	/**
	 * deberia agregarse correctamente, en caso de que la hoja de vida se agrege correctamente
	 */
	@Test
	public void agregarReferenciasFamiliaresConHojaVida() {	
		this.agregarHV();
		
		// Referencias Familiares antes de insertar
		List<ReferenciaFamiliar> refEncontradas = this.miRepositorioReferenciasFamiliares.findAll();
		int cantidadRefsAntes = refEncontradas.size();
		
		// agregar referencias
		this.agregarReferenciasFamiliares();
		
		// Referencias Familiares despues de insertar
		refEncontradas = this.miRepositorioReferenciasFamiliares.findAll();
		int cantidadRefsDespues = refEncontradas.size();
		
		// deben ser iguales porque no deben agregarse, ya que no exista una hv
		Assertions.assertEquals(cantidadRefsAntes, cantidadRefsDespues-2);
		
	}
				
	/**
	 * deberia agregarse correctamente, en caso de que la hoja de vida se agrege correctamente
	 */
	@Test
	public void agregarReferenciasPersonalesConHojaVida() {
		this.agregarHV();
				
		// Referencias Personales antes de insertar
		List<ReferenciaPersonal> refEncontradas = this.miRepositorioReferenciasPersonales.findAll();
		int cantidadRefsAntes = refEncontradas.size();
		
		this.agregarReferenciasPersonales();
		
		// Referencias Personales despues de insertar
		refEncontradas = this.miRepositorioReferenciasPersonales.findAll();
		int cantidadRefsDespues = refEncontradas.size();
		
		// deben ser iguales porque no deben agregarse, ya que no exista una hv
		Assertions.assertEquals(cantidadRefsAntes, cantidadRefsDespues-2);		
	}
				
	/**
	 * deberia agregarse correctamente, en caso de que la hoja de vida se agrege correctamente
	 */
	@Test
	public void agregarExperienciasConHojaVida() {
		this.agregarHV();
		
		// Referencias Personales antes de insertar
		List<ExperienciaLaboral> expEncontradas = this.miRepositorioExperienciasLaborales.findAll();
		int cantidadAntes = expEncontradas.size();
		
		this.agregarExperiencias();
		
		// Referencias Personales despues de insertar
		expEncontradas = this.miRepositorioExperienciasLaborales.findAll();
		int cantidadDespues = expEncontradas.size();
		
		// deben ser iguales porque no deben agregarse, ya que no exista una hv
		Assertions.assertEquals(cantidadAntes, cantidadDespues-2);		
	}
				
	/**
	 * deberia agregarse correctamente, en caso de que la hoja de vida se agrege correctamente
	 */
	@Test
	public void agregarEstudiosConHojaVida() {
		this.agregarHV();
		
		// Estudios antes de insertar
		List<Estudio> estEncontradas = this.miRepositorioEstudios.findAll();
		int cantidadAntes = estEncontradas.size();
		
		this.agregarEstudios();
		
		// Estudios despues de insertar
		estEncontradas = this.miRepositorioEstudios.findAll();
		int cantidadDespues = estEncontradas.size();
		
		// deben ser iguales porque no deben agregarse, ya que no exista una hv
		Assertions.assertEquals(cantidadAntes, cantidadDespues-2);		
	}
	
	/**
	 * Agregar hoja de vida con todos los arreglos	 * 
	 */
	@Test
	public void agregarHVCompleta() {
		// Hojas de vida antes de insertar
		List<HojaVida> hvEncontradas = this.miRepositorioHojasVida.findAll();
		int cantidadHVAntes = hvEncontradas.size();
		
		this.agregarHV();
		this.agregarEstudios();
		this.agregarExperiencias();
		this.agregarReferenciasFamiliares();
		this.agregarReferenciasPersonales();
		
		// Hojas de vida despues de insertar
		hvEncontradas = this.miRepositorioHojasVida.findAll();
		int cantidadHVDespues = hvEncontradas.size();
		
		Assertions.assertEquals(cantidadHVAntes, cantidadHVDespues - 1);		
	}

	/**
	 * Buscar una hoja de vida con un id existente en la base de datos 
	 */
	@Test
	public void buscarHojaVidaPorIdValido() {
		this.agregarHV();
		HojaVida hv = this.miRepositorioHojasVida.findById(1000L).orElse(null);
		
		Assertions.assertEquals(hv.getNumeroDocumento(), 1000L);
	}
	
	/**
	 * Buscar una hoja de vida con un id existente en la base de datos 
	 */
	@Test
	public void buscarHojaVidaPorIdInValido() {
		this.agregarHV();
		HojaVida hv = this.miRepositorioHojasVida.findById(1001L).orElse(null);
		
		Assertions.assertNull(hv);
	}
	
	/**
	 * Buscar una hoja de vida con un correo existente en la base de datos 
	 */
	@Test
	public void buscarHojaVidaPorCorreoValido() {
		this.agregarHV();
		HojaVida hv = this.miRepositorioHojasVida.findByCorreo("lupita@gmail.com");
		
		Assertions.assertEquals(hv.getCorreo(),"lupita@gmail.com");
	}
	
	/**
	 * Buscar una hoja de vida con un correo no existente en la base de datos 
	 */
	@Test
	public void buscarHojaVidaPorCorreoInValido() {
		this.agregarHV();
		HojaVida hv = this.miRepositorioHojasVida.findByCorreo("lupita2@gmail.com");
		
		Assertions.assertNull(hv);
	}
	
	
	
	// ACTUALIZAR HOJA DE VIDA	
	
	/**
	 * Actualiza todos los campos de la hv, sin las relaciones como referencias o estudios
	 */
	@Test
	public void actualizarHojaVidaBasica() {
		this.agregarHV();
		HojaVida hv = this.miRepositorioHojasVida.findById(1000L).orElse(null);
		
		hv.setNombres("Sandra");
		hv.setApellidos("Jimenez");
		hv.setDireccion("Nueva direccion");
		hv.setCorreo("sandra@gmail.com");
		hv.setCalificacion(9.0);
		hv.setEstadoPersona("CONTRATADO");
		hv.setTelefono("3119856734");
		
		this.miRepositorioHojasVida.save(hv);
		
		hv = this.miRepositorioHojasVida.findById(1000L).orElse(null);
		
		Assertions.assertEquals(hv.getNombres(),"Sandra");
		Assertions.assertEquals(hv.getApellidos(),"Jimenez");
		Assertions.assertEquals(hv.getDireccion(),"Nueva direccion");
		Assertions.assertEquals(hv.getCorreo(),"sandra@gmail.com");
		Assertions.assertEquals(hv.getCalificacion(),9.0);
		Assertions.assertEquals(hv.getEstadoPersona(),"CONTRATADO");
		Assertions.assertEquals(hv.getTelefono(),"3119856734");
	}
	
	@Test
	public void actualizarReferenciasFamiliares() {
		this.agregarHV();
		
		ReferenciaFamiliar ref1 = ReferenciaFamiliar.builder()
				.nombres("Juanito")
				.apellidos("Gomez")
				.numeroDocumento(1000L)
				.parentesco("PRIMO")
				.telefono("3144586507")
				.build();
		ReferenciaFamiliar ref2 = ReferenciaFamiliar.builder()
				.nombres("Juanita")
				.apellidos("Gomez Charrasco")
				.numeroDocumento(1000L)
				.parentesco("PRIMA")
				.telefono("3112594316")
				.build();
		
		// Insertar Referencias Familiares		
		ref1 = this.miRepositorioReferenciasFamiliares.save(ref1);
		ref2 = this.miRepositorioReferenciasFamiliares.save(ref2);
		
		ref1.setNombres("Pepe");
		ref1.setApellidos("Golzales");
		ref1 = this.miRepositorioReferenciasFamiliares.save(ref1);
		
		ref2.setNombres("Laura");
		ref2.setApellidos("Ruiz");
		ref2 = this.miRepositorioReferenciasFamiliares.save(ref2);
		
		Assertions.assertEquals(ref1.getNombres(), "Pepe");	
		Assertions.assertEquals(ref1.getApellidos(), "Golzales");	
		Assertions.assertEquals(ref2.getNombres(), "Laura");	
		Assertions.assertEquals(ref2.getApellidos(), "Ruiz");
	}
	
	@Test
	public void actualizarReferenciasPersonales() {
		this.agregarHV();
		
		ReferenciaPersonal ref1 = ReferenciaPersonal.builder()
				.nombres("Juanito")
				.apellidos("Gomez")
				.numeroDocumento(1000L)
				.telefono("3144586507")
				.build();
		ReferenciaPersonal ref2 = ReferenciaPersonal.builder()
				.nombres("Juanita")
				.apellidos("Gomez Charrasco")
				.numeroDocumento(1000L)
				.telefono("3112594316")
				.build();
		
		// Insertar Referencias Familiares		
		ref1 = this.miRepositorioReferenciasPersonales.save(ref1);
		ref2 = this.miRepositorioReferenciasPersonales.save(ref2);
		
		ref1.setNombres("Pepe");
		ref1.setApellidos("Golzales");
		ref1 = this.miRepositorioReferenciasPersonales.save(ref1);
		
		ref2.setNombres("Laura");
		ref2.setApellidos("Ruiz");
		ref2 = this.miRepositorioReferenciasPersonales.save(ref2);
		
		Assertions.assertEquals(ref1.getNombres(), "Pepe");	
		Assertions.assertEquals(ref1.getApellidos(), "Golzales");	
		Assertions.assertEquals(ref2.getNombres(), "Laura");	
		Assertions.assertEquals(ref2.getApellidos(), "Ruiz");
	}
	
	@Test
	public void actualizarExperiencias() {
		this.agregarHV();
		
		EmpresaExterna ee = EmpresaExterna.builder()
				.empresaExternaId(150L)
				.contacto("Lucas")
				.nombre("Domino's Pizza")
				.telefono("3126789367")
				.build();
		
		ee = this.miRepositorioEmpresasExternas.save(ee);
		
		ExperienciaLaboral exp1 = ExperienciaLaboral.builder()
				.calificacion(8.0)
				.cargo("MESERO")
				.empresaExterna(ee)
				.numeroDocumento(1000L)
				.tiempo("5 años")
				.build();
				
				
		ExperienciaLaboral exp2 = ExperienciaLaboral.builder()
				.calificacion(9.0)
				.cargo("COCINERO")
				.empresaExterna(ee)
				.numeroDocumento(1000L)
				.tiempo("1 años")
				.build();
		
		// Insertar Referencias Personales
		exp1 = this.miRepositorioExperienciasLaborales.save(exp1);
		exp2 = this.miRepositorioExperienciasLaborales.save(exp2);
		
		exp1.setCargo("Panadero");
		exp1.setTiempo("9 meses"); 
		exp1 = this.miRepositorioExperienciasLaborales.save(exp1);
		
		exp2.setCargo("Carnicero");
		exp2.setTiempo("7 meses"); 
		exp2 = this.miRepositorioExperienciasLaborales.save(exp2);
		
		Assertions.assertEquals(exp1.getCargo(), "Panadero");	
		Assertions.assertEquals(exp1.getTiempo(), "9 meses");	
		Assertions.assertEquals(exp2.getCargo(), "Carnicero");	
		Assertions.assertEquals(exp2.getTiempo(), "7 meses");
	}
	
	@Test
	public void actualizarEstudios() {
		this.agregarHV();
		
		InstitucionEducativa inst = InstitucionEducativa.builder()
				.instId(200L)
				.nombre("Servicios tecnicos Cauca")
				.build();
		
		inst = this.miRepositorioInstituciones.save(inst);
		
		Estudio est1 = Estudio.builder()
				.calificacion(8.0)
				.institucionEducativa(inst)
				.nombreTitulo("TECNICO EN REPARACION DE LICUADORAS")
				.numeroDocumento(1000L)
				.tiempo("6 años")
				.tipo("TECNICO")
				.build();
		
		Estudio est2 = Estudio.builder()
				.calificacion(8.0)
				.institucionEducativa(inst)
				.nombreTitulo("TECNICO EN SERVICIO AL CLIENTE")
				.numeroDocumento(1000L)
				.tiempo("2 años")
				.tipo("TECNICO")
				.build();
		
		// Insertar Estudios
		est1 = this.miRepositorioEstudios.save(est1);
		est2 = this.miRepositorioEstudios.save(est2);
		
		est1.setNombreTitulo("TECNICO EN LICUADORAS");
		est1.setTiempo("4 años");
		est1 = this.miRepositorioEstudios.save(est1);
		
		est2.setNombreTitulo("TECNICO EN ATENCION AL CLIENTE");
		est2.setTiempo("3 años");
		est2 = this.miRepositorioEstudios.save(est2);
		
		Assertions.assertEquals(est1.getNombreTitulo(), "TECNICO EN LICUADORAS");	
		Assertions.assertEquals(est1.getTiempo(), "4 años");	
		Assertions.assertEquals(est2.getNombreTitulo(), "TECNICO EN ATENCION AL CLIENTE");	
		Assertions.assertEquals(est2.getTiempo(), "3 años");
	}
	
	
	private HojaVida agregarHV() {
		HojaVida nuevaHV = HojaVida.builder()
				.numeroDocumento(1000L)
				.apellidos("Gomez")
				.calificacion(8.0)
				.correo("lupita@gmail.com")
				.direccion("Carrera 9na")
				.estadoPersona("PROSPECTO")
				.municipioId(1L)
				.nitEmpresa("1")
				.nombres("Lupita")
				.telefono("3157856547")
				.tipoDocumento("CEDULA")
				.build();

		// Insertar hoja vida
		return this.miRepositorioHojasVida.save(nuevaHV);
	} 
	private void agregarReferenciasFamiliares() {
		ReferenciaFamiliar ref1 = ReferenciaFamiliar.builder()
				.nombres("Juanito")
				.apellidos("Gomez")
				.numeroDocumento(1000L)
				.parentesco("PRIMO")
				.telefono("3144586507")
				.build();
		ReferenciaFamiliar ref2 = ReferenciaFamiliar.builder()
				.nombres("Juanita")
				.apellidos("Gomez Charrasco")
				.numeroDocumento(1000L)
				.parentesco("PRIMA")
				.telefono("3112594316")
				.build();
		
		// Insertar Referencias Familiares		
		this.miRepositorioReferenciasFamiliares.save(ref1);
		this.miRepositorioReferenciasFamiliares.save(ref2);
	}
	private void agregarReferenciasPersonales() {
		ReferenciaPersonal ref1 = ReferenciaPersonal.builder()
				.nombres("Juanito")
				.apellidos("Gomez")
				.numeroDocumento(1000L)
				.telefono("3144586507")
				.build();
		ReferenciaPersonal ref2 = ReferenciaPersonal.builder()
				.nombres("Juanita")
				.apellidos("Gomez Charrasco")
				.numeroDocumento(1000L)
				.telefono("3112594316")
				.build();	
		
		// Insertar Referencias Personales		
		this.miRepositorioReferenciasPersonales.save(ref1);
		this.miRepositorioReferenciasPersonales.save(ref2);		
	}
	private void agregarExperiencias() {
		EmpresaExterna ee = EmpresaExterna.builder()
				.empresaExternaId(150L)
				.contacto("Lucas")
				.nombre("Domino's Pizza")
				.telefono("3126789367")
				.build();
		
		ee = this.miRepositorioEmpresasExternas.save(ee);
		
		ExperienciaLaboral exp1 = ExperienciaLaboral.builder()
				.calificacion(8.0)
				.cargo("MESERO")
				.empresaExterna(ee)
				.numeroDocumento(1000L)
				.tiempo("5 años")
				.build();
				
				
		ExperienciaLaboral exp2 = ExperienciaLaboral.builder()
				.calificacion(9.0)
				.cargo("COCINERO")
				.empresaExterna(ee)
				.numeroDocumento(1000L)
				.tiempo("1 años")
				.build();
		
		// Insertar Referencias Personales
		this.miRepositorioExperienciasLaborales.save(exp1);
		this.miRepositorioExperienciasLaborales.save(exp2);
	}
	private void agregarEstudios() {
		InstitucionEducativa inst = InstitucionEducativa.builder()
				.instId(200L)
				.nombre("Servicios tecnicos Cauca")
				.build();
		
		inst = this.miRepositorioInstituciones.save(inst);
		
		Estudio est1 = Estudio.builder()
				.calificacion(8.0)
				.institucionEducativa(inst)
				.nombreTitulo("TECNICO EN REPARACION DE LICUADORAS")
				.numeroDocumento(1000L)
				.tiempo("6 años")
				.tipo("TECNICO")
				.build();
		
		Estudio est2 = Estudio.builder()
				.calificacion(8.0)
				.institucionEducativa(inst)
				.nombreTitulo("TECNICO EN SERVICIO AL CLIENTE")
				.numeroDocumento(1000L)
				.tiempo("2 años")
				.tipo("TECNICO")
				.build();
		
		// Insertar Estudios
		this.miRepositorioEstudios.save(est1);
		this.miRepositorioEstudios.save(est2);
	}
}
