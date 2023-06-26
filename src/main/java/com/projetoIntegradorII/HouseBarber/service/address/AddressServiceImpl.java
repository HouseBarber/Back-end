package com.projetoIntegradorII.HouseBarber.service.address;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.Address.AddressDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService{

    static String ADDRESS_API = "https://brasilapi.com.br/api/cep/v1/";


    @Override
    public InfoDTO getAddressByCep(String cep) {
        InfoDTO infoDTO = new InfoDTO();
        String url = ADDRESS_API + cep;
        RestTemplate restTemplate = new RestTemplate();
        AddressDTO addressDTO = restTemplate.getForObject(url, AddressDTO.class);




        return infoDTO;
    }
}
