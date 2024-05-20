package com.demo.persistencia.demopersistencia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FacturaProductoDto {
    private Integer intNumFactura;
    private Integer intIdProducto;
    private Integer intCantidad;
}
