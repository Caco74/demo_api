package com.security.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.app.model.entity.Alumno;
import com.security.app.model.service.IAlumnoService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"*"}) // Para todas las peticiones, evito los errores de peticiones que vienen de diferentes origenes.
public class AlumnoController {
	
	@Autowired
	private IAlumnoService alumnoService;
	
	@GetMapping("/list")
	public List<Alumno> index() {
		return alumnoService.getAlumnos();
	}
	
	//Buscar alumno
	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Alumno alumno = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			alumno = alumnoService.findAlumnoById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error de acceso a la base de datos");
			response.put("error", e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (alumno == null) {
			response.put("mensaje", "El alumno no se encuentra en el sistema");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Alumno>(alumno, HttpStatus.OK);
	}
	
	//Guardar alumno
	@PostMapping("/save")
	public ResponseEntity<?> create(@RequestBody Alumno alumno) {
		Alumno alumnoNuevo = null;
		// Mapeo de la clave y el valor
		Map<String, Object> response = new HashMap<>();
		
		try {
			alumnoNuevo = alumnoService.saveAlumno(alumno); 
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar el alumno en la base de datos");
			response.put("error", e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Alumno creado con éxito");
		response.put("alumno", alumnoNuevo);
		
		return new ResponseEntity<Alumno>(alumno, HttpStatus.CREATED);
	}
	
	//Actualizar alumno
	@PutMapping("/update/{id}")
	public  ResponseEntity<?> update(@RequestBody Alumno alumno, @PathVariable Long id) {

		Alumno alum = alumnoService.findAlumnoById(id);
		Alumno alumnoActualizado = null;
		Map<String, Object> response = new HashMap<>();
		
		if (alum == null) {
			response.put("mensaje", "No se ha podido actualizar al alumno, porque no existe en el sistema.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			alum.setApellido(alumno.getApellido());
			alum.setCurso(alumno.getCurso());
			alum.setNota(alumno.getNota());
			
			alumnoActualizado = alumnoService.saveAlumno(alum);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el alumno en la base de datos");
			response.put("error", e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		response.put("mensaje", "Alumno actualizado con éxito");
		response.put("alumnoActualizado", alumnoActualizado);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	//Eliminar alumno
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			alumnoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el alumno en la base de datos");
			response.put("error", e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Alumno eliminado con éxito");		
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
