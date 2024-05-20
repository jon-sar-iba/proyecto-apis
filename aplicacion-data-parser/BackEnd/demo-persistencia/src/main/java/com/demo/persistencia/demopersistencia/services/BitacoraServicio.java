package com.demo.persistencia.demopersistencia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.persistencia.demopersistencia.entidades.Bitacoras;
import com.demo.persistencia.demopersistencia.repositorio.BitacoraRepositorio;

@Service
public class BitacoraServicio {

    @Autowired
    private BitacoraRepositorio bitacoraRepositorio;

    public List<Bitacoras> consultarBicatora(){
        return (List<Bitacoras>) bitacoraRepositorio.findAll();
    }
    public Bitacoras registBitacora(Bitacoras bitacora){
        return bitacoraRepositorio.save(bitacora);
    }
}
