package com.projetoIntegradorII.HouseBarber.controller.images;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.projetoIntegradorII.HouseBarber.dto.images.UserImageDTO;
import com.projetoIntegradorII.HouseBarber.service.images.UserImageService;

@RestController
@RequestMapping("/images")
public class UserImageController {

    @Autowired
    private UserImageService userImageService;
    
    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("userId") Long userId, @RequestParam("file") MultipartFile file)
            throws IOException {
        userImageService.uploadImage(userId, file);
        return ResponseEntity.ok("Imagem do usuário salva com sucesso.");
    }

    @GetMapping("/user/{userId}")
    public @ResponseBody byte[] getImageByUserId(@PathVariable Long userId) throws IOException {
        UserImageDTO userImageDTO = userImageService.getImageByUserId(userId);
        if (userImageDTO != null) {
            return userImageDTO.getDataImage();
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        userImageService.deleteImage(id);
        return ResponseEntity.ok("Imagem excluída com sucesso.");
    }
}
