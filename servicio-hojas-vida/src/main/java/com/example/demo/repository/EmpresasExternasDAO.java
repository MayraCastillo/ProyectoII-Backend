package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.EmpresaExterna;

/**
 * Acceso a la tabla de  empresas externas, que almacena las empresas donde un empleado
 * logró experiencia laboral
 * 
 * @author Ruben
 *
 */

@Repository
public interface EmpresasExternasDAO  extends JpaRepository<EmpresaExterna, Long> {
		
}
