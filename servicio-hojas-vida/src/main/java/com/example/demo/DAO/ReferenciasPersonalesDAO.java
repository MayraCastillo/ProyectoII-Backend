package com.example.demo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ReferenciaPersonal;

@Repository
public interface ReferenciasPersonalesDAO   extends JpaRepository<ReferenciaPersonal, Long>{
	
	public List<ReferenciaPersonal> findByNumeroDocumento(Long numeroDocumento);

}
