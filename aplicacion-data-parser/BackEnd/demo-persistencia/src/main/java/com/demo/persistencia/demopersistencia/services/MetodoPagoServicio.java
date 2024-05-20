package com.demo.persistencia.demopersistencia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.persistencia.demopersistencia.entidades.MetodosPagos;
import com.demo.persistencia.demopersistencia.repositorio.MetodoPagoRepositorio;

@Service
public class MetodoPagoServicio {

    @Autowired
    private MetodoPagoRepositorio metodoPagoRepositorio;

    public List<MetodosPagos> consultarMetodoPago(){
        return (List<MetodosPagos>) metodoPagoRepositorio.findAll();
    }
    public MetodosPagos registMetodosPagos(MetodosPagos metodoPago){
        return metodoPagoRepositorio.save(metodoPago);
    }
}
