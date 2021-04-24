package com.example.demo.HojasVida;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.DAO.HojasVidaDAO;
import com.example.demo.DTO.EstudioDTO;
import com.example.demo.DTO.ExperienciaLaboralDTO;
import com.example.demo.DTO.HojaVidaDTO;
import com.example.demo.DTO.ReferenciaFamiliarDTO;
import com.example.demo.DTO.ReferenciaPersonalDTO;
import com.example.demo.entity.HojaVida;
import com.example.demo.service.ServicioHojasVidaImpl;
import com.example.demo.service.ServicioUbicacionImpl;

@SpringBootTest
public class ServicioHojasVida_RegistrarMockTest {

	@Mock
	public HojasVidaDAO miRepositorioHojasVida;
	
	@Mock
	public ServicioUbicacionImpl servicioUbicacion; 
	
	@Autowired
	public ServicioHojasVidaImpl servicioHV;
	
	
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		servicioHV.setMiRepositorioHojasVida(miRepositorioHojasVida);
		servicioHV.setServicioUbicacion(servicioUbicacion);
			
		// hoja de vida correcta basica
		HojaVida nuevaHV1 = HojaVida.builder()
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
		
		// guardada previamente, no puede agregarse nuevamente
		HojaVida nuevaHV2 = HojaVida.builder()
				.numeroDocumento(2000L)
				.apellidos("Cordoba")
				.calificacion(8.0)
				.correo("juan@gmail.com")
				.direccion("Carrera 10")
				.estadoPersona("PROSPECTO")
				.municipioId(1L)
				.nitEmpresa("1")
				.nombres("Juan")
				.telefono("3177856547")
				.tipoDocumento("CEDULA")
				.build();
		
		// Hoja de vida valida con referencias familiares, referencias personales, estidios y experiencias
		HojaVida nuevaHV3 = HojaVida.builder()
				.numeroDocumento(3000L)
				.apellidos("Cordoba")
				.calificacion(8.0)
				.correo("nelson@gmail.com")
				.direccion("Carrera 10")
				.estadoPersona("PROSPECTO")
				.municipioId(1L)
				.nitEmpresa("1")
				.nombres("Nelson")
				.telefono("3177856547")
				.tipoDocumento("CEDULA")
				.build();
		//nuevaHV3.setReferenciasFamiliares(this.crearReferenciasFamiliares(3000L));
		
		// Mock del repositorio de hojas de vida
		Mockito.when(this.miRepositorioHojasVida.findById(1000L)).thenReturn(Optional.empty());
		Mockito.when(this.miRepositorioHojasVida.findById(2000L)).thenReturn(Optional.of(nuevaHV2));
		Mockito.when(this.miRepositorioHojasVida.findById(3000L)).thenReturn(Optional.empty());
		Mockito.when(this.miRepositorioHojasVida.save(nuevaHV1)).thenReturn(nuevaHV1);
		Mockito.when(this.miRepositorioHojasVida.save(nuevaHV2)).thenReturn(null);
		Mockito.when(this.miRepositorioHojasVida.save(nuevaHV3)).thenReturn(nuevaHV3);
		
