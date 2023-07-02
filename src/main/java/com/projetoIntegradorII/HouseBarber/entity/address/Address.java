package com.projetoIntegradorII.HouseBarber.entity.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projetoIntegradorII.HouseBarber.entity.Auditable;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Data
@NoArgsConstructor
@Table(name = "address")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
public class Address extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "cep")
    private String cep;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private String number;

    @Column(name = "complement")
    private String complement;


    @OneToOne(mappedBy = "address")
    private UserAuth userAuth;
}