package com.projetoIntegradorII.HouseBarber.service.user;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.address.AddressDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.exception.InfoException;
import com.projetoIntegradorII.HouseBarber.repository.authentication.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserAuthRepository userAuthRepository;

    private final ObjectMapper objectMapper;

    @Override
    public InfoDTO<UserAuthDTO> update(Long id, UserAuthDTO userAuthDTO) {
        InfoDTO<UserAuthDTO> infoDTO = new InfoDTO<>();

        Optional<UserAuth> userAuthOptional = userAuthRepository.findById(id);

        if (userAuthOptional.isPresent()) {
            UserAuth userAuth = userAuthOptional.get();
            mapUserAuthDTOToUserAuth(userAuthDTO, userAuth);

            userAuthRepository.saveAndFlush(userAuth);

            infoDTO.setMessage("Atualização realizada com sucesso");
            infoDTO.setStatus(HttpStatus.OK);
            infoDTO.setObject(userAuthDTO);
        } else {
            throw new InfoException("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }

        return infoDTO;
    }

    @Override
    public UserAuthDTO getUserById(Long id) {
        UserAuthDTO user = new UserAuthDTO();
        try {
            Optional<UserAuth> userAuthOptional = userAuthRepository.findById(id);
            userIsPresent(userAuthOptional,id);
            ModelMapper modelMapper = new ModelMapper();

            user = modelMapper.map(userAuthOptional.get(),UserAuthDTO.class);

        } catch (Exception e){
            throw new InfoException("Ocorreu um erro ao buscar o usuário", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return user;
    }

    private void userIsPresent(Optional<UserAuth> userAuth,Long id){
        if(!userAuth.isPresent()){
            throw new InfoException("Usuário não encontrado com o ID: " + id, HttpStatus.BAD_REQUEST);
        }
    }

    private void mapUserAuthDTOToUserAuth(UserAuthDTO userAuthDTO, UserAuth userAuth) {
        JsonNode userAuthNode = objectMapper.valueToTree(userAuthDTO);
    
        if (userAuthNode.has("name")) {
            userAuth.setName(userAuthNode.get("name").asText());
        }
        if (userAuthNode.has("email")) {
            userAuth.setEmail(userAuthNode.get("email").asText());
        }
        if (userAuthNode.has("gender")) {
            userAuth.setGender(userAuthNode.get("gender").asText());
        }
        if (userAuthNode.has("username")) {
            userAuth.setUsername(userAuthNode.get("username").asText());
        }
        if (userAuthNode.has("telephone")) {
            userAuth.setTelephone(userAuthNode.get("telephone").asText());
        }
        if (userAuthNode.has("description")) {
            userAuth.setDescription(userAuthNode.get("description").asText());
        }
        if (userAuthNode.has("cpf")) {
            userAuth.setCpf(userAuthNode.get("cpf").asText());
        }
        if (userAuthNode.has("cnpj")) {
            userAuth.setCnpj(userAuthNode.get("cnpj").asText());
        }
        if (userAuthNode.has("dateBirth")) {
            String dateBirthStr = userAuthNode.get("dateBirth").asText();
            Date dateBirth = parseDate(dateBirthStr);
            userAuth.setDateBirth(dateBirth);
        }   
    }

    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}
