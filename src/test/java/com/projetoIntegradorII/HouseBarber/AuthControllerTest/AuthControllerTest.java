package com.projetoIntegradorII.HouseBarber.AuthControllerTest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.TokenRecovery;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.repository.authentication.TokenRecoveryRepository;
import com.projetoIntegradorII.HouseBarber.repository.authentication.UserAuthRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private TokenRecoveryRepository tokenRecoveryRepository;


    private static final String BASE_URL = "http://localhost:";


    public static String getTestUrl(int port) {
        return BASE_URL + port + "/v1/auth/";
    }

    @Test
    public void isValidToken() throws JsonProcessingException {
        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setEmail("viniciusnakahara@gmail.com");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userAuthDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<InfoDTO<UserAuthDTO>> userAuthDTOEntity = restTemplate.exchange(getTestUrl(port) + "recoveryPassword", HttpMethod.POST,entity,
                new ParameterizedTypeReference<InfoDTO<UserAuthDTO>>() {});
        Optional<UserAuth> userAuthOptional = userAuthRepository.findByEmailEqualsIgnoreCase(userAuthDTOEntity.getBody().getObject().getEmail());
        if(userAuthOptional.isPresent()){
            Optional<TokenRecovery> tokenRecoveryOptional = tokenRecoveryRepository.findByToken(userAuthOptional.get().getTokenRecovery().getToken());
            assertThat(tokenRecoveryOptional.get().isValid());
            tokenRecoveryOptional.get().setLastModifiedDate(tokenRecoveryOptional.get().getLastModifiedDate().plusHours(2));
            assertThat(!tokenRecoveryOptional.get().isValid());
        }
    }

    @Test
    public void getToken() throws JsonProcessingException {
        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setEmail("viniciusnakahara@gmail.com");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userAuthDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<InfoDTO<UserAuthDTO>> userAuthDTOEntity = restTemplate.exchange(getTestUrl(port) + "recoveryPassword", HttpMethod.POST,entity,
                new ParameterizedTypeReference<InfoDTO<UserAuthDTO>>() {});
        Optional<UserAuth> userAuthOptional = userAuthRepository.findByEmailEqualsIgnoreCase(userAuthDTOEntity.getBody().getObject().getEmail());
        if (userAuthOptional.isPresent()) {
            Optional<TokenRecovery> tokenRecoveryOptional = tokenRecoveryRepository.findByToken(userAuthOptional.get().getTokenRecovery().getToken());
            assertThat(tokenRecoveryOptional.isPresent());
        }
    }
}
