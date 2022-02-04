package com.security.app.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.app.model.entity.Alumno;

@Repository
public interface IAlumnoDao extends JpaRepository<Alumno, Long> {

}
