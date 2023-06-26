package com.projetoIntegradorII.HouseBarber.entity.autenticathion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projetoIntegradorII.HouseBarber.entity.Auditable;
import com.projetoIntegradorII.HouseBarber.enums.Specialties;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Entity
@Data
@NoArgsConstructor
@Table(name = "user_auth")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler" })
public class UserAuth extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "cnpj", unique = true)
    private String cnpj;

    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "userAuth", fetch = FetchType.EAGER)
    private TokenRecovery tokenRecovery;

    @Column(name = "gender")
    private String gender;

    @Column(name = "dateBirth")
    private Date dateBirth;

    @Column(name = "description")
    private String description;

    @Column(name = "telephone")
    private String telephone;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private List<Roles> roles = new ArrayList<>();

}
