package com.projetoIntegradorII.HouseBarber.dto.authentication;

import lombok.Data;

@Data
public class AddressDTO {
    private String cep;
    private String logradouro;
    private String neighborhood;
    private String city;

    private String street;
    private String state;

}
