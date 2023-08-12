package com.projetoIntegradorII.HouseBarber.entity.establishment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "establishment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Establishment {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nameFantasy", nullable = true)
    private String name;

    @Column(name = "cnpj", nullable = true)
    private String cnpj;

    @Column(name = "contact", nullable = true)
    private String contact;

    @Column(name = "billing", nullable = false)
    private Double billing;

    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "daysOpens", nullable = false)
    private String daysOpens;

    @Column(name = "photos", nullable = false)
    private String photos;

}
