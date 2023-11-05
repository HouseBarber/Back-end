package com.projetoIntegradorII.HouseBarber.service.images;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoIntegradorII.HouseBarber.dto.images.UserImageDTO;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.entity.images.UserImage;
import com.projetoIntegradorII.HouseBarber.exception.InfoException;
import com.projetoIntegradorII.HouseBarber.repository.authentication.UserAuthRepository;
import com.projetoIntegradorII.HouseBarber.repository.images.UserImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserImageServiceImpl  implements UserImageService{

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    private final ObjectMapper objectMapper;

    @Override
    public UserImageDTO uploadImage(Long userId, MultipartFile file){
        Optional<UserImage> userImage = userImageRepository.findByUserAuthId(userId);
        try {
            Optional<UserAuth> user = userAuthRepository.findById(userId);
            if(user.isEmpty()){
                throw new InfoException("USUÁRIO NÃO ENCONTRADO", HttpStatus.BAD_REQUEST);
            }
            if(userImage.isPresent()){
                userImage.get().setDataImage(file.getBytes());
                userImageRepository.save(userImage.get());
                return objectMapper.convertValue(userImage.get(), new TypeReference<UserImageDTO>() {});
            }
            UserImage newUserImage = UserImage.builder()
                .dataImage(file.getBytes())
                .userAuth(user.get())
                .build();
            userImageRepository.save(newUserImage);
            return objectMapper.convertValue(newUserImage, new TypeReference<UserImageDTO>() {});
        } catch (Exception e) {
            throw new InfoException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void deleteImage(Long id) {
        userImageRepository.deleteById(id);
    }

    @Override
    public UserImageDTO getImageByUserId(Long userId) {
        Optional<UserImage> userImage = userImageRepository.findByUserAuthId(userId);
        return userImage.<UserImageDTO>map(image -> objectMapper.convertValue(image, new TypeReference<>() {
        })).orElse(null);
    }
    
}
