package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.classes.Exportar;
import br.com.bandtec.apifaculdade.classes.PilhaObj;
import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.repository.AlunoMateriaRepository;
import br.com.bandtec.apifaculdade.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoMateriaRepository alunoMateriaRepository;

    private PilhaObj<Aluno> alunosDeletados = new PilhaObj<>(5);

    @GetMapping
    public ResponseEntity<List<Aluno>> getAlunos() {
        List<Aluno> alunos = alunoRepository.findAll();

        if (!alunos.isEmpty()) {
            return ResponseEntity.status(200).body(alunos);
        } else {
            return ResponseEntity.status(204).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getAluno(@PathVariable Integer id){
        Optional<Aluno> aluno = alunoRepository.findById(id);

        return aluno.map(value ->
                ResponseEntity.status(200).body(value)).orElseGet(() ->
                ResponseEntity.status(204).build());
    }

    @GetMapping("/exportar/{nomeArq}")
    public ResponseEntity exportarArquivoAlunos(@PathVariable String nomeArq){
        List<Aluno> alunos = alunoRepository.findAll();

        if (!alunos.isEmpty()){
            Exportar.gerarHeader(nomeArq);
            for (int i = 0; i < alunos.size(); i++) {
                Exportar.gerarCorpoAluno(nomeArq, alunos.get(i));
            }
            Exportar.gerarTrailer(nomeArq);
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(204).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAluno(@PathVariable Integer id){
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if (aluno.isPresent()){
            alunosDeletados.push(aluno.get());
            alunoRepository.deleteById(id);
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(204).build();
        }
    }

    @PostMapping("/restaurar")
    public ResponseEntity<String> restaurarAluno(){
        if (!alunosDeletados.isEmpty()){
            alunoRepository.save(alunosDeletados.pop());
            return ResponseEntity.status(201).body("Aluno restaurado com sucesso!");
        } else {
            return ResponseEntity.status(204).body("Aluno não encontrado!");
        }
    }

    // Dá um "ctr + z" de até 5 vezes
    @PostMapping
    public ResponseEntity postAluno(@RequestBody @Valid Aluno novoAluno){
        alunoRepository.save(novoAluno);
        return ResponseEntity.status(201).body("Aluno cadastrado com sucesso!");
    }

    public PilhaObj<Aluno> getAlunosDeletados() {
        return alunosDeletados;
    }
}
