package com.demo.persistencia.demopersistencia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.persistencia.demopersistencia.entidades.Estados;
import com.demo.persistencia.demopersistencia.repositorio.EstadoRepositorio;

@Service
public class EstadoServicio {

    @Autowired
    private EstadoRepositorio estadoRepositorio;

    public List<Estados> consultarEstado(){
        return (List<Estados>) estadoRepositorio.findAll();
    }
    public Estados registEstados(Estados estado){
        return estadoRepositorio.save(estado);
    }
}
