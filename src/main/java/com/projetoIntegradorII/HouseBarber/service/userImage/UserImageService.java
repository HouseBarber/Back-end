package com.projetoIntegradorII.HouseBarber.service.userImage;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.projetoIntegradorII.HouseBarber.entity.userImage.UserImage;

public interface UserImageService {
    List<UserImage> findAll();

    UserImage inserir(Long idUser, MultipartFile file);

    UserImage alterar(UserImage obj);

    void excluir(Long id);
}
