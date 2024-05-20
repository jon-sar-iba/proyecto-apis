package com.demo.persistencia.demopersistencia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.persistencia.demopersistencia.dto.BitacoraDto;
import com.demo.persistencia.demopersistencia.entidades.Bitacoras;
import com.demo.persistencia.demopersistencia.entidades.Productos;
import com.demo.persistencia.demopersistencia.services.BitacoraServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class BitacoraController {

    @Autowired
    private BitacoraServicio servicioBitacora;

    @Operation(summary = "Consultar todas las bitácoras existentes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bitácoras existentes",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "204", description = "No existen bitácoras registradas")
    })
    @GetMapping("/listarBitacoras")
    public List<Bitacoras> consultarBitacora(){
        return servicioBitacora.consultarBicatora();
    }

    @Operation(summary = "Registrar una nueva bitácora.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Bitácora registrada exitosamente", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", 
            content = @Content), 
        @ApiResponse(responseCode = "409", description = "Conflicto, la bitácora ya existe", 
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", 
            content = @Content) })
    @PostMapping("/registrarBitacora")
    public Bitacoras registrarBitacora(@RequestBody BitacoraDto bitacoraJson){
        Bitacoras bitacora = new Bitacoras();

        bitacora.setIntNumFactura(bitacoraJson.getIntNumFactura());
        bitacora.setBRegistroExitoso(bitacoraJson.getBRegistroExitoso());
        bitacora.setCvDescripcion(bitacoraJson.getCvDescripcion());
        bitacora.setDFecha(bitacoraJson.getDFecha());

        return servicioBitacora.registBitacora(bitacora);
    }
}
