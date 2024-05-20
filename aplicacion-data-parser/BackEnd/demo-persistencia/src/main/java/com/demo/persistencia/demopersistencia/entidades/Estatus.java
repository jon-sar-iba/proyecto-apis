package com.demo.persistencia.demopersistencia.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Estatus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estatus {

    @Id
    @Column(name = "c_cve_estatus")
    private String cCveEstatus;
    @Column(name = "cv_tipo")
    private String cvTipo;
}
