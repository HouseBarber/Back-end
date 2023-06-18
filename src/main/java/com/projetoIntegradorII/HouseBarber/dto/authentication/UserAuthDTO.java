package com.projetoIntegradorII.HouseBarber.dto.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projetoIntegradorII.HouseBarber.enums.Gender;
import com.projetoIntegradorII.HouseBarber.enums.Specialties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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

    private Gender gender;
    private Date dateBirth;
    private String description;
    private AddressDTO adress;

    private List<Specialties> specialties;
    private String createdBy;
    private String lastModifiedBy;
    private Boolean active;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
