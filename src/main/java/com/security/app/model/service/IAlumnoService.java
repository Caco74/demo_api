package com.security.app.model.service;

import java.util.List;

import com.security.app.model.entity.Alumno;

public interface IAlumnoService {
	List<Alumno> getAlumnos();
	Alumno findAlumnoById(Long id);
	Alumno saveAlumno(Alumno alumno);
	void delete(Long id);
}
