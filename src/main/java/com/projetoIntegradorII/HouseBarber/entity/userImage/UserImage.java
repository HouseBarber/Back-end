package com.projetoIntegradorII.HouseBarber.entity.userImage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;

import lombok.Data;

@Entity
@Table(name = "user_image")
@Data
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="idUser")
    private UserAuth user;
    
    @Transient
    private byte[] arquivo;

}
