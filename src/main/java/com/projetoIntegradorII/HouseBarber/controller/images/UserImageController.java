package com.projetoIntegradorII.HouseBarber.controller.images;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        UserImageDTO userImageDTO = userImageService.getImage(id);
        if (userImageDTO != null) {
            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(userImageDTO.getDataImage());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id) {
        userImageService.deleteImage(id);
        return ResponseEntity.ok("Imagem excluída com sucesso.");
    }
}
