package com.projetoIntegradorII.HouseBarber.service.user;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.address.AddressDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.LoginDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;
import com.projetoIntegradorII.HouseBarber.entity.address.Address;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.exception.InfoException;
import com.projetoIntegradorII.HouseBarber.repository.authentication.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;

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
            Optional<UserAuth> userAuthOptional = userAuthRepository.findById(userAuthDTO.getId());

            if(userAuthOptional.isEmpty()){
                throw new InfoException("Usuario não encontrado", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            updateStringNotNullAndNotEmpty(userAuthOptional.get()::setName, userAuthDTO.getName());
            updateStringNotNullAndNotEmpty(userAuthOptional.get()::setEmail, userAuthDTO.getEmail());
            updateStringNotNullAndNotEmpty(userAuthOptional.get()::setGender,userAuthDTO.getGender());
            updateStringNotNullAndNotEmpty(userAuthOptional.get()::setUsername,userAuthDTO.getUsername());
            updateStringNotNullAndNotEmpty(userAuthOptional.get()::setTelephone, userAuthDTO.getTelephone());
            updateStringNotNullAndNotEmpty(userAuthOptional.get()::setDescription, userAuthDTO.getDescription());
            updateCPFField(userAuthOptional.get(),userAuthDTO.getCpf());
            updateCNPJField(userAuthOptional.get(),userAuthDTO.getCnpj());
            updateDateNotNullAndNotEmpty(userAuthOptional.get()::setDateBirth,userAuthDTO.getDateBirth());
            updateAddressFields(userAuthOptional.get(), userAuthDTO.getAddress());



         /*


            if(userAuthDTO.getAddress() != null) {
                if(userAuthDTO.getAddress().getNumber() != null && userAuthDTO.getAddress().getNumber().equals("")){
                    userAuthOptional.get().getAddress().setNumber(userAuthDTO.getAddress().getNumber());
                }
                if(userAuthDTO.getAddress().getNeighborhood() != null && userAuthDTO.getAddress().getNeighborhood().equals("")){
                    userAuthOptional.get().getAddress().setNeighborhood(userAuthDTO.getAddress().getNeighborhood());
                }
                if(userAuthDTO.getAddress().getState() != null && userAuthDTO.getAddress().getState().equals("")){
                    userAuthOptional.get().getAddress().setState(userAuthDTO.getAddress().getState());
                }
                if(userAuthDTO.getAddress().getCep() != null && userAuthDTO.getAddress().getCep().equals("")){
                    userAuthOptional.get().getAddress().setCep(userAuthDTO.getAddress().getCep());
                }
                if(userAuthDTO.getAddress().getCity() != null && userAuthDTO.getAddress().getCity().equals("")){
                    userAuthOptional.get().getAddress().setCity(userAuthDTO.getAddress().getCity());
                }
                if(userAuthDTO.getAddress().getComplement() != null && userAuthDTO.getAddress().getComplement().equals("")){
                    userAuthOptional.get().getAddress().setComplement(userAuthDTO.getAddress().getComplement());
                }
                if(userAuthDTO.getAddress().getStreet() != null && userAuthDTO.getAddress().getStreet().equals("")){

                }

                */



           
            userAuthRepository.save(userAuthOptional.get());

            infoDTO.setMessage("Atualização realizada com sucesso");
            infoDTO.setStatus(HttpStatus.OK);
            infoDTO.setObject(userAuthDTO);

        } catch (InfoException e) {
            throw new InfoException(e.getMessage(),e.getStatus());
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
        if (userAuthDTO.getAddress().getCep().equals("")) {
            throw new InfoException("MESSAGES.CEP_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getAddress().getState().equals("")) {
            throw new InfoException("MESSAGES.STATE_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getAddress().getCity().equals("")) {
            throw new InfoException("MESSAGES.CITY_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getAddress().getNeighborhood().equals("")) {
            throw new InfoException("MESSAGES.NEIGHBORHOOD_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getAddress().getStreet().equals("")) {
            throw new InfoException("MESSAGES.STREET_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getAddress().getNumber().equals("")) {
            throw new InfoException("MESSAGES.NUMBER_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getAddress().getComplement().equals("")) {
            throw new InfoException("MESSAGES.COMPLEMENT_REQUIRED", HttpStatus.BAD_REQUEST);
        }
    }

    private void updateStringNotNullAndNotEmpty(Consumer<String> setter, String newValue) {
        if (newValue != null && !newValue.isEmpty()) {
            setter.accept(newValue);
        }
    }

    private void updateDateNotNullAndNotEmpty(Consumer<Date> setter, Date newValue) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

    private void updateCPFField(UserAuth userAuth,String cpf){
        if(cpf != null && !new CPFValidator().isEligible(cpf)){
            throw new InfoException("Informe um CPF Valido", HttpStatus.BAD_REQUEST);
        }
        updateStringNotNullAndNotEmpty(userAuth::setCpf,cpf);
    }

    private void updateCNPJField(UserAuth userAuth,String CNPJ){
        if(CNPJ != null && !new CNPJValidator().isEligible(CNPJ)){
            throw new InfoException("Informe um CNPJ Valido", HttpStatus.BAD_REQUEST);
        }
        updateStringNotNullAndNotEmpty(userAuth::setCnpj,CNPJ);
    }

    private void updateAddressFields(UserAuth userAuth, AddressDTO addressDTO){
        if(addressDTO != null){
            updateStringNotNullAndNotEmpty(userAuth.getAddress()::setCity,userAuth.getAddress().getCity());
            updateStringNotNullAndNotEmpty(userAuth.getAddress()::setCep,userAuth.getAddress().getCep());
            updateStringNotNullAndNotEmpty(userAuth.getAddress()::setComplement,userAuth.getAddress().getComplement());
            updateStringNotNullAndNotEmpty(userAuth.getAddress()::setNeighborhood,userAuth.getAddress().getNeighborhood());
            updateStringNotNullAndNotEmpty(userAuth.getAddress()::setState,userAuth.getAddress().getState());
            updateStringNotNullAndNotEmpty(userAuth.getAddress()::setStreet,userAuth.getAddress().getStreet());
        }
    }
    private void userIsPresent(Optional<UserAuth> userAuth,Long id){
        if(!userAuth.isPresent()){
            throw new InfoException("Usuário não encontrado com o ID: " + id, HttpStatus.BAD_REQUEST);
        }
    }



}
