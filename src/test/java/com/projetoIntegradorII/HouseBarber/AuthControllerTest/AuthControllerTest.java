package com.projetoIntegradorII.HouseBarber.AuthControllerTest;


import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.TokenRecovery;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.repository.authentication.TokenRecoveryRepository;
import com.projetoIntegradorII.HouseBarber.repository.authentication.UserAuthRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private UserAuthRepository userAuthRepository;

    @MockBean
    private TokenRecoveryRepository tokenRecoveryRepository;


    private static final String BASE_URL = "http://localhost:";


    public static String getTestUrl(int port) {
        return BASE_URL + port + "/v1/auth/";
    }

    @Test
    public void isValidToken(){
        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setEmail("viniciusnakahara@gmail.com");
        ResponseEntity<UserAuthDTO> userAuthDTOEntity = restTemplate.postForEntity(getTestUrl(port) + "recoveryPassword", userAuthDTO, UserAuthDTO.class);
        Optional<UserAuth> userAuthOptional = userAuthRepository.findByEmailEqualsIgnoreCase(userAuthDTOEntity.getBody().getEmail());
        if(userAuthOptional.isPresent()){
            Optional<TokenRecovery> tokenRecoveryOptional = tokenRecoveryRepository.findByToken(userAuthOptional.get().getTokenRecovery().getToken());
            assertThat(tokenRecoveryOptional.get().isValid());
            tokenRecoveryOptional.get().setLastModifiedDate(tokenRecoveryOptional.get().getLastModifiedDate().plusHours(2));
            assertThat(!tokenRecoveryOptional.get().isValid());
        }
    }

    @Test
    public void getToken() {
        UserAuthDTO userAuthDTO = new UserAuthDTO();
        userAuthDTO.setEmail("viniciusnakahara@gmail.com");
        ResponseEntity<UserAuthDTO> userAuthDTOEntity = restTemplate.postForEntity(getTestUrl(port) + "recoveryPassword", userAuthDTO, UserAuthDTO.class);
        Optional<UserAuth> userAuthOptional = userAuthRepository.findByEmailEqualsIgnoreCase(userAuthDTOEntity.getBody().getEmail());
        if (userAuthOptional.isPresent()) {
            Optional<TokenRecovery> tokenRecoveryOptional = tokenRecoveryRepository.findByToken(userAuthOptional.get().getTokenRecovery().getToken());
            assertThat(tokenRecoveryOptional.isPresent());
        }


    }
}
