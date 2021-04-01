package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.RegistroHoras;

@Repository
public interface RegistroHorasDAO extends JpaRepository<RegistroHoras, Long> {


}
