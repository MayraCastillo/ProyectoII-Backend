package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.HojaVidaDTO;
import com.example.demo.entity.EmpresaExterna;
import com.example.demo.entity.InstitucionEducativa;
import com.example.demo.entity.ReferenciaFamiliar;
import com.example.demo.service.IntServicioEmpresasExternas;
import com.example.demo.service.IntServicioHojasVida;
import com.example.demo.service.IntServicioInstitucionesEducativas;
import com.example.demo.service.IntServicioReferenciasFamiliares;

/**
 * 
 * @author Ruben
 *
 */
@RestController
@RequestMapping(value = "/hojas-vida")
public class controladorHojasVida {
	
	
	/**
	 * Referencia a los servicios disponibles sobre las hojas de vida, 
	 * utilizando inyeccion de dependencias, automatizado por el framework con la decoracion @Autowired
	 */
	@Autowired
    private IntServicioHojasVida miServicioHojasVida;	
	
	/**
	 * Referencia a los servicios disponibles sobre las empresas externas, 
	 * utilizando inyeccion de dependencias, automatizado por el framework con la decoracion @Autowired
	 */
	@Autowired
    private IntServicioEmpresasExternas miServicioEmpresasExternas;	
	
	/**
	 * Referencia a los servicios disponibles sobre las instituciones educativas, 
	 * utilizando inyeccion de dependencias, automatizado por el framework con la decoracion @Autowired
	 */
	@Autowired
    private IntServicioInstitucionesEducativas miServicioInstitucionesEducativas;
	
	@Autowired
    private IntServicioReferenciasFamiliares miServicioReferenciasFamiliares;	
		
	
	
	@GetMapping
	public ResponseEntity<List<HojaVidaDTO>> listarHojasVida(){		
		List<HojaVidaDTO> HojasVidaEncontradas = miServicioHojasVida.listarHojasVida();
		
		// No hay hojas de vida
        if(HojasVidaEncontradas == null){
            return ResponseEntity.noContent().build();
        }        
        
        return ResponseEntity.ok(HojasVidaEncontradas);
	}
			
	@GetMapping(value = "buscar-por-id/{id}")
    public ResponseEntity<HojaVidaDTO> buscarHojaVidaPorId(@PathVariable("id") Long hojaVidaId) {
		HojaVidaDTO hojaVidaEncontrada = miServicioHojasVida.buscarHojaVidaPorId(hojaVidaId);
        
		if (hojaVidaEncontrada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hojaVidaEncontrada);
    }
	
	@GetMapping(value = "buscar-por-correo/{correo}")
    public ResponseEntity<HojaVidaDTO> buscarHojaVidaPorCorreo(@PathVariable("correo") String correo) {
		HojaVidaDTO hojaVidaEncontrada = miServicioHojasVida.buscarHojaVidaPorCorreo(correo);
        
		if (hojaVidaEncontrada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hojaVidaEncontrada);
    }	
	
	@PostMapping
	public ResponseEntity<HojaVidaDTO> crearHojaVida(@Valid @RequestBody HojaVidaDTO pHojaVida){
				
        HojaVidaDTO vHojaVidaEncontrada =  miServicioHojasVida.registrarHojaVida(pHojaVida);       
                     
        return ResponseEntity.status(HttpStatus.CREATED).body(vHojaVidaEncontrada);
    }  
	
	@PutMapping
	public ResponseEntity<HojaVidaDTO> actualizarHojaVida(@Valid @RequestBody HojaVidaDTO pHojaVida){
		
        HojaVidaDTO vHojaVidaEncontrada =  miServicioHojasVida.actualizarHojaVida(pHojaVida);       
                    
        return ResponseEntity.status(HttpStatus.OK).body(vHojaVidaEncontrada);
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
	
	@DeleteMapping(value = "/{id}")
    public ResponseEntity<HojaVidaDTO> eliminarHV(@PathVariable("id") Long id){
		HojaVidaDTO hvEncontrada = miServicioHojasVida.eliminarHojaVida(id);
        
		if(hvEncontrada != null) {
			// Eliminar correcto
			return ResponseEntity.ok(hvEncontrada);
		}else {
			// la hoja de vida no existía, o no era de un prospecto
			return ResponseEntity.status(HttpStatus.CONFLICT).body(hvEncontrada);
		}
    }
	
	@GetMapping(value = "/referencias-familiares")
	public ResponseEntity<List<ReferenciaFamiliar>> listarReferenciasFamiliares(){		
		List<ReferenciaFamiliar> lista = miServicioReferenciasFamiliares.listarReferencias();
		
		// No hay hojas de vida
        if(lista == null){
            return ResponseEntity.noContent().build();
        }        
        
        return ResponseEntity.ok(lista);
	}
	
	/*
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
	*/
}