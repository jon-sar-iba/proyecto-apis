package com.demo.persistencia.demopersistencia.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.demo.persistencia.demopersistencia.entidades.Estatus;

public interface EstatusRepositorio extends CrudRepository<Estatus, String> {

}
