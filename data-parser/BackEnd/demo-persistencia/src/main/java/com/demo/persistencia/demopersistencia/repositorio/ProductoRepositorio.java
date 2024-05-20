package com.demo.persistencia.demopersistencia.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.demo.persistencia.demopersistencia.entidades.Productos;

public interface ProductoRepositorio extends CrudRepository<Productos, Integer>{//Para que funcione como tal, debe heredar, por ende "extends"

}
