package com.demo.persistencia.demopersistencia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.persistencia.demopersistencia.entidades.FacturasProductos;
import com.demo.persistencia.demopersistencia.repositorio.FacturaProductoRepositorio;

@Service
public class FacturaProductoServicio {

    @Autowired
    private FacturaProductoRepositorio facturaProductoRepositorio;

    public List<FacturasProductos> consultarFacturaProducto(){
        return (List<FacturasProductos>) facturaProductoRepositorio.findAll();
    }
    public FacturasProductos registFacturaProducto(FacturasProductos facturaProducto){
        return facturaProductoRepositorio.save(facturaProducto);
    }
}
