package com.projetoIntegradorII.HouseBarber.dto.Address;

import lombok.Data;

@Data
public class AddressDTO {

    private String cep;
    private String neighborhood;
    private String city;
    private String street;
    private String state;
    private String service;
}
