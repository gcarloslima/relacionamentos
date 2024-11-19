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
@RequestMapping("/cursos")
public class CursoController {

    private final CursoRepository cursoRepository;
    private final EstudanteRepository estudanteRepository;

    @GetMapping
    public List<Curso> getAll() {
        return cursoRepository.findAll();
    }

    @PostMapping
    public Curso create(@RequestBody Curso curso) {
        if (!curso.getEstudantes().isEmpty()) {
            List<Estudante> estudantesSalvos = new ArrayList<>();
            curso.getEstudantes().forEach(estudante -> {
                if (estudante != null && estudante.getId() == null) {
                    Estudante estudanteSalvo = estudanteRepository.save(estudante);
                    estudantesSalvos.add(estudanteSalvo);
                }
            });
            curso.setEstudantes(estudantesSalvos);
        }
        return cursoRepository.save(curso);
    }

    @GetMapping("/{id}")
    public Curso getById(@PathVariable String id) {
        return cursoRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Curso update(@PathVariable String id, @RequestBody Curso curso) {
        Curso cursoEncontrado = cursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        if (curso.getNome() != null && !curso.getNome().equals(cursoEncontrado.getNome())) {
            cursoEncontrado.setNome(curso.getNome());
        }

        cursoEncontrado.setId(id);

        return cursoRepository.save(cursoEncontrado);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        cursoRepository.deleteById(id);
    }

}
