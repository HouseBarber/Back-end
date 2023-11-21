package com.projetoIntegradorII.HouseBarber.service.establishment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.repository.authentication.UserAuthRepository;
import com.projetoIntegradorII.HouseBarber.service.Utils.InfoDTO.InfoDTOService;
import org.apache.http.protocol.HTTP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
            if (establishmentDTO.getName().isEmpty()){
                throw new InfoException("NOME REQUERIDO", HttpStatus.BAD_REQUEST);
            }

            if (establishmentDTO.getContact().isEmpty()){
                throw new InfoException("CONTATO REQUERIDO", HttpStatus.BAD_REQUEST);
            }

            if (establishmentDTO.getCnpj().isEmpty()){
                throw new InfoException("CNPJ É NECESSARIO", HttpStatus.BAD_REQUEST);
            }
            Establishment establishment = new Establishment();
            establishment.setName(establishmentDTO.getName());
            establishment.setCnpj(establishmentDTO.getCnpj());
            establishment.setContact(establishmentDTO.getContact());
            establishment.setDaysOpens(establishmentDTO.getDaysOpens());
            establishment.setBilling(establishmentDTO.getBilling());
            establishment.setTime(establishmentDTO.getTime());
            establishmentRepository.save(establishment);
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
            Page<Establishment> establishmentPage = establishmentRepository.findEstablishmentsByUserId(userId, pageable);
            List<EstablishmentDTO> establishmentDTOList = establishmentPage.getContent()
                    .stream()
                    .map(this::convertToDto)
                    .toList();
            Page<EstablishmentDTO> establishmentDTOS = new PageImpl<>(
                    establishmentDTOList,
                    pageable,
                    establishmentPage.getTotalElements()
            );
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
            infoDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            infoDTO.setMessage("Erro Interno");
            return infoDTO;
        }
        return infoDTO;
    }
    @Override
    public InfoDTO<EstablishmentDTO> findEstablishmentById(Long establismentId){
        InfoDTO<EstablishmentDTO> infoEstablishmentDTO = new InfoDTO<EstablishmentDTO>();
        try{
            Optional<Establishment> optionalEstablishment = establishmentRepository.findById(establismentId);
            if(optionalEstablishment.isEmpty()){
                throw new InfoException("Estabelecimento não encontrado", HttpStatus.BAD_REQUEST);
            }
            EstablishmentDTO establishmentDTO = objectMapper.convertValue(optionalEstablishment.get(), EstablishmentDTO.class);
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
    public InfoDTO<EstablishmentDTO> updateEstablishment(EstablishmentDTO establishmentDTO){
        InfoDTO<EstablishmentDTO> infoEstablishmentDTO = new InfoDTO<EstablishmentDTO>();
        try {
            if (establishmentDTO.getName().isEmpty()) {
                throw new InfoException("NOME REQUERIDO", HttpStatus.BAD_REQUEST);
            }

            if (establishmentDTO.getContact().isEmpty()) {
                throw new InfoException("CONTATO REQUERIDO", HttpStatus.BAD_REQUEST);
            }

            if (establishmentDTO.getCnpj().isEmpty()) {
                throw new InfoException("CNPJ É NECESSARIO", HttpStatus.BAD_REQUEST);
            }
            Establishment establishment = objectMapper.convertValue(establishmentDTO, Establishment.class);
            establishmentRepository.save(establishment);

            infoEstablishmentDTO.setObject(establishmentDTO);
            infoEstablishmentDTO.setSuccess(true);
            infoEstablishmentDTO.setMessage("Atualizado com sucesso");

        } catch (InfoException exception){
            infoEstablishmentDTO.setSuccess(false);
            infoEstablishmentDTO.setStatus(HttpStatus.BAD_REQUEST);
            infoEstablishmentDTO.setMessage(exception.getMessage());
            return infoEstablishmentDTO;
        }
        return infoEstablishmentDTO;
    }


    private EstablishmentDTO convertToDto(Establishment establishment) {
        EstablishmentDTO dto = objectMapper.convertValue(establishment, EstablishmentDTO.class);

        String formattedAddress = establishment.getAddress().getState() + ", " +
                establishment.getAddress().getCity() + " - " +
                establishment.getAddress().getStreet();
        dto.setFormattedAddress(formattedAddress);
        return dto;
    }


}
