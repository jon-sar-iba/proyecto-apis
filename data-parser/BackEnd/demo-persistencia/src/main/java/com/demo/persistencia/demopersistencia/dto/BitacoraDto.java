package com.demo.persistencia.demopersistencia.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BitacoraDto {
    private Integer intNumFactura;
    private Boolean bRegistroExitoso;
    private String cvDescripcion;
    private Date dFecha;
}
