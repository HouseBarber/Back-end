package com.projetoIntegradorII.HouseBarber.dto.scheduling;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.enums.Status;
import lombok.Data;

@Data
public class SchedulingDTO {
    private Long id;

    private Date timeInit;

    private Date timeEnd;

    private Status status;

    private String observation;

    private UserAuth barber;

    private UserAuth client;
}
