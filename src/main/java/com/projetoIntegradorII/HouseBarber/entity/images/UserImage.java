package com.projetoIntegradorII.HouseBarber.entity.images;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Entity
@Data
@NoArgsConstructor
@Table(name = "user_image")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler" })
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
