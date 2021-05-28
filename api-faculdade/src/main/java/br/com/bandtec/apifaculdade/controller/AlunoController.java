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

    // Pega todos os alunos cadastrados
    @GetMapping
    public ResponseEntity<List<Aluno>> getAlunos() {
        List<Aluno> alunos = alunoRepository.findAll();

        if (!alunos.isEmpty()) {
            return ResponseEntity.status(200).body(alunos);
        } else {
            return ResponseEntity.status(204).build();
        }
    }

    // Pega um aluno pelo id
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getAluno(@PathVariable Integer id){
        Optional<Aluno> aluno = alunoRepository.findById(id);

        return aluno.map(value ->
                ResponseEntity.status(200).body(value)).orElseGet(() ->
                ResponseEntity.status(204).build());
    }

    // Recebe um parametro e exporta um arquivo com alunos usando esse parametro como nome
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

    // Deleta um aluno pelo ID e joga o aluno dentro de uma pilha
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

    // Restaura o aluno deletado utilizando a pilha, dando assim um "ctrl + z"
    @PostMapping("/restaurar")
    public ResponseEntity<String> restaurarAluno(){
        if (!alunosDeletados.isEmpty()){
            alunoRepository.save(alunosDeletados.pop());
            return ResponseEntity.status(201).body("Aluno restaurado com sucesso!");
        } else {
            return ResponseEntity.status(204).body("Aluno não encontrado!");
        }
    }

    // Recebe um corpo de requisição e cadastra um aluno
    @PostMapping
    public ResponseEntity postAluno(@RequestBody @Valid Aluno novoAluno){
        alunoRepository.save(novoAluno);
        return ResponseEntity.status(201).body("Aluno cadastrado com sucesso!");
    }

    // Get usado para pegar a pilha com alunos deletados
    public PilhaObj<Aluno> getAlunosDeletados() {
        return alunosDeletados;
    }
}
