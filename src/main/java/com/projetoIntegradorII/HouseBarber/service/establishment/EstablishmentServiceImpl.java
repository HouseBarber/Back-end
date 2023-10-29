package com.projetoIntegradorII.HouseBarber.service.establishment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.repository.authentication.UserAuthRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.establishment.EstablishmentDTO;
import com.projetoIntegradorII.HouseBarber.entity.establishment.Establishment;
import com.projetoIntegradorII.HouseBarber.exception.InfoException;
import com.projetoIntegradorII.HouseBarber.repository.establishment.EstablishmentRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EstablishmentServiceImpl implements EstablishmentService{

    private final UserAuthRepository userAuthRepository;
    
    private final EstablishmentRepository establishmentRepository;

    private final ObjectMapper objectMapper;

    @Override
    public InfoDTO<EstablishmentDTO> creatEstablishment(EstablishmentDTO establishmentDTO) {
        InfoDTO<EstablishmentDTO> infoDTO = new InfoDTO<>();

        try {
            if (establishmentDTO.getName().isEmpty() || establishmentDTO.getName() == null){
                throw new InfoException("NOME REQUERIDO", HttpStatus.BAD_REQUEST);
            }

            if (establishmentDTO.getContact().isEmpty() || establishmentDTO.getContact() == null){
                throw new InfoException("CONTATO REQUERIDO", HttpStatus.BAD_REQUEST);
            }

            if (establishmentDTO.getCnpj().isEmpty() || establishmentDTO.getCnpj() == null){
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
    public InfoDTO<Page<EstablishmentDTO>> listEstablishment(Long userId, Pageable pageable) {
        InfoDTO<Page<EstablishmentDTO>> infoDTO = new InfoDTO<>();

        try {
            Optional<UserAuth> userAuth = userAuthRepository.findById(userId);
            if (userAuth.isEmpty()){
                throw new InfoException("Usuario com esse Id não encontrado",HttpStatus.OK);
            }
            Page<Establishment> establishmentList = establishmentRepository.findEstablishmentsByUser(userAuth.get());
            Page<EstablishmentDTO> establishmentDTOS = objectMapper.convertValue(establishmentList, new TypeReference<Page<EstablishmentDTO>>() {});

            infoDTO.setStatus(HttpStatus.OK);
            infoDTO.setObject(establishmentDTOS);
            infoDTO.setMessage("Mensagem");
        } catch (InfoException exception){
            infoDTO.setSuccess(false);
            infoDTO.setStatus(HttpStatus.BAD_REQUEST);
            infoDTO.setMessage(exception.getMessage());
            return infoDTO;
        } catch (Exception e) {
            infoDTO.setSuccess(false);
            infoDTO.setStatus(HttpStatus.BAD_REQUEST);
            infoDTO.setMessage("Erro Interno");
            return infoDTO;
        }
        return infoDTO;
    }

}
