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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.entity.EmpresaExterna;
import com.example.demo.entity.HojaVida;
import com.example.demo.entity.InstitucionEducativa;
import com.example.demo.service.ServicioEmpresasExternas;
import com.example.demo.service.ServicioHojasVida;
import com.example.demo.service.ServicioInstitucionesEducativas;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Ruben
 *
 */
@RestController
@RequestMapping (value = "/hojas-vida")
public class controladorHojasVida {
	
	
	/**
	 * Referencia a los servicios disponibles sobre las hojas de vida, 
	 * utilizando inyeccion de dependencias, automatizado por el framework con la decoracion @Autowired
	 */
	@Autowired
    private ServicioHojasVida miServicioHojasVida;	
	
	/**
	 * Referencia a los servicios disponibles sobre las empresas externas, 
	 * utilizando inyeccion de dependencias, automatizado por el framework con la decoracion @Autowired
	 */
	@Autowired
    private ServicioEmpresasExternas miServicioEmpresasExternas;	
	
	/**
	 * Referencia a los servicios disponibles sobre las instituciones educativas, 
	 * utilizando inyeccion de dependencias, automatizado por el framework con la decoracion @Autowired
	 */
	@Autowired
    private ServicioInstitucionesEducativas miServicioInstitucionesEducativas;	
		
	
	
	@GetMapping
	public ResponseEntity<List<HojaVida>> listarHojasVida(){		
		List<HojaVida> HojasVidaEncontradas = miServicioHojasVida.listarHojasVida();
		
		// No hay hojas de vida
        if(HojasVidaEncontradas == null){
            return ResponseEntity.noContent().build();
        }        
        
        return ResponseEntity.ok(HojasVidaEncontradas);
	}
			
	@PostMapping
	public ResponseEntity<HojaVida> crearHojaVida(@Valid @RequestBody HojaVida pHojaVida, BindingResult result){
		
		if (result.hasErrors()){ 			
			System.out.println("\n\nTiene errores.\n\n");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
		
        HojaVida vHojaVidaEncontrada =  miServicioHojasVida.registrarHojaVida(pHojaVida);        
        
        if (vHojaVidaEncontrada == null){
        	System.out.println("Error");
        	return ResponseEntity.badRequest().body(vHojaVidaEncontrada);
        }        
        
        return ResponseEntity.status(HttpStatus.CREATED).body(vHojaVidaEncontrada);        
    }    
	
	
	@GetMapping(value="/empresas-externas")
	public ResponseEntity<List<EmpresaExterna>> listarEmpresasExternas(){		
		List<EmpresaExterna> empresasExternasEncontradas = miServicioEmpresasExternas.listarEmpresasExternas();
		
        if(empresasExternasEncontradas == null){
            return ResponseEntity.noContent().build();
        }        
        
        return ResponseEntity.ok(empresasExternasEncontradas);
	}
		
	@GetMapping(value="/instituciones-educativas")
	public ResponseEntity<List<InstitucionEducativa>> listarInstitucionesEducativas(){		
		List<InstitucionEducativa> ieEncontradas = miServicioInstitucionesEducativas.listarInstitucionesEducativas();
		
        if(ieEncontradas == null){
            return ResponseEntity.noContent().build();
        }        
        
        return ResponseEntity.ok(ieEncontradas);
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
