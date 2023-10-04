package com.projetoIntegradorII.HouseBarber.dto.images;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserImageDTO {

    private Long id;
    
    private UserAuthDTO userAuth;

    private byte[] dataImage;
    
}
