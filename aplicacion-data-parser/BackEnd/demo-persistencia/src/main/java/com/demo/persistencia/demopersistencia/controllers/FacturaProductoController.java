package com.demo.persistencia.demopersistencia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.persistencia.demopersistencia.dto.FacturaProductoDto;
import com.demo.persistencia.demopersistencia.entidades.FacturasProductos;
import com.demo.persistencia.demopersistencia.entidades.Productos;
import com.demo.persistencia.demopersistencia.services.FacturaProductoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class FacturaProductoController {

    @Autowired
    private FacturaProductoServicio servicioFacPro;

    @Operation(summary = "Consultar todos los registros existentes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros existentes",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "204", description = "No existen registros.")
    })
    @GetMapping("/listarFacturasProductos")
    public List<FacturasProductos> consultarFacturasProductos(){
        return servicioFacPro.consultarFacturaProducto();
    }

    @Operation(summary = "Nuevo registro.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Registrado exitosamente", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", 
            content = @Content), 
        @ApiResponse(responseCode = "409", description = "Conflicto, el registro ya existe", 
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", 
            content = @Content) })
    @PostMapping("/registrarFacturaProducto")
    private FacturasProductos registrarFacturaProducto(@RequestBody FacturaProductoDto facturaProductoJson){
        FacturasProductos facturaProducto = new FacturasProductos();

        facturaProducto.setIntNumFactura(facturaProductoJson.getIntNumFactura());
        facturaProducto.setIntIdProducto(facturaProductoJson.getIntIdProducto());
        facturaProducto.setIntCantidad(facturaProductoJson.getIntCantidad());

        return servicioFacPro.registFacturaProducto(facturaProducto);
    }
}
