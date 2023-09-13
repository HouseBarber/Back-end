package com.projetoIntegradorII.HouseBarber.service.images;

import org.springframework.web.multipart.MultipartFile;

import com.projetoIntegradorII.HouseBarber.dto.images.UserImageDTO;

import io.jsonwebtoken.io.IOException;

public interface UserImageService {
    UserImageDTO uploadImage(Long userId, MultipartFile file) throws IOException;
    UserImageDTO getImage(Long id);
    UserImageDTO getImageByUserId(Long userId);
    void deleteImage(Long id);
}
