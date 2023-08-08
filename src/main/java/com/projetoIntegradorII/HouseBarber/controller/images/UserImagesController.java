package com.projetoIntegradorII.HouseBarber.controller.images;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.projetoIntegradorII.HouseBarber.entity.autenticathion.UserImage;
import com.projetoIntegradorII.HouseBarber.service.imageUpload.UserImageService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/image")
@Api(tags = {"Images Controller"})
@RequiredArgsConstructor
public class UserImagesController {

    private final UserImageService userImageService;

    @GetMapping("/")
    public List<UserImage> findAll(){
       return userImageService.findAll();
    }

    @PostMapping("/")
    public UserImage inserir(@RequestParam("idUser") Long idUser, @RequestParam("file") MultipartFile file){
        return userImageService.inserir(idUser, file);
    }

    @PutMapping("/")
    public UserImage alterar(@RequestBody UserImage obj){
        return userImageService.alterar(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
        userImageService.excluir(id);
        return ResponseEntity.ok().build();
    }
}
