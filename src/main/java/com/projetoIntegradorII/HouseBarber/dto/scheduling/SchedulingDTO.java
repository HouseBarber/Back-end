package com.projetoIntegradorII.HouseBarber.dto.scheduling;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.enums.Status;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SchedulingDTO {
    private Long id;

    private LocalDateTime timeInit;

    private LocalDateTime timeEnd;

    private Status status;

    private String observation;

    private UserAuth barber;

    private UserAuth client;
}
