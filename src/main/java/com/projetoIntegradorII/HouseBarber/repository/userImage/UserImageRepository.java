package com.projetoIntegradorII.HouseBarber.repository.userImage;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoIntegradorII.HouseBarber.entity.userImage.UserImage;

public interface UserImageRepository extends JpaRepository<UserImage, Long>{
    public List<UserImage> findByUserId(Long id);
}
