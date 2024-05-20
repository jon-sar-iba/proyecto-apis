package com.demo.persistencia.demopersistencia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.persistencia.demopersistencia.dto.ProductoDto;
import com.demo.persistencia.demopersistencia.entidades.Productos;
import com.demo.persistencia.demopersistencia.repositorio.ProductoRepositorio;
import com.demo.persistencia.demopersistencia.services.ProductoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ProductoController {

    @Autowired//Inyección de dependencia
    private ProductoServicio servicioProducto;
    @Autowired
    private ProductoRepositorio repositorioProducto;

    @Operation(summary = "Consultar todos los productos existentes.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos existentes",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "204", description = "No existen productos registrados")
    })
    @GetMapping("/listarProductos")
    public List<Productos> consultarProductos(){
        return servicioProducto.consultarProducto();
    }
    //Buscar un producto en específico a partir de su idProducto
    @Operation(summary = "Buscar un producto por su clave.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Producto encontrado", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "400", description = "Clave inválida", 
            content = @Content), 
        @ApiResponse(responseCode = "404", description = "No existe el producto", 
            content = @Content) })
    @GetMapping("/buscarProducto/{idProducto}")
    public ResponseEntity<Productos> buscarProducto(@PathVariable Integer idProducto) {
        return repositorioProducto.findById(idProducto).map(ResponseEntity::ok).orElseGet(()
            -> ResponseEntity.notFound().build()
         );
    }
    @Operation(summary = "Registrar un nuevo producto.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Producto registrado exitosamente", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", 
            content = @Content), 
        @ApiResponse(responseCode = "409", description = "Conflicto, el producto ya existe", 
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", 
            content = @Content) })
    @PostMapping("/registrarProducto")
    public Productos registrarProducto(@RequestBody ProductoDto productoJson){
        Productos producto = new Productos();
        //Asignar a la entidad los valores que vienen del Json (lo que trae ProductoDto)
        producto.setIntIdProducto(productoJson.getIntIdProducto());
        producto.setCvNombre(productoJson.getCvNombre());
        producto.setDecPrecioUnitario(productoJson.getDecPrecioUnitario());
        producto.setIntAntiguedadXMes(productoJson.getIntAntiguedadXMes());
        
        return servicioProducto.registProductos(producto);
    }

    @Operation(summary = "Actualizar un producto existente.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Productos.class)) }),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", 
            content = @Content), 
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", 
            content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflicto en la actualización del producto", 
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", 
            content = @Content) })
    @PutMapping("/actualizarProducto")
    public Productos actualizarProducto(@RequestBody ProductoDto productoJson){
        Productos producto = new Productos();
        producto.setIntIdProducto(productoJson.getIntIdProducto());
        producto.setCvNombre(productoJson.getCvNombre());
        producto.setDecPrecioUnitario(productoJson.getDecPrecioUnitario());
        producto.setIntAntiguedadXMes(productoJson.getIntAntiguedadXMes());

        return servicioProducto.actualizarProducto(producto);
    }
    @Operation(summary = "Eliminar un producto por su ID.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente", 
            content = @Content), 
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", 
            content = @Content), 
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", 
            content = @Content), 
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", 
            content = @Content) })
    @DeleteMapping("/eliminarProducto")
    public ResponseEntity<String> eliminarProducto(@RequestBody ProductoDto productoJson) {
        try {
            servicioProducto.eliminaProducto(productoJson.getIntIdProducto());
            return ResponseEntity.ok("Producto eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el producto: " + e.getMessage());
        }
    }
}
