package com.projetoIntegradorII.HouseBarber.service.user;

import com.projetoIntegradorII.HouseBarber.entity.user.User;
import com.projetoIntegradorII.HouseBarber.exception.InfoException;
import com.projetoIntegradorII.HouseBarber.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findALl() {
        return userRepository.findAll();
    }

    public User insert(User user) {
        user.setCreationDate(new Date());
        User userNew = userRepository.saveAndFlush(user);
        return userNew;
    }

    public User update(User user) {
        user.setUpdateDate(new Date());
        return userRepository.saveAndFlush(user);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
    }

    public User findByID(Long userId) {
        return userRepository.findById( userId)
                .orElseThrow(() -> new InfoException("Exemplo não encontrado", HttpStatus.MULTI_STATUS));
    }
}
