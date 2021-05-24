package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.entity.Curso;
import br.com.bandtec.apifaculdade.entity.Materia;
import br.com.bandtec.apifaculdade.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity postCurso(@RequestBody @Valid Curso novoCurso){
        cursoRepository.save(novoCurso);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity getCursos() {
        List<Curso> cursos = cursoRepository.findAll();

        if (!cursos.isEmpty()) {
            return ResponseEntity.status(200).body(cursos);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getCurso(@PathVariable Integer id){
        Optional<Curso> curso = cursoRepository.findById(id);

        if (curso.isPresent()){
            return ResponseEntity.status(200).body(curso);
        } else {
            return ResponseEntity.status(400).build();
        }
    }
}
