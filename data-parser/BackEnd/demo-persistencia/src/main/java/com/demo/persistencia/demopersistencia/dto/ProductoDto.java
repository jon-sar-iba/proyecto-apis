package com.demo.persistencia.demopersistencia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoDto {//Clase que recibirá los datos en formato Json por medio del controlador
    private Integer intIdProducto;
    private String cvNombre;
    private Float decPrecioUnitario;
    private Integer intAntiguedadXMes;
}
