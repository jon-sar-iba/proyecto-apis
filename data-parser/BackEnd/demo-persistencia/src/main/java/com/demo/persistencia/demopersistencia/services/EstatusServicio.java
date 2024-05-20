package com.demo.persistencia.demopersistencia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.persistencia.demopersistencia.entidades.Estatus;
import com.demo.persistencia.demopersistencia.repositorio.EstatusRepositorio;

@Service
public class EstatusServicio {

    @Autowired
    private EstatusRepositorio statusRepositorio;
    public List<Estatus> consultarEstatus(){
        return (List<Estatus>) statusRepositorio.findAll();
    }
    public Estatus registEstatus(Estatus status){
        return statusRepositorio.save(status);
    }
}
