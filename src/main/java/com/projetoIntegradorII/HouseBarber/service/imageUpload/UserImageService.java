package com.projetoIntegradorII.HouseBarber.service.imageUpload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserImage;

public interface UserImageService {
    List<UserImage> findAll();

    UserImage inserir(Long idUser, MultipartFile file);

    UserImage alterar(UserImage obj);

    void excluir(Long id);
}
