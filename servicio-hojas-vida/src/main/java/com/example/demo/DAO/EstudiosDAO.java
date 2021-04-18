package com.example.demo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Estudio;

@Repository
public interface EstudiosDAO extends JpaRepository<Estudio, Long>  {

	public List<Estudio> findByNumeroDocumento(Long numeroDocumento);
	
}
