package com.projetoIntegradorII.HouseBarber.service.images;

import org.springframework.web.multipart.MultipartFile;

import com.projetoIntegradorII.HouseBarber.dto.images.UserImageDTO;

public interface UserImageService {
    UserImageDTO uploadImage(Long userId, MultipartFile file);
    UserImageDTO getImageByUserId(Long userId);
    void deleteImage(Long id);
}
