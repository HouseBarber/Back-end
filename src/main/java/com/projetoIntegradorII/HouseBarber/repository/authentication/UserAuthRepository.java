package com.projetoIntegradorII.HouseBarber.repository.authentication;

import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findByUsernameEquals(String username);

    Optional<UserAuth> findByEmailEqualsIgnoreCase(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByCpf(String cpf);

    boolean existsByCnpj(String cnpj);
}
