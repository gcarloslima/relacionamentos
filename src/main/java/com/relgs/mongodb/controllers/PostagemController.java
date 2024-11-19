package com.relgs.mongodb.controllers;

import com.relgs.mongodb.models.Postagem;
import com.relgs.mongodb.repositories.PostagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/postagems")
public class PostagemController {

    private final PostagemRepository postagemRepository;

    @GetMapping
    public List<Postagem> getAll() {
        return postagemRepository.findAll();
    }

    @PostMapping
    public Postagem create(@RequestBody Postagem postagem) {
        return postagemRepository.save(postagem);
    }

    @GetMapping("/{id}")
    public Postagem getById(@PathVariable String id) {
        return postagemRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Postagem update(@PathVariable String id, @RequestBody Postagem postagem) {
        Postagem postagemEncontrada = postagemRepository.findById(id).orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

        if (postagem.getTitulo() != null && !postagem.getTitulo().equals(postagemEncontrada.getTitulo())) {
            postagemEncontrada.setTitulo(postagem.getTitulo());
        }

        if (postagem.getConteudo() != null && !postagem.getConteudo().equals(postagemEncontrada.getConteudo())) {
            postagemEncontrada.setConteudo(postagem.getConteudo());
        }

        postagemEncontrada.setId(id);

        return postagemRepository.save(postagemEncontrada);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        postagemRepository.deleteById(id);
    }

}
