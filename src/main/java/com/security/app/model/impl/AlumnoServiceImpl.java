package com.security.app.model.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.app.model.dao.IAlumnoDao;
import com.security.app.model.entity.Alumno;
import com.security.app.model.service.IAlumnoService;

@Service
public class AlumnoServiceImpl implements IAlumnoService {

	@Autowired
	private IAlumnoDao alumnoDao;
	
	@Override
	public List<Alumno> getAlumnos() {
		return alumnoDao.findAll();
	}

	@Override
	public Alumno findAlumnoById(Long id) {
		return alumnoDao.findById(id).orElse(null);
	}

	@Override
	public Alumno saveAlumno(Alumno alumno) {
		return alumnoDao.save(alumno);
	}

	@Override
	public void delete(Long id) {
		alumnoDao.deleteById(id);
	}
	
}
