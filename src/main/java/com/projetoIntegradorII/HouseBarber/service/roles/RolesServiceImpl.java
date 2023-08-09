package com.projetoIntegradorII.HouseBarber.service.roles;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.RolesDTO;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.Roles;
import com.projetoIntegradorII.HouseBarber.exception.InfoException;
import com.projetoIntegradorII.HouseBarber.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RolesServiceImpl implements RolesService{

    private final RolesRepository rolesRepository;

    private final ObjectMapper objectMapper;

    @Override
    public List<RolesDTO> getAllRoles() {
        try{
            List<Roles> allRoles = rolesRepository.findAll();
            List<RolesDTO> roles = objectMapper.convertValue(allRoles, new TypeReference<List<RolesDTO>>() {});
            if (roles.isEmpty()) {
                throw new InfoException("LIST ROLES IS EMPY", HttpStatus.BAD_REQUEST);
            }
            return roles;
        } catch (Exception e){
            String errorMessage = "Erro ao buscar as Roles: " + e.getMessage();
            throw new InfoException(errorMessage, HttpStatus.BAD_REQUEST);
        }   
    }
}
