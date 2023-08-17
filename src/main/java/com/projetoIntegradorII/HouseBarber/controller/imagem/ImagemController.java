package com.projetoIntegradorII.HouseBarber.controller.imagem;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.projetoIntegradorII.HouseBarber.entity.imagem.ImagemTeste;
import com.projetoIntegradorII.HouseBarber.repository.imagem.ImagemRepository;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/imagens")
public class ImagemController {

    @Autowired
    private ImagemRepository imagemRepository;

    @PostMapping
    public ResponseEntity<String> uploadImagem(@RequestParam("nome") String nome, @RequestParam("file") MultipartFile arquivo)
            throws IOException {
        ImagemTeste imagem = new ImagemTeste();
        imagem.setNome(nome);
        try {
            imagem.setDados(arquivo.getBytes());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        imagemRepository.save(imagem);

        return ResponseEntity.ok("Imagem salva com sucesso.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImagem(@PathVariable Long id) {
        Optional<ImagemTeste> imagemOptional = imagemRepository.findById(id);
        if (imagemOptional.isPresent()) {
            ImagemTeste imagem = imagemOptional.get();

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + imagem.getNome())
                .contentType(MediaType.IMAGE_JPEG) // Ajuste para o tipo MIME correto da imagem
                .body(imagem.getDados());
        }
        return ResponseEntity.notFound().build();
    }

}
