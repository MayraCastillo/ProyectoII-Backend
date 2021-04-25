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
public class ServicioHojasVida_ActualizarMockTest {

	@Mock
	public HojasVidaDAO miRepositorioHojasVida;
	
	@Mock
	public ServicioUbicacionImpl servicioUbicacion; 
	
	@Autowired
	public ServicioHojasVidaImpl servicioHV;
	
	
	/**
	 * Aqui agrego la hoja de vida que se va a actualizar posteriormente, tambien 
	 * se configuran los Mock
	 */
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		servicioHV.setMiRepositorioHojasVida(miRepositorioHojasVida);
		servicioHV.setServicioUbicacion(servicioUbicacion);
		
		HojaVida nuevaHV1 = HojaVida.builder()
				.numeroDocumento(1000L)
				.apellidos("Gomez")
				.calificacion(8.0)
				.correo("lupita@gmail.com")
				.direccion("Carrera 9na")
				//.estadoPersona("PROSPECTO")
				.municipioId(1L)
				//.nitEmpresa("1")
				.nombres("Lupita")
				.telefono("3157856547")
				.tipoDocumento("CEDULA")
				.build();
		
		HojaVida nuevaHV3 = HojaVida.builder()
				.numeroDocumento(3000L)
				.apellidos("Gomez")
				.calificacion(8.0)
				.correo("lupita @gmail.com") // correo invalido
				.direccion("Carrera 9na")
				//.estadoPersona("PROSPECTO")
				.municipioId(1L)
				//.nitEmpresa("1")
				.nombres("Lupita")
				.telefono("3157856547")
				.tipoDocumento("CEDULA")
				.build();
		
		
		// Mock del repositorio de hojas de vida
		Mockito.when(this.miRepositorioHojasVida.findById(1000L)).thenReturn(Optional.of(nuevaHV1));
		Mockito.when(this.miRepositorioHojasVida.existsById(1000L)).thenReturn(true);
		Mockito.when(this.miRepositorioHojasVida.findById(2000L)).thenReturn(Optional.empty());
		Mockito.when(this.miRepositorioHojasVida.existsById(2000L)).thenReturn(false);
		Mockito.when(this.miRepositorioHojasVida.findById(3000L)).thenReturn(Optional.of(nuevaHV3));
		Mockito.when(this.miRepositorioHojasVida.existsById(3000L)).thenReturn(true);
		Mockito.when(this.miRepositorioHojasVida.save(nuevaHV1)).thenReturn(nuevaHV1);
		Mockito.when(this.miRepositorioHojasVida.save(nuevaHV3)).thenReturn(null);
		
		
		// Mock del servicio de ubicacion
		Mockito.when(this.servicioUbicacion.validarUbicacion(1L)).thenReturn(true);
		Mockito.when(this.servicioUbicacion.validarUbicacion(99999L)).thenReturn(false);
		
			
	}
	
	@Test
	public void ActualizarHojaVidaCorrecto(){		
		HojaVidaDTO nuevaHV = this.servicioHV.buscarHojaVidaPorId(1000L);
		Assertions.assertThat(nuevaHV).isNotNull();
		
		nuevaHV.setReferenciasFamiliares(this.crearReferenciasFamiliaresDTO());
		nuevaHV.setReferenciasPersonales(this.crearReferenciasPersonalesDTO());
		nuevaHV.setExperienciasLaborales(this.crearExperienciasDTO());
		nuevaHV.setEstudios(this.crearEstudiosDTO());
		
		nuevaHV = this.servicioHV.actualizarHojaVida(nuevaHV);
		
		Assertions.assertThat(nuevaHV.getNombres()).isEqualTo("Lupita");		
		Assertions.assertThat(nuevaHV.getReferenciasFamiliares().size()).isEqualTo(2);
		Assertions.assertThat(nuevaHV.getReferenciasPersonales().size()).isEqualTo(2);
		Assertions.assertThat(nuevaHV.getExperienciasLaborales().size()).isEqualTo(2);
		Assertions.assertThat(nuevaHV.getEstudios().size()).isEqualTo(2);
	}
	
	@Test
	public void ActualizarReferenciaFamiliarHojaVidaCorrecto(){		
		HojaVidaDTO nuevaHV = this.servicioHV.buscarHojaVidaPorId(1000L);
		Assertions.assertThat(nuevaHV).isNotNull();
		
		nuevaHV.setReferenciasFamiliares(this.crearReferenciasFamiliaresDTO());
		nuevaHV.getReferenciasFamiliares().get(0).setNombres("Luis");
		nuevaHV.setReferenciasPersonales(this.crearReferenciasPersonalesDTO());
		nuevaHV.setExperienciasLaborales(this.crearExperienciasDTO());
		nuevaHV.setEstudios(this.crearEstudiosDTO());
		
		nuevaHV = this.servicioHV.actualizarHojaVida(nuevaHV);
		
		Assertions.assertThat(nuevaHV.getNombres()).isEqualTo("Lupita");
		Assertions.assertThat(nuevaHV.getReferenciasFamiliares().size()).isEqualTo(2);
		Assertions.assertThat(nuevaHV.getReferenciasFamiliares().get(0).getNombres()).isEqualTo("Luis");
	}
	
	@Test
	public void ActualizarReferenciaPersonalHojaVidaCorrecto(){		
		HojaVidaDTO nuevaHV = this.servicioHV.buscarHojaVidaPorId(1000L);
		Assertions.assertThat(nuevaHV).isNotNull();
		
		nuevaHV.setReferenciasFamiliares(this.crearReferenciasFamiliaresDTO());		
		nuevaHV.setReferenciasPersonales(this.crearReferenciasPersonalesDTO());		
		nuevaHV.setExperienciasLaborales(this.crearExperienciasDTO());
		nuevaHV.setEstudios(this.crearEstudiosDTO());
		
		nuevaHV.getReferenciasPersonales().get(0).setNombres("Luis");
		
		nuevaHV = this.servicioHV.actualizarHojaVida(nuevaHV);
		
		Assertions.assertThat(nuevaHV.getNombres()).isEqualTo("Lupita");
		Assertions.assertThat(nuevaHV.getReferenciasPersonales().size()).isEqualTo(2);
		Assertions.assertThat(nuevaHV.getReferenciasPersonales().get(0).getNombres()).isEqualTo("Luis");
	}
	
	@Test
	public void ActualizarExperienciaHojaVidaCorrecto(){		
		HojaVidaDTO nuevaHV = this.servicioHV.buscarHojaVidaPorId(1000L);
		Assertions.assertThat(nuevaHV).isNotNull();
		
		nuevaHV.setReferenciasFamiliares(this.crearReferenciasFamiliaresDTO());		
		nuevaHV.setReferenciasPersonales(this.crearReferenciasPersonalesDTO());		
		nuevaHV.setExperienciasLaborales(this.crearExperienciasDTO());
		nuevaHV.setEstudios(this.crearEstudiosDTO());
		
		nuevaHV.getExperienciasLaborales().get(0).setCargoEmpresa("Domiciliario");
		
		nuevaHV = this.servicioHV.actualizarHojaVida(nuevaHV);
		
		Assertions.assertThat(nuevaHV.getNombres()).isEqualTo("Lupita");
		Assertions.assertThat(nuevaHV.getExperienciasLaborales().size()).isEqualTo(2);
		Assertions.assertThat(nuevaHV.getExperienciasLaborales().get(0).getCargoEmpresa()).isEqualTo("Domiciliario");
	}
	
	@Test
	public void ActualizarEstudioHojaVidaCorrecto(){		
		HojaVidaDTO nuevaHV = this.servicioHV.buscarHojaVidaPorId(1000L);
		Assertions.assertThat(nuevaHV).isNotNull();
		
		nuevaHV.setReferenciasFamiliares(this.crearReferenciasFamiliaresDTO());		
		nuevaHV.setReferenciasPersonales(this.crearReferenciasPersonalesDTO());		
		nuevaHV.setExperienciasLaborales(this.crearExperienciasDTO());
		nuevaHV.setEstudios(this.crearEstudiosDTO());
		
		nuevaHV.getEstudios().get(0).setTiempo("9 meses");
		
		nuevaHV = this.servicioHV.actualizarHojaVida(nuevaHV);
		
		Assertions.assertThat(nuevaHV.getNombres()).isEqualTo("Lupita");
		Assertions.assertThat(nuevaHV.getEstudios().size()).isEqualTo(2);
		Assertions.assertThat(nuevaHV.getEstudios().get(0).getTiempo()).isEqualTo("9 meses");
	}
		
	@Test
	public void ActualizarHojaVidaInexistente(){		
		HojaVidaDTO nuevaHV = this.servicioHV.buscarHojaVidaPorId(2000L);
		Assertions.assertThat(nuevaHV).isNull();
		
		nuevaHV = HojaVidaDTO.builder()
				.numeroDocumento(2000L)
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
		
		nuevaHV = this.servicioHV.actualizarHojaVida(nuevaHV);
		
		Assertions.assertThat(nuevaHV).isNull();
	}
	
	@Test
	public void ActualizarHojaVidaMunicipioInvalido(){		
		HojaVidaDTO nuevaHV = this.servicioHV.buscarHojaVidaPorId(1000L);
		Assertions.assertThat(nuevaHV).isNotNull();
		
		nuevaHV = HojaVidaDTO.builder()
				.numeroDocumento(1000L)
				.apellidos("Gomez")
				.calificacion(8.0)
				.correo("lupita@gmail.com")
				.direccion("Carrera 9na")
				.estadoPersona("PROSPECTO")
				.municipioId(99999L)
				.nitEmpresa("1")
				.nombres("Lupita")
				.telefono("3157856547")
				.tipoDocumento("CEDULA")
				.build();
		
		nuevaHV = this.servicioHV.actualizarHojaVida(nuevaHV);
		
		Assertions.assertThat(nuevaHV).isNull();
	}
	
	@Test
	public void ActualizarHojaVidaCorreoInvalido(){		
		HojaVidaDTO nuevaHV = this.servicioHV.buscarHojaVidaPorId(3000L);
		Assertions.assertThat(nuevaHV).isNotNull();
		
		nuevaHV = HojaVidaDTO.builder()
				.numeroDocumento(3000L)
				.apellidos("Gomez")
				.calificacion(8.0)
				.correo("lupita @gmail.com")
				.direccion("Carrera 9na")
				.estadoPersona("PROSPECTO")
				.municipioId(99999L)
				.nitEmpresa("1")
				.nombres("Lupita")
				.telefono("3157856547")
				.tipoDocumento("CEDULA")
				.build();
		
		nuevaHV = this.servicioHV.actualizarHojaVida(nuevaHV);
		
		Assertions.assertThat(nuevaHV).isNull();
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
