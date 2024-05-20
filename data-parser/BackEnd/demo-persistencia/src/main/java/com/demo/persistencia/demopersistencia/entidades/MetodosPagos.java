package com.demo.persistencia.demopersistencia.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "metodopago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetodosPagos {

    @Id
    @Column(name = "c_cve_metodo_pago")
    private String cCveMetodoPago;
    @Column(name = "cv_tipo")
    private String cvTipo;
}
