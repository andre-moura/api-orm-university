package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.classes.Exportar;
import br.com.bandtec.apifaculdade.classes.PilhaObj;
import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.entity.AlunoMateria;
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

    @GetMapping("/exportar-alunos/{nomeArq}")
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

    @PutMapping("/lancar-notas/{idUsuario}/{idMateria}")
    public ResponseEntity lancarNotas(@PathVariable Integer idUsuario,
                                      @PathVariable Integer idMateria,
                                      @RequestBody AlunoMateria alunoMateria){

        Optional<AlunoMateria> alunoMateriaProcurado;

        alunoMateriaProcurado = Optional.ofNullable(alunoRepository.acharNotasPeloId
                (idUsuario, idMateria));

        if (alunoMateriaProcurado.isPresent()){

            alunoMateriaProcurado.get().setNota1(alunoMateria.getNota1());
            alunoMateriaProcurado.get().setNota2(alunoMateria.getNota2());
            alunoMateriaProcurado.get().setNota3(alunoMateria.getNota3());
            alunoMateriaProcurado.get().setNota4(alunoMateria.getNota4());

            alunoMateriaRepository.save(alunoMateriaProcurado.get());
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(204).build();
        }
    }

    @GetMapping("/media/{id}/{idMateria}")
    public ResponseEntity<String> getMedia(@PathVariable Integer id,
                                   @PathVariable Integer idMateria){
        Optional<AlunoMateria> alunoMateria;
        alunoMateria = Optional.ofNullable(alunoRepository.acharNotasPeloId(id, idMateria));

        if (alunoMateria.isPresent()){
            double[] vetorNota = {
                    alunoMateria.get().getNota1(),
                    alunoMateria.get().getNota2(),
                    alunoMateria.get().getNota3(),
                    alunoMateria.get().getNota4()
            };
            return ResponseEntity.status(200)
                    .body("Media do aluno " + alunoMateria.get().getAluno().getNome()+" na materia "+
                            alunoMateria.get().getMateria()+" é " +
                            mediaRecursiva(vetorNota, 4));
        } else {
            return ResponseEntity.status(204).body("Notas não lançadas ainda!");
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

    @PostMapping("/restaurar-aluno")
    public ResponseEntity<String> restaurarAluno(){
        alunoRepository.save(alunosDeletados.pop());
        return ResponseEntity.status(200).body("Aluno restaurado com sucesso!");
    }

    // Dá um "ctr + z" de até 5 vezes
    @PostMapping
    public ResponseEntity postAluno(@RequestBody @Valid Aluno novoAluno){
        alunoRepository.save(novoAluno);
        return ResponseEntity.status(201).build();
    }

    // Calcula a média de forma recursiva
    public static Double mediaRecursiva(double[] notas, int posicao){
        if (posicao == 0){
            return 0.0;
        } else if (posicao == 4){
            return (notas[posicao - 1] + mediaRecursiva(notas, posicao - 1)) / 4;
        } else {
            return notas[posicao - 1] + mediaRecursiva(notas, posicao - 1);
        }
    }
}
