package com.demo.persistencia.demopersistencia.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Productos {

    @Id
    @Column(name = "int_id_producto")
    private Integer intIdProducto;
    @Column(name = "cv_nombre")
    private String cvNombre;
    @Column(name = "dec_precio_unitario")
    private Float decPrecioUnitario;
    @Column(name = "int_antiguedad_x_mes")
    private Integer intAntiguedadXMes;
}
