package com.example.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Estudio;

@Repository
public interface EstudiosDAO extends JpaRepository<Estudio, Long>  {

}
