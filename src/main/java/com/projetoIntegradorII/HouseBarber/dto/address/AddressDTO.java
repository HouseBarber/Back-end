package com.projetoIntegradorII.HouseBarber.dto.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {

    private Long id;
    private String cep;
    private String city;
    private String state;
    private String neighborhood;
    private String street;
    private String number;
    private String complement;
}
