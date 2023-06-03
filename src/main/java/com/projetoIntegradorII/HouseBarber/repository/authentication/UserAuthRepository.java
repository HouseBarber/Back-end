package com.projetoIntegradorII.HouseBarber.repository.authentication;

import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findByUsernameEquals(String username);

    Optional<UserAuth> findByEmailEqualsIgnoreCase(String email);

}
