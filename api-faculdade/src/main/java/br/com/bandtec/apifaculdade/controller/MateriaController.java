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
    public ResponseEntity getMaterias() {
        List<Materia> materias = materiaRepository.findAll();

        if (!materias.isEmpty()) {
            return ResponseEntity.status(200).body(materias);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getMateria(@PathVariable Integer id){
        Optional<Materia> materia = materiaRepository.findById(id);

        if (materia.isPresent()){
            return ResponseEntity.status(200).body(materia);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/exportar-materias/{nomeArq}")
    public ResponseEntity exportarMaterias(@PathVariable String nomeArq){
        List<Materia> materias = materiaRepository.findAll();

        if (!materias.isEmpty()){
            Exportar.gerarHeader(nomeArq);
            for (int i = 0; i < materias.size(); i++) {
                Exportar.gerarCorpoMateria(nomeArq, materias.get(i));
            }
            Exportar.gerarTrailer(nomeArq);
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }
}
