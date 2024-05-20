package com.demo.persistencia.demopersistencia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.persistencia.demopersistencia.dto.EstatusDto;
import com.demo.persistencia.demopersistencia.entidades.Estatus;
import com.demo.persistencia.demopersistencia.entidades.Productos;
import com.demo.persistencia.demopersistencia.services.EstatusServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class EstatusController {

    @Autowired
    private EstatusServicio servicioEstatus;

    @Operation(summary = "Consultar todos los estatus existentes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estatus existentes",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "204", description = "No existen estatus registrados")
    })
    @GetMapping("/listarEstatus")
    public List<Estatus> consultarEstatus(){
        return servicioEstatus.consultarEstatus();
    }

    @Operation(summary = "Registrar un nuevo estatus.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Estatus registrado exitosamente", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", 
            content = @Content), 
        @ApiResponse(responseCode = "409", description = "Conflicto, el estatus ya existe", 
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", 
            content = @Content) })
    @PostMapping("/registrarEstatus")
    public Estatus registrarEstatus(@RequestBody EstatusDto estatusJson){
        Estatus status = new Estatus();

        status.setCCveEstatus(estatusJson.getCCveEstatus());
        status.setCvTipo(estatusJson.getCvTipo());

        return servicioEstatus.registEstatus(status);
    }
}
