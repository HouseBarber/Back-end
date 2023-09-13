package com.projetoIntegradorII.HouseBarber.service.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.address.AddressDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.RolesDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;
import com.projetoIntegradorII.HouseBarber.entity.address.Address;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.exception.InfoException;
import com.projetoIntegradorII.HouseBarber.repository.address.AddressRepository;
import com.projetoIntegradorII.HouseBarber.repository.authentication.UserAuthRepository;
import com.projetoIntegradorII.HouseBarber.service.Utils.StringUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserAuthRepository userAuthRepository;

    private final AddressRepository addressRepository;

    @Override
    public InfoDTO<UserAuthDTO> update(Long id, UserAuthDTO userAuthDTO) {
        InfoDTO<UserAuthDTO> infoDTO = new InfoDTO<>();
        
        Optional<UserAuth> userAuthOptional = userAuthRepository.findById(id);
        
        if (userAuthOptional.isPresent()) {
            UserAuth userAuth = userAuthOptional.get();

            AddressDTO addressDTO = userAuthDTO.getAddress();
            if (addressDTO != null) {
                Address address = userAuth.getAddress(); 
                if (address == null) {
                    address = new Address(); 
                }
                updateAddress(addressDTO, address); 
                addressRepository.save(address);
                userAuth.setAddress(address);
            } 

            userAuthRepository.save(userAuth);

            infoDTO.setMessage("Atualização realizada com sucesso");
            infoDTO.setStatus(HttpStatus.OK);
            infoDTO.setObject(userAuthDTO);
        } else {
            throw new InfoException("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }

        return infoDTO;
    }

    private void updateAddress(AddressDTO addressDTO, Address address) {
        if(!StringUtil.isNullOrEmpty(addressDTO.getCep())){
            address.setCep(addressDTO.getCep());
        }
        if(!StringUtil.isNullOrEmpty(addressDTO.getState())){
            address.setState(addressDTO.getState());
        }
        if(!StringUtil.isNullOrEmpty(addressDTO.getCity())){
            address.setCity(addressDTO.getCity());
        }
        if(!StringUtil.isNullOrEmpty(addressDTO.getStreet())){
            address.setStreet(addressDTO.getStreet());
        }      
        if(!StringUtil.isNullOrEmpty(addressDTO.getNeighborhood())){
            address.setNeighborhood(addressDTO.getNeighborhood());
        }
        if(!StringUtil.isNullOrEmpty(addressDTO.getComplement())){
            address.setComplement(addressDTO.getComplement());
        }
        if(!StringUtil.isNullOrEmpty(addressDTO.getNumber())){
            address.setNumber(addressDTO.getNumber());
        }
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

}
