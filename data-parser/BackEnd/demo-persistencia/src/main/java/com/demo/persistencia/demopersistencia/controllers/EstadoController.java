package com.demo.persistencia.demopersistencia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.persistencia.demopersistencia.dto.EstadoDto;
import com.demo.persistencia.demopersistencia.entidades.Estados;
import com.demo.persistencia.demopersistencia.entidades.Productos;
import com.demo.persistencia.demopersistencia.services.EstadoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class EstadoController {

    @Autowired
    private EstadoServicio servicioEstado;

    @Operation(summary = "Consultar todos los estados existentes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estados existentes",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "204", description = "No existen estados registrados")
    })
    @GetMapping("/listarEstados")
    public List<Estados> consultarEstados(){
        return servicioEstado.consultarEstado();
    }

    @Operation(summary = "Registrar un nuevo estado.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Estado registrado exitosamente", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", 
            content = @Content), 
        @ApiResponse(responseCode = "409", description = "Conflicto, el estado ya existe", 
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", 
            content = @Content) })
    @PostMapping("/registrarEstado")
    public Estados registrarEstados(@RequestBody EstadoDto estadoJson){
        Estados estado = new Estados();
        //Asignar a la entidad los valores correspondientes
        estado.setCCveEstado(estadoJson.getCCveEstado());
        estado.setCvNombre(estadoJson.getCvNombre());

        return servicioEstado.registEstados(estado);
    }
}
