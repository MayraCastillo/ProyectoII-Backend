package com.example.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.Tercero;

@Repository
public interface IterceroDAO extends JpaRepository<Tercero, Long>{
	public Tercero findByNombre(String pNombre);
}
