package com.demo.persistencia.demopersistencia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.persistencia.demopersistencia.dto.MetodoPagoDto;
import com.demo.persistencia.demopersistencia.entidades.MetodosPagos;
import com.demo.persistencia.demopersistencia.entidades.Productos;
import com.demo.persistencia.demopersistencia.services.MetodoPagoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoServicio servicioMetPago;

    @Operation(summary = "Consultar todos los métodos de pagos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Métodos de pagos existentes",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "204", description = "No existen métodos de pagos registrados")
    })
    @GetMapping("/listarMetodosPago")
    public List<MetodosPagos> consultarMetodosPagos(){
        return servicioMetPago.consultarMetodoPago();
    }

    @Operation(summary = "Registrar un nuevo método de pago.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Método de pago registrado exitosamente", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", 
            content = @Content), 
        @ApiResponse(responseCode = "409", description = "Conflicto, el método de pago ya existe", 
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", 
            content = @Content) })
    @PostMapping("/registrarMetodoPago")
    public MetodosPagos registrarMetodoPago(@RequestBody MetodoPagoDto metodoPagoJson){
        MetodosPagos metodoPago = new MetodosPagos();
        
        metodoPago.setCCveMetodoPago(metodoPagoJson.getCCveMetodoPago());
        metodoPago.setCvTipo(metodoPagoJson.getCvTipo());

        return servicioMetPago.registMetodosPagos(metodoPago);
    }
}
