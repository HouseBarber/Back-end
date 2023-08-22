package com.projetoIntegradorII.HouseBarber.service.images;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.projetoIntegradorII.HouseBarber.dto.images.UserImageDTO;
import com.projetoIntegradorII.HouseBarber.entity.images.UserImage;
import com.projetoIntegradorII.HouseBarber.repository.authentication.UserAuthRepository;
import com.projetoIntegradorII.HouseBarber.repository.images.UserImageRepository;

import io.jsonwebtoken.io.IOException;

@Service
public class UserImageServiceImpl  implements UserImageService{

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Override
    public UserImageDTO uploadImage(Long userId, MultipartFile file) throws IOException {
        UserImage userImage = new UserImage();
        userImage.setUser(userAuthRepository.findById(userId).orElse(null));
        try {
            userImage.setDataImage(file.getBytes());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        userImage = userImageRepository.save(userImage);

        return convertToDTO(userImage);
    }

    @Override
    public void deleteImage(Long id) {
        userImageRepository.deleteById(id);
    }

    @Override
    public UserImageDTO getImage(Long id) {
        Optional<UserImage> userImageOptional = userImageRepository.findById(id);
        if (userImageOptional.isPresent()) {
            UserImage userImage = userImageOptional.get();
            return convertToDTO(userImage);
        }
        return null;
    }

    @Override
    public UserImageDTO getImageByUserId(Long userId) {
        UserImage userImage = userImageRepository.findByUserId(userId);
        if (userImage != null) {
            return convertToDTO(userImage);
        }
        return null;
    }

    private UserImageDTO convertToDTO(UserImage userImage) {
        UserImageDTO userImageDTO = new UserImageDTO();
        userImageDTO.setId(userImage.getId());
        userImageDTO.setUserId(userImage.getUser().getId());
        userImageDTO.setDataImage(userImage.getDataImage());
        return userImageDTO;
    }
    
}
