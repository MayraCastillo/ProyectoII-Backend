package com.example.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.Municipio;

@Repository
public interface ImunicipioDAO extends JpaRepository<Municipio, Long> {
	public Municipio findByNombre(String pNombre);
}
