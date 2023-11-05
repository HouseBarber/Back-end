package com.projetoIntegradorII.HouseBarber.entity.autenticathion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projetoIntegradorII.HouseBarber.entity.Auditable;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Data
@NoArgsConstructor
@Table(name = "token_recovery")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
public class TokenRecovery extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "token")
    private String token;

    @OneToOne()
    @JoinColumn(name = "user_auth", referencedColumnName = "id")
    private UserAuth userAuth;

    public boolean isValid(){
        if (lastModifiedDate.isBefore(LocalDateTime.now().plusHours(1))){
            return true;
        }
        return false;
    }
}


