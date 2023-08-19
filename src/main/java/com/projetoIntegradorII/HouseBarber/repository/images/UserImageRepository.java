package com.projetoIntegradorII.HouseBarber.repository.images;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoIntegradorII.HouseBarber.entity.images.UserImage;

public interface UserImageRepository extends JpaRepository <UserImage, Long> {
    UserImage findByUserId(Long userId);
}
