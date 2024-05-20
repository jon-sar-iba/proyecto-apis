package com.demo.persistencia.demopersistencia.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.demo.persistencia.demopersistencia.entidades.MetodosPagos;

public interface MetodoPagoRepositorio extends CrudRepository<MetodosPagos, String>{

}
