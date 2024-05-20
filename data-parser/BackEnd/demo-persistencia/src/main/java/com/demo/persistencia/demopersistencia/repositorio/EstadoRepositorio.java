package com.demo.persistencia.demopersistencia.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.demo.persistencia.demopersistencia.entidades.Estados;

public interface EstadoRepositorio extends CrudRepository<Estados, String>{

}
