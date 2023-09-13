package com.projetoIntegradorII.HouseBarber.entity.images;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;

import lombok.Data;

@Entity
@Data
@Table(name = "user_image")
public class UserImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="idUser")
    private UserAuth user;

    @Lob
    private byte[] dataImage;
}
