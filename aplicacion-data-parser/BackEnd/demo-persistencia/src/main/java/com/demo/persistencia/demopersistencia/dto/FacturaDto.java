package com.demo.persistencia.demopersistencia.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FacturaDto {
    private Integer intNumFactura;
    private Integer intNumCliente;
    private Integer intCantTiposProductos;
    private Float decTotal;
    private String cCveMetodoPago;
    private String dFecha;
    private String cCveEstado;
    private String cCveEstatus;
}
