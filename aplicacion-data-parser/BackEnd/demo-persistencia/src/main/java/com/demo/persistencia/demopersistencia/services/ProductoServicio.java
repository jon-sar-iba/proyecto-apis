package com.demo.persistencia.demopersistencia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.persistencia.demopersistencia.entidades.Productos;
import com.demo.persistencia.demopersistencia.repositorio.ProductoRepositorio;

@Service
public class ProductoServicio {//Con este servicio se podrá interactura con los datos

    @Autowired//Con esto indicamos que es una inyección de dependencia
    private ProductoRepositorio productoRepositorio;//private nombreDeLaInterfaz nombreVariable;

    public List<Productos> consultarProducto(){
        return (List<Productos>) productoRepositorio.findAll();
    }
    public Optional<Productos> consultarProductoPorId(Integer idProducto){
        return productoRepositorio.findById(idProducto);
    }
    public Productos registProductos(Productos producto){
        return productoRepositorio.save(producto);
    }
    public Productos actualizarProducto(Productos producto) {
        // Verificamos si el producto existe en la base de datos
        Optional<Productos> productoExistente = productoRepositorio.findById(producto.getIntIdProducto());

        if (productoExistente.isPresent()) {
            // Si el producto existe, lo actualizamos
            return productoRepositorio.save(producto);
        } else {
            // Si el producto no existe, puedes manejarlo lanzando una excepción o de la forma que consideres adecuada
            throw new RuntimeException("Producto no encontrado con id: " + producto.getIntIdProducto());
        }
    }
    public void eliminaProducto(Integer intIdProducto) throws Exception {
        productoRepositorio.deleteById(intIdProducto);
    }

}
