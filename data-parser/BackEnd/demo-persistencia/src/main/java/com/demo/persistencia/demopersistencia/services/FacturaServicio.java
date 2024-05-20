package com.demo.persistencia.demopersistencia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.persistencia.demopersistencia.entidades.Facturas;
import com.demo.persistencia.demopersistencia.repositorio.FacturaRepositorio;

@Service
public class FacturaServicio {

    @Autowired
    private FacturaRepositorio facturaRepositorio;

    public List<Facturas> consultarFactura(){
        return (List<Facturas>) facturaRepositorio.findAll();
    }
    public Facturas registFactura(Facturas factura){
        return facturaRepositorio.save(factura);
    }
}
