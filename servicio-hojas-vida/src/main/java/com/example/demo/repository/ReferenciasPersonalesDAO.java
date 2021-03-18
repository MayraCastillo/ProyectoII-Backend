package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ReferenciaPersonal;

@Repository
public interface ReferenciasPersonalesDAO   extends JpaRepository<ReferenciaPersonal, Long>{

}
