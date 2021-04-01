package com.example.demo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.InstitucionEducativa;


@Repository
public interface InstitucionesEducativasDAO extends JpaRepository<InstitucionEducativa, Long> {

	public List<InstitucionEducativa> findByNombreIgnoreCase(String nombre);
	
}
