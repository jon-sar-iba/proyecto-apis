package com.demo.persistencia.demopersistencia.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.demo.persistencia.demopersistencia.entidades.FacturasProductos;
import com.demo.persistencia.demopersistencia.entidades.FacturasProductosId;
//En esta sección recordar que el identificador en una llave compuesta, por ende
public interface FacturaProductoRepositorio extends CrudRepository<FacturasProductos, FacturasProductosId>{

}
