package com.projetoIntegradorII.HouseBarber.dto.images;

import lombok.Data;

@Data
public class UserImageDTO {

    private Long id;
    private Long userId;
    private byte[] dataImage;
    
}
