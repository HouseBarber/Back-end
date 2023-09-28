package com.projetoIntegradorII.HouseBarber.repository.images;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetoIntegradorII.HouseBarber.entity.images.UserImage;

public interface UserImageRepository extends JpaRepository <UserImage, Long> {
    Optional<UserImage> findByUserAuthId(Long userId);
}
