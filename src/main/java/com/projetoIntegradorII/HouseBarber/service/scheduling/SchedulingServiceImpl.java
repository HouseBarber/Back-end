package com.projetoIntegradorII.HouseBarber.service.scheduling;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoIntegradorII.HouseBarber.dto.scheduling.SchedulingDTO;
import com.projetoIntegradorII.HouseBarber.entity.scheduling.Scheduling;
import com.projetoIntegradorII.HouseBarber.exception.InfoException;
import com.projetoIntegradorII.HouseBarber.repository.scheduling.SchedulingRepository;

@Service
public class SchedulingServiceImpl implements SchedulingService{

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public SchedulingDTO create(SchedulingDTO schedulingDTO) {
        try {
            validateScheduling(schedulingDTO);
            Scheduling newObj = Scheduling.builder()
                .title(schedulingDTO.getTitle())
                .observation(schedulingDTO.getObservation())
                .timeInit(schedulingDTO.getTimeInit())
                .timeEnd(schedulingDTO.getTimeEnd())
                .date(schedulingDTO.getDate())
                .status(schedulingDTO.getStatus())
                .client(schedulingDTO.getClient())
                .barber(schedulingDTO.getBarber())
                .build();
            schedulingRepository.save(newObj);
            return objectMapper.convertValue(newObj, new TypeReference<SchedulingDTO>() {});
        } catch (Exception e) {
            throw new InfoException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public SchedulingDTO getById(Long id) {
        try {
            Scheduling scheduling = schedulingRepository.findById(id).orElseThrow();
            return objectMapper.convertValue(scheduling, new TypeReference<SchedulingDTO>() {});
        } catch (Exception e) {
            throw new InfoException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public SchedulingDTO update(Long id, SchedulingDTO schedulingDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

     private void validateScheduling(SchedulingDTO schedulingDTO) throws Exception{
        if(schedulingDTO.getDate().isEqual(null)){
            throw new Exception("Campo date é obrigatório");
        }
        if(schedulingDTO.getTimeInit().isEqual(null)){
            throw new Exception("Campo timeInit é obrigatório");
        }
        if(schedulingDTO.getTimeEnd().isEqual(null)){
            throw new Exception("Campo timeEnd é obrigatório");
        }
        if(schedulingDTO.getBarber() == null){
            throw new Exception("Campo date é obrigatório");
        }
        if(schedulingDTO.getClient() == null){
            throw new Exception("Campo client é obrigatório");
        }
        if(schedulingDTO.getStatus() == null){
            throw new Exception("Campo date é obrigatório");
        }
        if(schedulingDTO.getTitle() == null){
            throw new Exception("Campo title é obrigatório");
        }
        if(schedulingDTO.getObservation() == null){
            throw new Exception("Campo observation é obrigatório");
        }
     }
    
}
