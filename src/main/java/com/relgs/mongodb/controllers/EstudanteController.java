package com.relgs.mongodb.controllers;

import com.relgs.mongodb.models.Curso;
import com.relgs.mongodb.models.Estudante;
import com.relgs.mongodb.repositories.CursoRepository;
import com.relgs.mongodb.repositories.EstudanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estudantes")
public class EstudanteController {

    private final EstudanteRepository estudanteRepository;
    private final CursoRepository cursoRepository;

    @GetMapping
    public List<Estudante> getAll() {
        return estudanteRepository.findAll();
    }

    @PostMapping
    public Estudante create(@RequestBody Estudante estudante) {
        if (!estudante.getCursos().isEmpty()) {
            List<Curso> cursosSalvos = new ArrayList<>();
            estudante.getCursos().forEach(curso -> {
                if (curso != null && curso.getId() == null) {
                    Curso cursoSalvo = cursoRepository.save(curso);
                    cursosSalvos.add(cursoSalvo);
                }
            });
            estudante.setCursos(cursosSalvos);
        }
        return estudanteRepository.save(estudante);
    }

    @GetMapping("/{id}")
    public Estudante getById(@PathVariable String id) {
        return estudanteRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Estudante update(@PathVariable String id, @RequestBody Estudante estudante) {
        Estudante estudanteEncontrado = estudanteRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        if (estudante.getNome() != null && !estudante.getNome().equals(estudanteEncontrado.getNome())) {
            estudanteEncontrado.setNome(estudante.getNome());
        }

        estudanteEncontrado.setId(id);

        return estudanteRepository.save(estudanteEncontrado);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        estudanteRepository.deleteById(id);
    }

}
