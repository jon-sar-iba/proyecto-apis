package com.demo.persistencia.demopersistencia.entidades;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Facturas {

    @Id
    @Column(name = "int_num_factura")
    private Integer intNumFactura;
    @Column(name = "int_num_cliente")
    private Integer intNumCliente;
    @Column(name = "int_cant_tipos_productos")
    private Integer intCantTiposProductos;
    @Column(name = "dec_total")
    private Float decTotal;
    @Column(name = "c_cve_metodo_pago")
    private String cCveMetodoPago;
    @Column(name = "d_fecha")
    private String dFecha;
    @Column(name = "c_cve_estado")
    private String cCveEstado;
    @Column(name = "c_cve_estatus")
    private String cCveEstatus;
}
