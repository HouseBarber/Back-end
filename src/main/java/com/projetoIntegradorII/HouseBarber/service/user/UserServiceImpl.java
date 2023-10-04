package com.projetoIntegradorII.HouseBarber.service.user;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.address.AddressDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.RolesDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;
import com.projetoIntegradorII.HouseBarber.entity.address.Address;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.entity.roles.Roles;
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

            updateUser(userAuthDTO, userAuth);
            userAuthRepository.save(userAuth);

            infoDTO.setMessage("Atualização realizada com sucesso");
            infoDTO.setStatus(HttpStatus.OK);
            infoDTO.setObject(userAuthDTO);
        } else {
            throw new InfoException("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }

        return infoDTO;
    }

    private void updateUser(UserAuthDTO userAuthDTO, UserAuth userAuth) {
        if (!StringUtil.isNullOrEmpty(userAuthDTO.getName())) {
            userAuth.setName(userAuthDTO.getName());
        }
        if (!StringUtil.isNullOrEmpty(userAuthDTO.getDescription())) {
            userAuth.setDescription(userAuthDTO.getDescription());
        }
        if (!StringUtil.isNullOrEmpty(userAuthDTO.getCpf())) {
            userAuth.setCpf(userAuthDTO.getCpf());
        }
        if (!StringUtil.isNullOrEmpty(userAuthDTO.getCnpj())) {
            userAuth.setCnpj(userAuthDTO.getCnpj());
        }
        if (!StringUtil.isNullOrEmpty(userAuthDTO.getEmail())) {
            userAuth.setEmail(userAuthDTO.getEmail());
        }
        if (!StringUtil.isNullOrEmpty(userAuthDTO.getGender())) {
            userAuth.setGender(userAuthDTO.getGender());
        }
        if (!StringUtil.isNullOrEmpty(userAuthDTO.getTelephone())) {
            userAuth.setTelephone(userAuthDTO.getTelephone());
        }
        if (!StringUtil.isNullOrEmpty(userAuthDTO.getUsername())) {
            userAuth.setUsername(userAuthDTO.getUsername());
        }
        if (userAuthDTO.getDateBirth() != null && !StringUtil.isNullOrEmpty(userAuthDTO.getDateBirth().toString())) {
            userAuth.setDateBirth(userAuthDTO.getDateBirth());
        }
        if (!StringUtil.isNullOrEmpty(userAuthDTO.getRoles().toString())) {
            updateRoles(userAuthDTO.getRoles(), userAuth.getRoles());
        }
    }

    private void updateRoles(List<RolesDTO> list, List<Roles> list2) {
        list.forEach(dto -> {
            list2.stream()
                    .filter(role -> role.getId().equals(dto.getId()))
                    .findFirst()
                    .ifPresent(role -> {
                        if (!StringUtil.isNullOrEmpty(dto.getRole())) {
                            role.setRole(dto.getRole());
                        }
                        if (!StringUtil.isNullOrEmpty(dto.getName())) {
                            role.setName(dto.getName());
                        }
                        if (!StringUtil.isNullOrEmpty(dto.getDescription())) {
                            role.setDescription(dto.getDescription());
                        }
                    });
        });
    }

    private void updateAddress(AddressDTO addressDTO, Address address) {
        if (!StringUtil.isNullOrEmpty(addressDTO.getCep())) {
            address.setCep(addressDTO.getCep());
        }
        if (!StringUtil.isNullOrEmpty(addressDTO.getState())) {
            address.setState(addressDTO.getState());
        }
        if (!StringUtil.isNullOrEmpty(addressDTO.getCity())) {
            address.setCity(addressDTO.getCity());
        }
        if (!StringUtil.isNullOrEmpty(addressDTO.getStreet())) {
            address.setStreet(addressDTO.getStreet());
        }
        if (!StringUtil.isNullOrEmpty(addressDTO.getNeighborhood())) {
            address.setNeighborhood(addressDTO.getNeighborhood());
        }
        if (!StringUtil.isNullOrEmpty(addressDTO.getComplement())) {
            address.setComplement(addressDTO.getComplement());
        }
        if (!StringUtil.isNullOrEmpty(addressDTO.getNumber())) {
            address.setNumber(addressDTO.getNumber());
        }
    }

    @Override
    public UserAuthDTO getUserById(Long id) {
        UserAuthDTO user = new UserAuthDTO();
        try {
            Optional<UserAuth> userAuthOptional = userAuthRepository.findById(id);
            userIsPresent(userAuthOptional, id);
            ModelMapper modelMapper = new ModelMapper();

            user = modelMapper.map(userAuthOptional.get(), UserAuthDTO.class);

        } catch (Exception e) {
            throw new InfoException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return user;
    }

    private void userIsPresent(Optional<UserAuth> userAuth, Long id) {
        if (!userAuth.isPresent()) {
            throw new InfoException("Usuário não encontrado com o ID: " + id, HttpStatus.BAD_REQUEST);
        }
    }

}
