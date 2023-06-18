package com.projetoIntegradorII.HouseBarber.service.address;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.AddressDTO;

import java.net.MalformedURLException;

public interface AddressService {
    InfoDTO getAddressByCep(String cep) throws MalformedURLException;
}
