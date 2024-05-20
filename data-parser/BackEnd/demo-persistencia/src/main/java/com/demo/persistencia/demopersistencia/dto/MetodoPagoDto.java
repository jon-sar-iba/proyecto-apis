package com.demo.persistencia.demopersistencia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetodoPagoDto {
    private String cCveMetodoPago;
    private String cvTipo;
}