		// Mock del servicio de ubicacion
		Mockito.when(this.servicioUbicacion.validarUbicacion(1L)).thenReturn(true);
	}
	

	/**
	 * Hoja de vida existente, no se puede agregar
	 */
	@Test
	public void crearHojaVidaIncorrectaSinRelaciones() {
		HojaVidaDTO nuevaHV = HojaVidaDTO.builder()
				.numeroDocumento(2000L)
				.apellidos("Cordoba")
				.calificacion(8.0)
				.correo("juan@gmail.com")
				.direccion("Carrera 10")
				.estadoPersona("PROSPECTO")
				.municipioId(1L)
				.nitEmpresa("1")
				.nombres("Juan")
				.telefono("3177856547")
				.tipoDocumento("CEDULA")
				.build();
		
		nuevaHV = this.servicioHV.registrarHojaVida(nuevaHV);		
		
		Assertions.assertThat(nuevaHV).isNull();
	}
	/**
	 * Hoja de vida existente, no se puede agregar
	 */
	@Test
	public void crearHojaVidaApellidoInvalidoSinRelaciones() {
		HojaVidaDTO nuevaHV = HojaVidaDTO.builder()
				.numeroDocumento(1000L)
				.apellidos("")
				.calificacion(8.0)
				.correo("juan@gmail.com")
				.direccion("Carrera 10")
				.estadoPersona("PROSPECTO")
				.municipioId(1L)
				.nitEmpresa("1")
				.nombres("Juan")
				.telefono("3177856547")
				.tipoDocumento("CEDULA")
				.build();
		
		nuevaHV = this.servicioHV.registrarHojaVida(nuevaHV);		
		
		Assertions.assertThat(nuevaHV).isNull();
	}
	/**
	 * Hoja de vida con nombre invalido, no la puede agregar
	 */
	@Test
	public void crearHojaVidaNOmbreInvalidoSinRelaciones() {
		HojaVidaDTO nuevaHV = HojaVidaDTO.builder()
				.numeroDocumento(1000L)
				.apellidos("")
				.calificacion(8.0)
				.correo("juan@gmail.com")
				.direccion("Carrera 10")
				.estadoPersona("PROSPECTO")
				.municipioId(1L)
				.nitEmpresa("1")
				.nombres("")
				.telefono("3177856547")
				.tipoDocumento("CEDULA")
				.build();
		
		nuevaHV = this.servicioHV.registrarHojaVida(nuevaHV);		
		
		Assertions.assertThat(nuevaHV).isNull();
	}
	
	/**
	 * Hoja de vida con formato de correo invalido, no se puede agregar
	 */
	@Test
	public void crearHojaVidaCorreoInvalidoSinRelaciones() {
		HojaVidaDTO nuevaHV = HojaVidaDTO.builder()
				.numeroDocumento(1000L)
				.apellidos("")
				.calificacion(8.0)
				.correo("juan @gmail.com")
				.direccion("Carrera 10")
				.estadoPersona("PROSPECTO")
				.municipioId(1L)
				.nitEmpresa("1")
				.nombres("Juan")
				.telefono("3177856547")
				.tipoDocumento("CEDULA")
				.build();
		
		nuevaHV = this.servicioHV.registrarHojaVida(nuevaHV);		
		
		Assertions.assertThat(nuevaHV).isNull();
	}
	
	/**
	 * Hoja de vida con formato de correo invalido, no se puede agregar
	 */
	@Test
	public void crearHojaVidaTelefonoInvalidoSinRelaciones() {
		HojaVidaDTO nuevaHV = HojaVidaDTO.builder()
				.numeroDocumento(1000L)
				.apellidos("")
				.calificacion(8.0)
				.correo("juan @gmail.com")
				.direccion("Carrera 10")
				.estadoPersona("PROSPECTO")
				.municipioId(1L)
				.nitEmpresa("1")
				.nombres("Juan")
				.telefono(null)
				.tipoDocumento("CEDULA")
				.build();
		
		nuevaHV = this.servicioHV.registrarHojaVida(nuevaHV);		
		
		Assertions.assertThat(nuevaHV).isNull();
	}
	
	/**
	 * Hoja de vida con formato de correo invalido, no se puede agregar
	 */
	@Test
	public void crearHojaVidaTipoDocumentoInvalidoSinRelaciones() {
		HojaVidaDTO nuevaHV = HojaVidaDTO.builder()
				.numeroDocumento(1000L)
				.apellidos("")
				.calificacion(8.0)
				.correo("juan @gmail.com")
				.direccion("Carrera 10")
				.estadoPersona("PROSPECTO")
				.municipioId(1L)
				.nitEmpresa("1")
				.nombres("Juan")
				.telefono("3177856547")
				.tipoDocumento("")
				.build();
		
		nuevaHV = this.servicioHV.registrarHojaVida(nuevaHV);		
		
		Assertions.assertThat(nuevaHV).isNull();
	}
	
	
	@Test
	public void crearHojaVidaCorrectaSinRelaciones() {
		HojaVidaDTO nuevaHV = this.crearHVDTO();
		nuevaHV = this.servicioHV.registrarHojaVida(nuevaHV);		
		
		Assertions.assertThat(nuevaHV.getNombres()).isEqualTo("Lupita");
	}
	
	@Test
	public void crearHojaVidaValidaCompleta(){
		HojaVidaDTO nuevaHV = HojaVidaDTO.builder()
				.numeroDocumento(3000L)
				.apellidos("Cordoba")
				.calificacion(8.0)
				.correo("nelson@gmail.com")
				.direccion("Carrera 10")
				.estadoPersona("PROSPECTO")
				.municipioId(1L)
				.nitEmpresa("1")
				.nombres("Nelson")
				.telefono("3177856547")
				.tipoDocumento("CEDULA")
				.build();
		
		nuevaHV.setReferenciasFamiliares(this.crearReferenciasFamiliaresDTO());
		nuevaHV.setReferenciasPersonales(this.crearReferenciasPersonalesDTO());
		nuevaHV.setExperienciasLaborales(this.crearExperienciasDTO());
		nuevaHV.setEstudios(this.crearEstudiosDTO());
		
		nuevaHV = this.servicioHV.registrarHojaVida(nuevaHV);
		
		Assertions.assertThat(nuevaHV.getNombres()).isEqualTo("Nelson");		
		Assertions.assertThat(nuevaHV.getReferenciasFamiliares().size()).isEqualTo(2);
		Assertions.assertThat(nuevaHV.getReferenciasPersonales().size()).isEqualTo(2);
		Assertions.assertThat(nuevaHV.getExperienciasLaborales().size()).isEqualTo(2);
		Assertions.assertThat(nuevaHV.getEstudios().size()).isEqualTo(2);
	}
	
	
	
	
	
	private HojaVidaDTO crearHVDTO() {
		HojaVidaDTO nuevaHV = HojaVidaDTO.builder()
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
		return nuevaHV;
	} 
	
	private List<ReferenciaFamiliarDTO> crearReferenciasFamiliaresDTO(){
		List<ReferenciaFamiliarDTO> lista = new ArrayList<ReferenciaFamiliarDTO>();
		
		ReferenciaFamiliarDTO obj1 = ReferenciaFamiliarDTO.builder()
				.nombres("Juanito")
				.apellidos("Gomez")
				.parentesco("PRIMO")
				.telefono("3144586507")
				.build();
		ReferenciaFamiliarDTO obj2 = ReferenciaFamiliarDTO.builder()
				.nombres("Juanita")
				.apellidos("Gomez Charrasco")
				.parentesco("PRIMA")
				.telefono("3112594316")
				.build();
		
		lista.add(obj1);
		lista.add(obj2);
		
		return lista;
	}
	private List<ReferenciaPersonalDTO> crearReferenciasPersonalesDTO(){
		List<ReferenciaPersonalDTO> lista = new ArrayList<ReferenciaPersonalDTO>();
		
		ReferenciaPersonalDTO obj1 = ReferenciaPersonalDTO.builder()
				.nombres("Pepe")
				.apellidos("Gomez")
				.telefono("3144586507")
				.build();
		ReferenciaPersonalDTO obj2 = ReferenciaPersonalDTO.builder()
				.nombres("Juana")
				.apellidos("Gomez Charrasco")
				.telefono("3112594316")
				.build();
		
		lista.add(obj1);
		lista.add(obj2);
		
		return lista;
	}
	private List<ExperienciaLaboralDTO> crearExperienciasDTO(){
		List<ExperienciaLaboralDTO> lista = new ArrayList<ExperienciaLaboralDTO>();
		
		ExperienciaLaboralDTO obj1 = ExperienciaLaboralDTO.builder()
				.calificacion(9.0)
				.cargoEmpresa("GERENTE")
				.contacto("Contacto")
				.nombreEmpresa("Pio Pio centro")
				.telefonoEmpresa("3124688456")
				.tiempo("2 años")
				.build();
		ExperienciaLaboralDTO obj2 = ExperienciaLaboralDTO.builder()
				.calificacion(9.0)
				.cargoEmpresa("SUB GERENTE")
				.contacto("Contacto")
				.nombreEmpresa("Pio Pio LA ESMERALDA")
				.telefonoEmpresa("3124688456")
				.tiempo("3 años")
				.build();
		
		lista.add(obj1);
		lista.add(obj2);
		
		return lista;
	}
	private List<EstudioDTO> crearEstudiosDTO(){
		List<EstudioDTO> lista = new ArrayList<EstudioDTO>();
		
		EstudioDTO obj1 = EstudioDTO.builder()
				.calificacion(9.0)
				.entidad("SENA")
				.nombreTitulo("TECNICO EN COCINA")
				.tiempo("2 AÑOS")
				.tipo("TECNICO")				
				.build();
		EstudioDTO obj2 = EstudioDTO.builder()
				.calificacion(9.0)
				.entidad("SENA")
				.nombreTitulo("TECNICO EN ALMOHABANAS")
				.tiempo("3 AÑOS")
				.tipo("TECNICO")				
				.build();
		
		lista.add(obj1);
		lista.add(obj2);
		
		return lista;
	}
}	


