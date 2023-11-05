package com.projetoIntegradorII.HouseBarber.dto.establishment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projetoIntegradorII.HouseBarber.dto.address.AddressDTO;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstablishmentDTO {

    private Long id;

    private String name;

    private String cnpj;

    private String contact;

    private Double billing;

    private String time;
    
    private String daysOpens;

    private String photos;

    private AddressDTO address;

    private String formattedAddress;

}