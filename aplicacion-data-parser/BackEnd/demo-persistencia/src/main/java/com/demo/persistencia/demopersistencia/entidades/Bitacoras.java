package com.demo.persistencia.demopersistencia.entidades;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bitacora")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bitacoras {

    @Id
    @Column(name = "intIdBitacora")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer intIdBitacora;
    @Column(name = "int_num_factura")
    private Integer intNumFactura;
    @Column(name = "b_registro_exitoso")
    private Boolean bRegistroExitoso;
    @Column(name = "cv_descripcion")
    private String cvDescripcion;
    @Column(name = "d_fecha")
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dFecha;

    @PrePersist
    protected void onCreate() {
        dFecha = new Date();
    }
}
