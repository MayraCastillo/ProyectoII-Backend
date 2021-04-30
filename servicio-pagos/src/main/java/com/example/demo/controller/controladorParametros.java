package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.DetalleNomina;
import com.example.demo.entity.FactoresSalariales;
import com.example.demo.entity.ParametroLegal;
import com.example.demo.model.EmpleadoNominaP;
import com.example.demo.service.IservicioNominaP;
import com.example.demo.service.ServicioParametrosLegales;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Este controlador recibe las peticiones REST que corresponden a la gestion de
 * parametros legales
 * 
 * @author Ruben
 *
 */

@RestController
@RequestMapping(value = "/parametros")
public class controladorParametros {

	@Autowired
	private ServicioParametrosLegales miServicioParametrosLegales;
	
	@Autowired
	private IservicioNominaP servicioNominaP;

	@PostMapping(value = "/crear-parametro")
	public ResponseEntity<ParametroLegal> crearParametroLegal(@Valid @RequestBody ParametroLegal pParametro) {
		
		ParametroLegal parametro_creado = miServicioParametrosLegales.agregarParametro(pParametro);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(parametro_creado);
	}
	
	@PostMapping(value = "/generarDetalleNomina")
	public ResponseEntity<DetalleNomina> generarNomina(@RequestBody @Valid EmpleadoNominaP pEmpleadoNomina) 
	{

		DetalleNomina nomina = servicioNominaP.generarDetalleNomina(pEmpleadoNomina);
		if (nomina == null) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(nomina);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(nomina);
	}

	@PutMapping(value = "/actualizar-parametro")
	public ResponseEntity<ParametroLegal> actualizarParametroLegal(@RequestBody @Valid ParametroLegal pParametro) {
		ParametroLegal parametro_encontrado = miServicioParametrosLegales.actualizarParametro(pParametro);

		// no se pudo actualizar
		if (parametro_encontrado == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(parametro_encontrado);
	}

	@GetMapping(value = "/buscar-por-id/{id}")
	public ResponseEntity<ParametroLegal> buscarParametroPorId(@PathVariable("id") Long idParametro) {
		ParametroLegal parametro = miServicioParametrosLegales.buscarParametroPorId(idParametro);

		if (parametro == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(parametro);
	}
	
	@GetMapping(value = "/pagarDetalleNominaEmpleado/{pContratoId}/{pNominaId}")
	public ResponseEntity<DetalleNomina> PagarDetalleNominaEmpleado(@PathVariable Long pContratoId, @PathVariable Long pNominaId)
	{
		DetalleNomina detalleNomina = servicioNominaP.pagarDetalleNominaEmpleado(pContratoId, pNominaId);
		if(detalleNomina == null) 
		{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(detalleNomina);
		}
		return ResponseEntity.status(HttpStatus.OK).body(detalleNomina);
	}
	

	@GetMapping(value = "/buscar-por-nombre")
	public ResponseEntity<ParametroLegal> buscarParametroPorNombre(@RequestBody ParametroLegal parametro) {
		ParametroLegal parametroEncontrado = null;
		if (parametro != null) {
			if (parametro.getNombre() != null) {
				parametroEncontrado = miServicioParametrosLegales.buscarParametroPorNombre(parametro.getNombre());
			}
		}

		if (parametroEncontrado == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(parametroEncontrado);
	}
	
	@PostMapping(value = "/guardarDetalleNomina")
	public ResponseEntity<DetalleNomina> guardarDeatalleNomina(@RequestBody DetalleNomina pDetalleNomina) {
		DetalleNomina detalleNomina = servicioNominaP.guardarDetalleNomina(pDetalleNomina);
		if (detalleNomina == null) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(detalleNomina);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(detalleNomina);
	}

	@GetMapping
	public ResponseEntity<List<ParametroLegal>> listarParametrosLegales() {
		List<ParametroLegal> parametrosEncontrados = miServicioParametrosLegales.listarParametrosLegales();

		// No hay parametros registrados
		if (parametrosEncontrados == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(parametrosEncontrados);
	}
	
	@GetMapping(value = "/listarDetallesNominas")
	public ResponseEntity<List<DetalleNomina>> listarDetallesNominas()
	{
		List<DetalleNomina> listarDetallesNominas = servicioNominaP.listarDetallesNominas();
		if(listarDetallesNominas.size()==0){
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(listarDetallesNominas);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listarDetallesNominas);
	}
	
	@GetMapping(value = "/listarFactoresSalariales")
	public ResponseEntity<List<FactoresSalariales>> listarFactoresSalariales()
	{
		List<FactoresSalariales> listFacSalariales = servicioNominaP.listarFactoresSalariales();
		if(listFacSalariales.size()==0){
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(listFacSalariales);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listFacSalariales);
	}
	
	@GetMapping(value = "/listarDetallesNominaPorPeriodo")
	public ResponseEntity<List<DetalleNomina>> listarDetallesNominaPorPeriodo(@RequestParam String pFechaInicio, @RequestParam String pFechaFin)
	{
		List<DetalleNomina> listDetNomPorPerido = servicioNominaP.listarDetallesNominaPorPeriodo(pFechaInicio, pFechaFin);
		if(listDetNomPorPerido.size()==0){
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(listDetNomPorPerido);
		}
		return ResponseEntity.status(HttpStatus.OK).body(listDetNomPorPerido);
	}
	

	private String formatMessage(BindingResult result) {
		List<Map<String, String>> errors = result.getFieldErrors().stream().map(err -> {
			Map<String, String> error = new HashMap<>();
			error.put(err.getField(), err.getDefaultMessage());
			return error;

		}).collect(Collectors.toList());
		ErrorMessage errorMessage = ErrorMessage.builder().code("01").messages(errors).build();
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";

		try {
			jsonString = mapper.writeValueAsString(errorMessage);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

}