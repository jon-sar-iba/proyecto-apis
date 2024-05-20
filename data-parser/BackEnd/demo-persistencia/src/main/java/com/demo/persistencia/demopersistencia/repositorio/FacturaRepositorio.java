package com.demo.persistencia.demopersistencia.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.demo.persistencia.demopersistencia.entidades.Facturas;

public interface FacturaRepositorio extends CrudRepository<Facturas, Integer>{

}
