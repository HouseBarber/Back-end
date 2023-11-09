package com.projetoIntegradorII.HouseBarber.service.scheduling;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.scheduling.SchedulingDTO;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.entity.scheduling.Scheduling;
import com.projetoIntegradorII.HouseBarber.exception.InfoException;
import com.projetoIntegradorII.HouseBarber.repository.authentication.UserAuthRepository;
import com.projetoIntegradorII.HouseBarber.repository.scheduling.SchedulingRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SchedulingServiceImpl implements SchedulingService{

    private final UserAuthRepository userAuthRepository;

    private final SchedulingRepository schedulingRepository;

    private final ObjectMapper objectMapper;

    @Override
    public InfoDTO<SchedulingDTO> created(SchedulingDTO schedulingDTO) {
        InfoDTO<SchedulingDTO> infoDTO = new InfoDTO<>();
        try {
            Scheduling scheduling = new Scheduling();
            scheduling.setTimeInit(schedulingDTO.getTimeInit());
            scheduling.setTimeEnd(schedulingDTO.getTimeEnd());
            scheduling.setStatus(schedulingDTO.getStatus());
            scheduling.setBarber(schedulingDTO.getBarber());
            scheduling.setClient(schedulingDTO.getClient());
            scheduling.setObservation(schedulingDTO.getObservation());
            schedulingRepository.save(scheduling);
            infoDTO.setMessage("Sucesso amigao 2");
            infoDTO.setObject(schedulingDTO);
            infoDTO.setStatus(HttpStatus.CREATED);        
            return infoDTO;
        }
        catch (InfoException e){
            infoDTO.setSuccess(false);
            infoDTO.setStatus(HttpStatus.BAD_REQUEST);
            infoDTO.setMessage(e.getMessage());
            return infoDTO;
        }
        catch (Exception e) {
            infoDTO.setSuccess(false);
            infoDTO.setStatus(HttpStatus.BAD_REQUEST); 
            infoDTO.setMessage("Erro Interno");  
            return infoDTO;  
        }
    }

    @Override
    public InfoDTO<SchedulingDTO> findSchedulingByID(Long id){
        InfoDTO<SchedulingDTO> infoEstablishmentDTO = new InfoDTO<SchedulingDTO>();
        try{
            Optional<Scheduling> optionalEstablishment = schedulingRepository.findById(id);
            if(optionalEstablishment.isEmpty()){
                throw new InfoException("Agendamento não encontrado rapá", HttpStatus.BAD_REQUEST);
            }
            SchedulingDTO establishmentDTO = objectMapper.convertValue(optionalEstablishment.get(), SchedulingDTO.class);
            infoEstablishmentDTO.setSuccess(true);
            infoEstablishmentDTO.setStatus(HttpStatus.OK);
            infoEstablishmentDTO.setObject(establishmentDTO);
        } catch (InfoException infoException){
            infoEstablishmentDTO.setSuccess(false);
            infoEstablishmentDTO.setStatus(HttpStatus.BAD_REQUEST);
            infoEstablishmentDTO.setMessage(infoException.getMessage());
            return infoEstablishmentDTO;
        } catch (Exception exception){
            infoEstablishmentDTO.setSuccess(false);
            infoEstablishmentDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            infoEstablishmentDTO.setMessage("Erro Interno");
            return infoEstablishmentDTO;
        }
        return infoEstablishmentDTO;
    }

    @Override
    public InfoDTO<SchedulingDTO> updated(SchedulingDTO schedulingDTO){
        InfoDTO<SchedulingDTO> infoEstablishmentDTO = new InfoDTO<SchedulingDTO>();
        try {
           
            Scheduling scheduling = objectMapper.convertValue(schedulingDTO, Scheduling.class);
            schedulingRepository.save(scheduling);

            infoEstablishmentDTO.setObject(schedulingDTO);
            infoEstablishmentDTO.setSuccess(true);
            infoEstablishmentDTO.setMessage("100% Atualizado com sucesso");

        } catch (InfoException exception){
            infoEstablishmentDTO.setSuccess(false);
            infoEstablishmentDTO.setStatus(HttpStatus.BAD_REQUEST);
            infoEstablishmentDTO.setMessage(exception.getMessage());
            return infoEstablishmentDTO;
        }
        return infoEstablishmentDTO;
    }
    
}
