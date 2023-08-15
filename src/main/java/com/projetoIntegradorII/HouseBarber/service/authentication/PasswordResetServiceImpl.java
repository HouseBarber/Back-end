package com.projetoIntegradorII.HouseBarber.service.authentication;

import java.util.Optional;
import java.util.UUID;

import com.projetoIntegradorII.HouseBarber.service.Utils.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.projetoIntegradorII.HouseBarber.dto.InfoDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.TokenRecoveryDTO;
import com.projetoIntegradorII.HouseBarber.dto.authentication.UserAuthDTO;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.TokenRecovery;
import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserAuth;
import com.projetoIntegradorII.HouseBarber.exception.InfoException;
import com.projetoIntegradorII.HouseBarber.repository.authentication.TokenRecoveryRepository;
import com.projetoIntegradorII.HouseBarber.repository.authentication.UserAuthRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PasswordResetServiceImpl implements PasswordResetService {
    
    private final UserAuthRepository userRepository;

    private final TokenRecoveryRepository tokenRecoveryRepository;

    @Autowired
    EmailService emailService;

    @Override
    public InfoDTO<UserAuthDTO> recoveryPassword(UserAuthDTO userDTO) {
        InfoDTO<UserAuthDTO> infoDTO = new InfoDTO<UserAuthDTO>();
        try {

            UserAuth userAuth = validateUserAuthDTO(userDTO);
            TokenRecovery token = generateTokenRecovery();
            createPasswordResetTokenForUser(userAuth, token);
            sendPasswordResetEmail(userAuth);
            infoDTO.setMessage("");
            infoDTO.setStatus(HttpStatus.OK);
            infoDTO.setObject(userDTO);
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
            infoDTO.setMessage("Erro recuperando senhas");
            return infoDTO;
        }
    }

    @Override
    public InfoDTO changePassword(TokenRecoveryDTO tokenRecoveryDTO) {
        InfoDTO<UserAuthDTO> infoDTO = new InfoDTO<UserAuthDTO>();
        try {
            validateTokenRecoveryDTO(tokenRecoveryDTO);
            UserAuth userAuth = getUserByToken(tokenRecoveryDTO);
            changePassword(userAuth, tokenRecoveryDTO);
            tokenRecoveryRepository.deleteById(userAuth.getTokenRecovery().getId());
            infoDTO.setMessage("Alterada com sucesso");
            infoDTO.setStatus(HttpStatus.OK);

            return infoDTO;
        } catch (InfoException e) {
            e.printStackTrace();
            infoDTO.setSuccess(false);
            infoDTO.setStatus(HttpStatus.BAD_REQUEST);
            infoDTO.setMessage(e.getMessage());
            return infoDTO;
        }
    }

    @Override
    public InfoDTO validToken(String token) {
        InfoDTO<TokenRecoveryDTO> infoDTO = new InfoDTO<TokenRecoveryDTO>();
        try {
            Optional<TokenRecovery> tokenRecovery = tokenRecoveryRepository.findByToken(token);

            if (tokenRecovery.isEmpty()) {
                throw new InfoException("Token não encontrado", HttpStatus.NOT_FOUND);
            }
            if (!tokenRecovery.get().isValid()) {
                throw new InfoException("Token expirado", HttpStatus.OK);
            }
            infoDTO.setSuccess(true);
            infoDTO.setMessage("Token válido");
            infoDTO.setStatus(HttpStatus.OK);
            return infoDTO;
        } catch (InfoException e) {
            infoDTO.setSuccess(false);
            infoDTO.setStatus(HttpStatus.BAD_REQUEST);
            infoDTO.setMessage(e.getMessage());
            return infoDTO;
        }
    }

    private TokenRecovery generateTokenRecovery() {
        TokenRecovery tokenRecovery = new TokenRecovery();
        String token = UUID.randomUUID().toString();
        tokenRecovery.setToken(token);
        return tokenRecovery;
    }

    private UserAuth getUserByToken(TokenRecoveryDTO tokenRecoveryDTO) {
        Optional<TokenRecovery> optionalTokenRecovery = tokenRecoveryRepository
                .findByToken(tokenRecoveryDTO.getToken());
        if (optionalTokenRecovery.isEmpty()) {
            throw new InfoException("Token não encontrado", HttpStatus.NOT_FOUND);
        }
        if (!optionalTokenRecovery.get().isValid()) {
            throw new InfoException("Token expirado", HttpStatus.OK);
        }
        TokenRecovery tokenRecovery = optionalTokenRecovery.get();
        return tokenRecovery.getUserAuth();
    }

    private void validateTokenRecoveryDTO(TokenRecoveryDTO tokenRecoveryDTO) throws InfoException {
        if (tokenRecoveryDTO == null || tokenRecoveryDTO.getToken().equals("")) {
            throw new InfoException("Token inválido", HttpStatus.BAD_REQUEST);
        }
        if (tokenRecoveryDTO.getPassword().equals("") || tokenRecoveryDTO.getPassword() == null) {
            throw new InfoException("Informe uma senha valida", HttpStatus.BAD_REQUEST);
        }
    }

    private UserAuth validateUserAuthDTO(UserAuthDTO userDTO) throws InfoException {
        if (userDTO.getEmail() == null || userDTO.getEmail().equals("")) {
            throw new InfoException("Email inválido", HttpStatus.BAD_REQUEST);
        }
        Optional<UserAuth> userAuth = userRepository.findByEmailEqualsIgnoreCase(userDTO.getEmail());
        if (userAuth.isEmpty()) {
            throw new InfoException("Email não encontrado", HttpStatus.OK);
        }
        return userAuth.get();
    }

    @Override
    public void sendPasswordResetEmail(UserAuth user) throws MessagingException {
        String userEmail = user.getEmail();
        String userTokenRecover = user.getTokenRecovery().getToken();
        String subject = "Password Reset Request";
        String text = "To reset your password, click the link below:\n"
                + "http://localhost:4200/changePassword/" + userTokenRecover;
        emailService.sendSimpleMessage(userEmail,subject,text);
    }

    @Override
    public void changePassword(UserAuth user, TokenRecoveryDTO tokenRecoveryDTO) {
        String saltGenerator = BCrypt.gensalt();
        String newPassowrd = BCrypt.hashpw(tokenRecoveryDTO.getPassword(), saltGenerator);
        user.setPassword(newPassowrd);
    }

    private String generatePassword(String password) {
        String saltGenerator = BCrypt.gensalt();
        return BCrypt.hashpw(password, saltGenerator);
    }

    @Override
    public void createPasswordResetTokenForUser(UserAuth user, TokenRecovery token) {
        TokenRecovery currentTokenRecovery = user.getTokenRecovery();

        if (currentTokenRecovery != null) {
            currentTokenRecovery.setToken(token.getToken());
            tokenRecoveryRepository.save(currentTokenRecovery);
            return;
        }
        token.setUserAuth(user);
        user.setTokenRecovery(token);
        tokenRecoveryRepository.save(token);
        userRepository.save(user);
    }


}
