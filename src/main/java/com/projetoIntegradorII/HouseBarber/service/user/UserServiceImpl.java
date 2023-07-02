package com.projetoIntegradorII.HouseBarber.service.user;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.LoginDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.exception.InfoException;
import com.projetoIntegradorII.HouseBarber.repository.authentication.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserAuthRepository userAuthRepository;

    @Override
    public InfoDTO<UserAuthDTO> update(Long id, UserAuthDTO userAuthDTO) {
        InfoDTO<UserAuthDTO> infoDTO = new InfoDTO<>();
        try {
            validateUserUpdateInfo(userAuthDTO);
            Optional<UserAuth> userAuthOptional = userAuthRepository.findById(id);
            userIsPresent(userAuthOptional,id);

            UserAuth userAuth = userAuthOptional.get();

            userAuth.setName(userAuthDTO.getName());
            userAuth.setUsername(userAuthDTO.getUsername());
            userAuth.setEmail(userAuthDTO.getEmail());
            userAuth.setTelephone(userAuthDTO.getTelephone());
            userAuth.setGender(userAuthDTO.getGender());
            userAuth.setDateBirth(userAuthDTO.getDateBirth());
            userAuth.setDescription(userAuthDTO.getDescription());

            userAuth.getAddress().setCep(userAuthDTO.getAddressDTO().getCep());
            userAuth.getAddress().setCity(userAuthDTO.getAddressDTO().getCity());
            userAuth.getAddress().setState(userAuthDTO.getAddressDTO().getState());
            userAuth.getAddress().setNeighborhood(userAuthDTO.getAddressDTO().getNeighborhood());
            userAuth.getAddress().setStreet(userAuthDTO.getAddressDTO().getStreet());
            userAuth.getAddress().setComplement(userAuthDTO.getAddressDTO().getComplement());
            userAuth.getAddress().setNumber(userAuthDTO.getAddressDTO().getNumber());
            userAuthRepository.save(userAuth);

            infoDTO.setMessage("Atualização realizada com sucesso");
            infoDTO.setStatus(HttpStatus.OK);
            infoDTO.setObject(userAuthDTO);

        } catch (Exception e) {
            throw new InfoException("Ocorreu um erro ao atualizar o usuário", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return infoDTO;
    }

    @Override
    public InfoDTO<UserAuthDTO> getUserById(Long id) {
        InfoDTO<UserAuthDTO> infoDTO = new InfoDTO<>();
        try {
            Optional<UserAuth> userAuthOptional = userAuthRepository.findById(id);
            userIsPresent(userAuthOptional,id);
            UserAuth userAuth = userAuthOptional.get();
            ModelMapper modelMapper = new ModelMapper();

            UserAuthDTO userAuthDTO = modelMapper.map(userAuth,UserAuthDTO.class);

            infoDTO.setObject(userAuthDTO);
            infoDTO.setStatus(HttpStatus.OK);

        } catch (Exception e){
            throw new InfoException("Ocorreu um erro ao buscar o usuário", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return infoDTO;
    }

    private void validateUserUpdateInfo(UserAuthDTO userAuthDTO) {
        if (userAuthDTO.getUsername().equals("")) {
            throw new InfoException("MESSAGES.USERNAME_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getName().equals("")) {
            throw new InfoException("MESSAGES.NAME_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        /*
        if(userAuthDTO.getCpf().equals("") && userAuthDTO.getCnpj().equals("")) {
            throw new InfoException("O usuario deve possuir CPF ou CNPJ", HttpStatus.BAD_REQUEST);
        }
        if(!userAuthDTO.getCpf().equals("") && !userAuthDTO.getCnpj().equals("")) {
            throw new InfoException("O usuario deve possuir somente CPF ou CNPJ", HttpStatus.BAD_REQUEST);
        }
        */
        if (userAuthDTO.getEmail().equals("")) {
            throw new InfoException("MESSAGES.EMAIL_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getTelephone().equals("")) {
            throw new InfoException("MESSAGES.TELEPHONE_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getGender().equals("")) {
            throw new InfoException("MESSAGES.GENDER_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getDateBirth().equals("")) {
            throw new InfoException("MESSAGES.DATE_BIRTH_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getDescription().equals("")) {
            throw new InfoException("MESSAGES.DESCRIPTION_REQUIRED", HttpStatus.BAD_REQUEST);
        }
    }


    private void validateAddressUpdateInfo(UserAuthDTO userAuthDTO){
        if (userAuthDTO.getAddressDTO().getCep().equals("")) {
            throw new InfoException("MESSAGES.CEP_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getAddressDTO().getState().equals("")) {
            throw new InfoException("MESSAGES.STATE_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getAddressDTO().getCity().equals("")) {
            throw new InfoException("MESSAGES.CITY_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getAddressDTO().getNeighborhood().equals("")) {
            throw new InfoException("MESSAGES.NEIGHBORHOOD_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getAddressDTO().getStreet().equals("")) {
            throw new InfoException("MESSAGES.STREET_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getAddressDTO().getNumber().equals("")) {
            throw new InfoException("MESSAGES.NUMBER_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getAddressDTO().getComplement().equals("")) {
            throw new InfoException("MESSAGES.COMPLEMENT_REQUIRED", HttpStatus.BAD_REQUEST);
        }
    }

    private void userIsPresent(Optional<UserAuth> userAuth,Long id){
        if(!userAuth.isPresent()){
            throw new InfoException("Usuário não encontrado com o ID: " + id, HttpStatus.BAD_REQUEST);
        }
    }

}
