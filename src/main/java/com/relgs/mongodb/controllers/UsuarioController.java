package com.relgs.mongodb.controllers;

import com.relgs.mongodb.models.Perfil;
import com.relgs.mongodb.models.Postagem;
import com.relgs.mongodb.models.Usuario;
import com.relgs.mongodb.repositories.PerfilRepository;
import com.relgs.mongodb.repositories.PostagemRepository;
import com.relgs.mongodb.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PostagemRepository postagemRepository;
    private final PostagemController postagemController;

    @GetMapping
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuario create(@RequestBody Usuario usuario) {
        if (usuario.getPerfil() != null && usuario.getPerfil().getId() == null) {
            Perfil perfilSalvo = perfilRepository.save(usuario.getPerfil());
            usuario.setPerfil(perfilSalvo);
        }
        if (!usuario.getPostagens().isEmpty()) {
            List<Postagem> postagensSalvas = new ArrayList<>();
            usuario.getPostagens().forEach(postagem -> {
                if (postagem != null && postagem.getId() == null) {
                    Postagem postagemSalva = postagemRepository.save(postagem);
                    postagensSalvas.add(postagemSalva);
                }
            });
            usuario.setPostagens(postagensSalvas);
        }
        return usuarioRepository.save(usuario);
    }

    @GetMapping("/{id}")
    public Usuario getById(@PathVariable String id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Usuario update(@PathVariable String id, @RequestBody Usuario usuario) {
        Usuario usuarioEncontrado = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        if (usuario.getNome() != null && !usuario.getNome().equals(usuarioEncontrado.getNome())) {
            usuarioEncontrado.setNome(usuario.getNome());
        }

        usuario.setId(id);
        return usuarioRepository.save(usuarioEncontrado);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        usuarioRepository.deleteById(id);
    }

}
