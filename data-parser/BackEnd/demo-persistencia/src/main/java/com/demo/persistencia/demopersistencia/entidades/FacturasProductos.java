package com.demo.persistencia.demopersistencia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "facturaproducto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FacturasProductosId.class)
public class FacturasProductos {

    @Id
    @Column(name = "int_num_factura")
    private Integer intNumFactura;

    @Id
    @Column(name = "int_id_producto")
    private Integer intIdProducto;

    @Column(name = "int_cantidad")
    private Integer intCantidad;

    @ManyToOne
    @JoinColumn(name = "int_num_factura", referencedColumnName = "int_num_factura", insertable = false, updatable = false)
    private Facturas factura;

    @ManyToOne
    @JoinColumn(name = "int_id_producto", referencedColumnName = "int_id_producto", insertable = false, updatable = false)
    private Productos producto;
}
