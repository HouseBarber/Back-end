package com.projetoIntegradorII.HouseBarber.service.userImage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.entity.userImage.UserImage;
import com.projetoIntegradorII.HouseBarber.repository.authentication.UserAuthRepository;
import com.projetoIntegradorII.HouseBarber.repository.userImage.UserImageRepository;

@Service
public class UserImageServiceImpl implements UserImageService{
    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Override
    public List<UserImage> findAll() {
       return userImageRepository.findAll();
    }

    @Override
    public UserImage inserir(Long idUser, MultipartFile file) {
        UserAuth user = userAuthRepository.findById(idUser).get();
        UserImage userImage = new UserImage();

		try {
			if (!file.isEmpty()) {
				byte[] bytes = file.getBytes();
                String nomeImagem = String.valueOf(user.getId()) + file.getOriginalFilename();
				java.nio.file.Path caminho = Paths
						.get("c:/imagens/" +nomeImagem );
				Files.write(caminho, bytes);
                userImage.setName(nomeImagem);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        userImage.setUser(user);
        userImage = userImageRepository.saveAndFlush(userImage);
        return userImage;
    }

    @Override
    public UserImage alterar(UserImage obj) {
        return userImageRepository.saveAndFlush(obj);
    }

    @Override
    public void excluir(Long id) {
        UserImage obj = userImageRepository.findById(id).get();
        userImageRepository.delete(obj);
    }
    
}
