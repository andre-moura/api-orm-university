package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.classes.Exportar;
import br.com.bandtec.apifaculdade.entity.Materia;
import br.com.bandtec.apifaculdade.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    @Autowired
    private MateriaRepository materiaRepository;

    @PostMapping
    public ResponseEntity postMateria(@RequestBody @Valid Materia novaMateria){
        materiaRepository.save(novaMateria);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<Materia>> getMaterias() {
        List<Materia> materias = materiaRepository.findAll();

        if (!materias.isEmpty()) {
            return ResponseEntity.status(200).body(materias);
        } else {
            return ResponseEntity.status(204).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Materia> getMateria(@PathVariable Integer id){
        Optional<Materia> materia = materiaRepository.findById(id);

        return materia.map(value -> ResponseEntity.status(200).body(value)).orElseGet(() ->
                ResponseEntity.status(204).build());
    }

    @GetMapping("/exportar-materias/{nomeArq}")
    public ResponseEntity<List<Materia>> exportarMaterias(@PathVariable String nomeArq){
        List<Materia> materias = materiaRepository.findAll();

        if (!materias.isEmpty()){
            Exportar.gerarHeader(nomeArq);
            for (Materia materia : materias) {
                Exportar.gerarCorpoMateria(nomeArq, materia);
            }
            Exportar.gerarTrailer(nomeArq);
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(204).build();
        }
    }
}
