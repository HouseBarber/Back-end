package com.projetoIntegradorII.HouseBarber.service.roles;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.RolesDTO;
import com.projetoIntegradorII.HouseBarber.entity.roles.Roles;
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
    public InfoDTO<List<RolesDTO>> getAllRoles() {
        InfoDTO<List<RolesDTO>> infoDTO = new InfoDTO<List<RolesDTO>>();
        try {

            List<Roles> allRoles = rolesRepository.findAll();
            List<RolesDTO> roles = objectMapper.convertValue(allRoles, new TypeReference<List<RolesDTO>>() {});


            infoDTO.setMessage("Sucesso ao buscar roles");
            infoDTO.setStatus(HttpStatus.OK);
            infoDTO.setObject(roles);

            return infoDTO;

        } catch (InfoException e) {
            e.printStackTrace();
            infoDTO.setSuccess(false);
            infoDTO.setStatus(HttpStatus.BAD_REQUEST);
            infoDTO.setMessage(e.getMessage());
            return infoDTO;
        } catch (Exception e) {
            e.printStackTrace();
            infoDTO.setSuccess(false);
            infoDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            infoDTO.setMessage("Erro ao buscar roles");
            return infoDTO;
        }
    }
}
