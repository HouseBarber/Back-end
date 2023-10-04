package com.projetoIntegradorII.HouseBarber.service.establishment;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.RolesDTO;
import com.projetoIntegradorII.HouseBarber.dto.establishment.EstablishmentDTO;
import com.projetoIntegradorII.HouseBarber.entity.establishment.Establishment;
import com.projetoIntegradorII.HouseBarber.exception.InfoException;
import com.projetoIntegradorII.HouseBarber.repository.establishment.EstablishmentRepository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EstablishmentServiceImpl implements EstablishmentService{
    
    private final EstablishmentRepository establishmentRepository;

    private final ObjectMapper objectMapper;

    @Override
    public InfoDTO<EstablishmentDTO> createEstablishment(EstablishmentDTO establishmentDTO) {
        InfoDTO<EstablishmentDTO> infoDTO = new InfoDTO<>();

        try {
            if (establishmentDTO.getName().equals("") || establishmentDTO.getName() == null){
                throw new InfoException("NOME REQUERIDO", HttpStatus.BAD_REQUEST);
            }

            if (establishmentDTO.getContact().equals("") || establishmentDTO.getContact() == null){
                throw new InfoException("CONTATO REQUERIDO", HttpStatus.BAD_REQUEST);
            }

            if (establishmentDTO.getCnpj().equals("") || establishmentDTO.getCnpj() == null){
                throw new InfoException("CNPJ É NECESSARIO", HttpStatus.BAD_REQUEST);
            }

            Establishment establishment = new Establishment();

            establishment.setName(establishmentDTO.getName());
            establishment.setCnpj(establishmentDTO.getCnpj());
            establishment.setContact(establishmentDTO.getContact());
            establishment.setDaysOpens(establishmentDTO.getDaysOpens());
            establishment.setBilling(establishmentDTO.getBilling());
            establishment.setTime(establishmentDTO.getTime());
            

            establishmentRepository.saveAndFlush(establishment);

            infoDTO.setMessage("Sucesso amigao");
            infoDTO.setObject(establishmentDTO);
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
            e.printStackTrace();
            infoDTO.setSuccess(false);
            infoDTO.setStatus(HttpStatus.BAD_REQUEST); 
            infoDTO.setMessage("Erro Interno");  
            return infoDTO;  
        }
    }

    @Override
    public InfoDTO<List<EstablishmentDTO>> findAllEstablishment(Long idUser) {
        InfoDTO<List<EstablishmentDTO>> infoDTO = new InfoDTO<>();
        try {

            Optional<List<Establishment>> establishmentList = establishmentRepository.findByUserAuthId(idUser);
            
            if(establishmentList.isPresent()){
                infoDTO.setSuccess(true);
                infoDTO.setStatus(HttpStatus.BAD_REQUEST);
                return infoDTO;
            }
            List<EstablishmentDTO> establishmentsDTO = objectMapper.convertValue(establishmentList, new TypeReference<List<EstablishmentDTO>>() {});
            infoDTO.setSuccess(true);
            infoDTO.setStatus(HttpStatus.OK);
            infoDTO.setObject(establishmentsDTO);
        }  catch (InfoException e){
            infoDTO.setSuccess(false);
            infoDTO.setStatus(HttpStatus.BAD_REQUEST);
            infoDTO.setMessage(e.getMessage());
            return infoDTO;
        }
       
        return infoDTO;
    }
    
}