package com.projetoIntegradorII.HouseBarber.entity.scheduling;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projetoIntegradorII.HouseBarber.entity.Auditable;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.enums.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@NoArgsConstructor
@Table(name = "scheduling")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true, value = { "hibernateLazyInitializer", "handler" })
public class Scheduling extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime date;
    //@JsonFormat(pattern = "HH:mm:ss")
    private LocalDateTime timeInit;
    private LocalDateTime timeEnd;
    private Status status;
    private String title;
    private String observation;

    @ManyToOne
    @JoinColumn(name = "barber_id")
    private UserAuth barber;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserAuth client;
    
}
