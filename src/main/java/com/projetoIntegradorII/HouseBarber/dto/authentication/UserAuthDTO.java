package com.projetoIntegradorII.HouseBarber.dto.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projetoIntegradorII.HouseBarber.dto.Address.AddressDTO;
import com.projetoIntegradorII.HouseBarber.enums.Gender;
import com.projetoIntegradorII.HouseBarber.enums.Specialties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthDTO {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String cpf;
    private String cnpj;
    private String email;
    private String telephone;
    private List<RolesDTO> roles;
    @JsonIgnore
    private TokenRecoveryDTO tokenRecovery;

    private String gender;
    private Date dateBirth;
    private String description;

    private String cep;
    private String city;
    private String state;
    private String neighborhood;
    private String street;
    private String number;
    private String complement;

    private String createdBy;
    private String lastModifiedBy;
    private Boolean active;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
