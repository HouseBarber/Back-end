package com.projetoIntegradorII.HouseBarber.repository.user;

import com.projetoIntegradorII.HouseBarber.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
