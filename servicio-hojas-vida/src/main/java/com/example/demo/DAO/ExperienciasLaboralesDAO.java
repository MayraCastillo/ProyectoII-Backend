package com.example.demo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ExperienciaLaboral;


@Repository
public interface ExperienciasLaboralesDAO extends JpaRepository<ExperienciaLaboral, Long> {

	public List<ExperienciaLaboral> findByNumeroDocumento(Long numeroDocumento);
	
}
