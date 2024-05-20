package com.demo.persistencia.demopersistencia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.persistencia.demopersistencia.dto.FacturaDto;
import com.demo.persistencia.demopersistencia.entidades.Facturas;
import com.demo.persistencia.demopersistencia.entidades.Productos;
import com.demo.persistencia.demopersistencia.services.FacturaServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class FacturaController {

    @Autowired
    private FacturaServicio servicioFactura;

    @Operation(summary = "Consultar todas las facturas existentes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Facturas existentes",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "204", description = "No existen facturas registradas")
    })
    @GetMapping("/listarFacturas")
    public List<Facturas> consultarFacturas(){
        return servicioFactura.consultarFactura();
    }

    @Operation(summary = "Registrar una nueva factura.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Factura registrada exitosamente", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", 
            content = @Content), 
        @ApiResponse(responseCode = "409", description = "Conflicto, la factura ya existe", 
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", 
            content = @Content) })
    @PostMapping("/registrarFactura")
    public Facturas registrarFactura(@RequestBody FacturaDto facturaJson){
        Facturas factura = new Facturas();

        factura.setIntNumFactura(facturaJson.getIntNumFactura());
        factura.setIntNumCliente(facturaJson.getIntNumCliente());
        factura.setIntCantTiposProductos(facturaJson.getIntCantTiposProductos());
        factura.setDecTotal(facturaJson.getDecTotal());
        factura.setCCveMetodoPago(facturaJson.getCCveMetodoPago());
        factura.setDFecha(facturaJson.getDFecha());
        factura.setCCveEstado(facturaJson.getCCveEstado());
        factura.setCCveEstatus(facturaJson.getCCveEstatus());

        return servicioFactura.registFactura(factura);
    }
}
