package com.projetoIntegradorII.HouseBarber.entity.images;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;

import lombok.Builder;
import lombok.Data;

@Builder
@Entity
@Data
@Table(name = "user_image")
public class UserImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "user_auth", referencedColumnName = "id")
    private UserAuth userAuth;

    @Lob
    private byte[] dataImage;
}
