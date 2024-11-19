package com.relgs.mongodb.controllers;

import com.relgs.mongodb.models.Perfil;
import com.relgs.mongodb.repositories.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/perfils")
public class PerfilController {

    private final PerfilRepository perfilRepository;

    @GetMapping
    public List<Perfil> getAll() {
        return perfilRepository.findAll();
    }

    @PostMapping
    public Perfil create(@RequestBody Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    @GetMapping("/{id}")
    public Perfil getById(@PathVariable String id) {
        return perfilRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Perfil update(@PathVariable String id, @RequestBody Perfil perfil) {
        Perfil perfilEncontrado = perfilRepository.findById(id).orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        if (perfil.getBio() != null && !perfil.getBio().equals(perfilEncontrado.getBio())) {
            perfilEncontrado.setBio(perfil.getBio());
        }

        if (perfil.getAvatarUrl() != null && !perfil.getAvatarUrl().equals(perfilEncontrado.getAvatarUrl())) {
            perfilEncontrado.setAvatarUrl(perfil.getAvatarUrl());
        }

        perfilEncontrado.setId(perfil.getId());
        return perfilRepository.save(perfil);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        perfilRepository.deleteById(id);
    }

}
