package com.demo.persistencia.demopersistencia.entidades;

import java.io.Serializable;
import java.util.Objects;

public class FacturasProductosId implements Serializable {

    private Integer intNumFactura;
    private Integer intIdProducto;

    // Constructors, getters, setters, equals, and hashCode

    public FacturasProductosId() {}

    public FacturasProductosId(Integer intNumFactura, Integer intIdProducto) {
        this.intNumFactura = intNumFactura;
        this.intIdProducto = intIdProducto;
    }

    // Getters and Setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacturasProductosId that = (FacturasProductosId) o;
        return Objects.equals(intNumFactura, that.intNumFactura) && Objects.equals(intIdProducto, that.intIdProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(intNumFactura, intIdProducto);
    }
}