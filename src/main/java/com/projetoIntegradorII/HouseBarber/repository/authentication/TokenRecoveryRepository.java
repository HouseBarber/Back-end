package com.projetoIntegradorII.HouseBarber.repository.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.TokenRecovery;
import java.util.Optional;

public interface TokenRecoveryRepository extends JpaRepository<TokenRecovery, Long> {
    Optional<TokenRecovery> findByToken(String token);
}
