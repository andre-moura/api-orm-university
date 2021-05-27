package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.entity.AlunoMateria;
import br.com.bandtec.apifaculdade.repository.AlunoMateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/alunos-materias")
public class AlunoMateriaController {

    @Autowired
    private AlunoMateriaRepository alunoMateriaRepository;

    @PutMapping("/lancar-notas/{idAluno}/{idMateria}")
    public ResponseEntity lancarNotas(@PathVariable Integer idAluno,
                                      @PathVariable Integer idMateria,
                                      @RequestBody AlunoMateria alunoMateria){

        Optional<AlunoMateria> alunoMateriaProcurado;

        alunoMateriaProcurado = Optional.ofNullable(alunoMateriaRepository.acharNotasPeloId
                (idAluno, idMateria));

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

    @GetMapping("/media/{idAluno}/{idMateria}")
    public ResponseEntity getMedia(@PathVariable Integer idAluno,
                                   @PathVariable Integer idMateria){
        Optional<AlunoMateria> alunoMateria;
        alunoMateria = Optional.ofNullable(alunoMateriaRepository.acharNotasPeloId(idAluno, idMateria));

        if (alunoMateria.isPresent()){
            double[] vetorNota = {
                    alunoMateria.get().getNota1(),
                    alunoMateria.get().getNota2(),
                    alunoMateria.get().getNota3(),
                    alunoMateria.get().getNota4()
            };
            return ResponseEntity.status(200)
                    .body("Media do aluno "+
                            alunoMateria.get().getAluno().getNome()+ " em " +
                            alunoMateria.get().getMateria().getNome() +": " +
                            mediaRecursiva(vetorNota, 4)
                    );
        } else {
            return ResponseEntity.status(204).body("Notas não lançadas ainda!");
        }
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
