package com.projetoIntegradorII.HouseBarber.service.authentication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.RolesDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.LoginDTO;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.Roles;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.exception.InfoException;
import com.projetoIntegradorII.HouseBarber.repository.RolesRepository;
import com.projetoIntegradorII.HouseBarber.repository.authentication.UserAuthRepository;
import com.projetoIntegradorII.HouseBarber.security.Jwt.JwtToken;
import com.projetoIntegradorII.HouseBarber.security.Jwt.JwtTokenUtil;
import com.projetoIntegradorII.HouseBarber.security.Response.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private final JwtTokenUtil jwtUtils;
    private final UserAuthRepository userAuthRepository;
    private final RolesRepository rolesRepository;
    private final ObjectMapper objectMapper;

    @Override
    public InfoDTO login(UserAuthDTO userAuthDTO) {

        InfoDTO<LoginDTO> infoDTO = new InfoDTO<>();

        try {
            // Validações -> Mesmo que seja realizada no front, para que não ocorra alguma falha
            // E o usuário manipule os dados, é realizado a verificação no Back-End também.
            if (userAuthDTO.getPassword().equals("")) {
                throw new InfoException("MESSAGES.PASSWORD_REQUIRED", HttpStatus.BAD_REQUEST);
            }

            if (userAuthDTO.getUsername().equals("")) {
                throw new InfoException("MESSAGES.USERNAME_REQUIRED", HttpStatus.BAD_REQUEST);
            }

            if (userAuthDTO.getPassword().length() <= 5) {
                throw new InfoException("MESSAGES.PASSWORD_LENGHT_MIN", HttpStatus.BAD_REQUEST);
            }

            if (userAuthDTO.getPassword().length() >= 100) {
                throw new InfoException("MESSAGES.PASSWORD_LENGHT_MAX", HttpStatus.BAD_REQUEST);
            }

            Optional<UserAuth> user = userAuthRepository.findByUsernameEquals(userAuthDTO.getUsername());

            if (user.isEmpty()) {
                throw new InfoException("MESSAGES.USERNAME_NOT_FOUND", HttpStatus.NOT_FOUND);
            }

            boolean matches = BCrypt.checkpw(userAuthDTO.getPassword(), user.get().getPassword());
            if (!matches) {
                throw new InfoException("MESSAGES.WRONG_PASSWORD", HttpStatus.UNAUTHORIZED);
            }

            List<RolesDTO> roles = objectMapper.convertValue(user.get().getRoles(), new TypeReference<List<RolesDTO>>() {});

            JwtToken jwtToken = new JwtToken();
            jwtToken.setId(user.get().getId().toString());
            jwtToken.setEmail(user.get().getEmail());
            jwtToken.setUsername(user.get().getUsername());
            jwtToken.setName(user.get().getName());

            String jwt = jwtUtils.generateJwtToken(jwtToken);

            JwtResponse jwtResponse = JwtResponse.builder()
                .accessToken(jwt)
                .tokenType("Bearer")
                .userId(user.get().getId())
                .expiresIn(jwtUtils.getExpirationSecondsFromToken(jwt))
                .build();

            LoginDTO loginDto = LoginDTO.builder()
                .jwt(jwtResponse)
                .roles(roles)
                .build();

            infoDTO.setMessage("MESSAGES.LOGIN_SUCESS");
            infoDTO.setStatus(HttpStatus.OK);
            infoDTO.setObject(loginDto);

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
            infoDTO.setMessage("MESSAGES.ERROR_LOGIN");
            return infoDTO;
        }
    }

    @Override
    public InfoDTO register(UserAuthDTO userAuthDTO) {
        InfoDTO<LoginDTO> infoDTO = new InfoDTO<>();

        try {
            validateUserBasicInfo(userAuthDTO);
            validateUserToRegister(userAuthDTO);
            List<Roles> roles = setRolesToNewUser(userAuthDTO);
            UserAuth newUser = UserAuth.builder()
                    .username(userAuthDTO.getUsername())
                    .cpf(userAuthDTO.getCpf())
                    .cnpj(userAuthDTO.getCnpj())
                    .email(userAuthDTO.getEmail())
                    .name(userAuthDTO.getName())
                    .telephone(userAuthDTO.getTelephone())
                    .password(generatePassword(userAuthDTO.getPassword()))
                    .roles(roles)
                    .build();
            userAuthRepository.save(newUser);

            infoDTO.setMessage("MESSAGES.USER_REGISTERED_SUCCESS");
            infoDTO.setStatus(HttpStatus.OK);
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
            infoDTO.setMessage("MESSAGES.ERROR_LOGIN");
            return infoDTO;
        }
    }

    private String generatePassword(String password) {
        String saltGenerator = BCrypt.gensalt();
        return BCrypt.hashpw(password, saltGenerator);
    }

    private void validateUserBasicInfo(UserAuthDTO userAuthDTO) {
        if (userAuthDTO.getPassword().equals("")) {
            throw new InfoException("MESSAGES.PASSWORD_REQUIRED", HttpStatus.BAD_REQUEST);
        }

        if (userAuthDTO.getUsername().equals("")) {
            throw new InfoException("MESSAGES.USERNAME_REQUIRED", HttpStatus.BAD_REQUEST);
        }

        if (userAuthDTO.getPassword().length() <= 5) {
            throw new InfoException("MESSAGES.PASSWORD_LENGHT_MIN", HttpStatus.BAD_REQUEST);
        }

        if (userAuthDTO.getPassword().length() >= 100) {
            throw new InfoException("MESSAGES.PASSWORD_LENGHT_MAX", HttpStatus.BAD_REQUEST);
        }

        if(userAuthDTO.getCpf().equals("") && userAuthDTO.getCnpj().equals("")) {
            throw new InfoException("O usuario deve possuir CPF ou CNPJ", HttpStatus.BAD_REQUEST);
        }

        if(!userAuthDTO.getCpf().equals("") && !userAuthDTO.getCnpj().equals("")) {
            throw new InfoException("O usuario deve possuir somente CPF ou CNPJ", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateUserToRegister(UserAuthDTO userAuthDTO) {
        boolean validateUserByUsername = userAuthRepository.existsByUsername(userAuthDTO.getUsername());
        boolean validateUserByEmail = userAuthRepository.existsByEmail(userAuthDTO.getEmail());
        boolean validateUserByCpf = userAuthRepository.existsByCpf(userAuthDTO.getCpf());
        boolean validateUserByCnpj = userAuthRepository.existsByCnpj(userAuthDTO.getCnpj());

        if (validateUserByUsername) {
            throw new InfoException("MESSAGES.USER_ALREADY_EXISTS", HttpStatus.UNAUTHORIZED);
        }
        if (validateUserByEmail) {
            throw new InfoException("MESSAGES.EMAIL_ALREADY_USED", HttpStatus.UNAUTHORIZED);
        }
        if(userAuthDTO.getCnpj().equals("")){
            if (validateUserByCpf) {
                throw new InfoException("MESSAGES.CPF_ALREADY_REGISTERED", HttpStatus.UNAUTHORIZED);
            }
        }
        if (userAuthDTO.getCpf().equals("")) {
            if (validateUserByCnpj) {
                throw new InfoException("MESSAGES.CNPJ_ALREADY_REGISTERED", HttpStatus.UNAUTHORIZED);
            }
        }
    }

    private List<Roles> setRolesToNewUser(UserAuthDTO userAuthDTO){
        List<Roles> roles = rolesRepository.findAll();
        if (roles.isEmpty()) {
            throw new InfoException("Erro ao buscar roles.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Long roleId = userAuthDTO.getRoles().get(0).getId();
        return roles.stream()
                .filter(role -> role.getId().equals(roleId))
                .toList();
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
        if (userAuthDTO.getCep().equals("")) {
            throw new InfoException("MESSAGES.CEP_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getState().equals("")) {
            throw new InfoException("MESSAGES.STATE_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getCity().equals("")) {
            throw new InfoException("MESSAGES.CITY_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getNeighborhood().equals("")) {
            throw new InfoException("MESSAGES.NEIGHBORHOOD_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getStreet().equals("")) {
            throw new InfoException("MESSAGES.STREET_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getNumber().equals("")) {
            throw new InfoException("MESSAGES.NUMBER_REQUIRED", HttpStatus.BAD_REQUEST);
        }
        if (userAuthDTO.getComplement().equals("")) {
            throw new InfoException("MESSAGES.COMPLEMENT_REQUIRED", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public InfoDTO<UserAuthDTO> update(Long id, UserAuthDTO userAuthDTO) {
        InfoDTO<UserAuthDTO> infoDTO = new InfoDTO<>();
        try {
            validateUserUpdateInfo(userAuthDTO);
            validateAddressUpdateInfo(userAuthDTO);
            Optional<UserAuth> usuarioExistente = userAuthRepository.findById(id);
            if (usuarioExistente.isPresent()) {
                UserAuth userAuth = usuarioExistente.get();

                userAuth.setName(userAuthDTO.getName());
                userAuth.setUsername(userAuthDTO.getUsername());
                userAuth.setEmail(userAuthDTO.getEmail());
                userAuth.setTelephone(userAuthDTO.getTelephone());
                userAuth.setGender(userAuthDTO.getGender());
                userAuth.setDateBirth(userAuthDTO.getDateBirth());
                userAuth.setDescription(userAuthDTO.getDescription());

                userAuth.setCep(userAuthDTO.getCep());
                userAuth.setCity(userAuthDTO.getCity());
                userAuth.setState(userAuthDTO.getState());
                userAuth.setNeighborhood(userAuthDTO.getNeighborhood());
                userAuth.setStreet(userAuthDTO.getStreet());
                userAuth.setComplement(userAuthDTO.getComplement());
                userAuth.setNumber(userAuthDTO.getNumber());
                userAuthRepository.save(userAuth);

                infoDTO.setMessage("Atualização realizada com sucesso");
                infoDTO.setStatus(HttpStatus.OK);
                infoDTO.setObject(userAuthDTO);
            } else {
                throw new InfoException("Usuário não encontrado com o ID: " + id, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new InfoException("Ocorreu um erro ao atualizar o usuário", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return infoDTO;
    }

}

