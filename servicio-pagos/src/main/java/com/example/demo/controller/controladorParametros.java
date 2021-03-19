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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.entity.ParametroLegal;
import com.example.demo.service.ServicioParametrosLegales;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Este controlador recibe las peticiones REST que corresponden a la gestion de parametros legales
 * 
 * @author Ruben
 *
 */


@RestController
@RequestMapping (value = "/parametros")
public class controladorParametros {

	@Autowired
	private ServicioParametrosLegales miServicioParametrosLegales;
	
	
	@GetMapping
	public ResponseEntity<List<ParametroLegal>> listarParametrosLegales(){		
		List<ParametroLegal> parametrosEncontrados = miServicioParametrosLegales.listarParametrosLegales();
		
		// No hay parametros registrados
        if(parametrosEncontrados == null){
            return ResponseEntity.noContent().build();
        }        
        
        return ResponseEntity.ok(parametrosEncontrados);
	}
		
	@GetMapping(value = "/buscar-por-id/{id}")
    public ResponseEntity<ParametroLegal> buscarParametroPorId(@PathVariable("id") Long idParametro) {
		ParametroLegal parametro =  miServicioParametrosLegales.buscarParametroPorId(idParametro);
        
		if (parametro == null){
            return ResponseEntity.notFound().build();
        }
		
        return ResponseEntity.ok(parametro);
    }
	
	@GetMapping(value = "/buscar-por-nombre")
    public ResponseEntity<ParametroLegal> buscarParametroPorNombre(@RequestBody ParametroLegal parametro) {
		ParametroLegal parametroEncontrado = null;
		if(parametro != null) {
			if(parametro.getNombre() != null) {
				parametroEncontrado =  miServicioParametrosLegales.buscarParametroPorNombre(parametro.getNombre());
			}
		}
        
		if (parametroEncontrado == null){
            return ResponseEntity.notFound().build();
        }
		
        return ResponseEntity.ok(parametroEncontrado);
    }

	@PostMapping(value = "/crear-parametro")
	public ResponseEntity<ParametroLegal> crearParametroLegal(@Valid @RequestBody ParametroLegal pParametro, BindingResult result){		
		if (result.hasErrors()){     
			System.out.println("\nTiene errores.\n");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        
		ParametroLegal parametro_creado =  miServicioParametrosLegales.agregarParametro(pParametro);        
        
        if (parametro_creado == null){
            return ResponseEntity.badRequest().build();
        }        
        
        return ResponseEntity.status(HttpStatus.CREATED).body(parametro_creado);        
    }

	@PutMapping(value = "/actualizar-parametro")
    public ResponseEntity<ParametroLegal> actualizarParametroLegal(@RequestBody ParametroLegal pParametro){
		ParametroLegal parametro_encontrado =  miServicioParametrosLegales.actualizarParametro(pParametro);
        
		// no se pudo actualizar
		if (parametro_encontrado == null){
            return ResponseEntity. notFound().build();
        }
        return ResponseEntity.ok(parametro_encontrado);
    }
	
	
	
	private String formatMessage( BindingResult result){
		List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
