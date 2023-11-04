package com.empresa.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.entity.Empleado;
import com.empresa.repository.EmpleadoRepository;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

	@Autowired
	private EmpleadoRepository repository;
	
	@Override
	public Empleado insertaEmpleado(Empleado obj) {
		return repository.save(obj);
	}

	@Override
	public Empleado actualizaEmpleado(Empleado obj) {
		return repository.save(obj);
	}

	@Override
	public List<Empleado> listaPorNombreApellidoLike(String filtro) {
		return repository.listaEmpleadoNombreApellidoLike(filtro);
	}

	@Override
	public Optional<Empleado> buscaEmpleado(int idEmpleado) {
		return repository.findById(idEmpleado);
	}

	@Override
	public List<Empleado> listaPorNombreApellidoIgual(String nombre, String apellido) {
		return repository.listaEmpleadoNombreApellidoIgual(nombre, apellido);
	}

	@Override
	public List<Empleado> listaPorNombreApellidoIgualActualiza(String nombre, String apellido, int idEmpleado) {
		return repository.listaEmpleadoNombreApellidoIgualActualiza(nombre, apellido, idEmpleado);
	}

	//SEMANA 11- LUEGO DE SERVICE
	@Override
	public List<Empleado> listaConsultaEmpleado(int estado, int idPais, String nomApe, Date desde, Date hasta) {
		return repository.listaConsultaEmpleado(estado, idPais, nomApe, desde, hasta);
	}

}
