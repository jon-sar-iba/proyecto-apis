package com.demo.persistencia.demopersistencia.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estados {
    @Id
    @Column(name = "c_cve_estado")
    private String cCveEstado;
    @Column(name = "cv_nombre")
    private String cvNombre;
}
