package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.classes.Exportar;
import br.com.bandtec.apifaculdade.classes.FilaObj;
import br.com.bandtec.apifaculdade.classes.PilhaObj;
import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.entity.AlunoMateria;
import br.com.bandtec.apifaculdade.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    private PilhaObj<Aluno> alunosDeletados = new PilhaObj<>(5);

    private FilaObj<Aluno> filaEmicaoBoleto = new FilaObj<>(5);

    @PutMapping("/emitir-boleto/{id}")
    public ResponseEntity emitirBoleto(@PathVariable Integer id){

        Optional<Aluno> aluno = alunoRepository.findById(id);
        if (aluno.isPresent()){
            // Adiciona o usuário a fila de requisições de boleto
            filaEmicaoBoleto.insert(aluno.get());

            // Aqui eu coloco um UID aleatório como o codigo do boleto
            String codigoBoleto = UUID.randomUUID().toString();

            // Aqui e o prazo limite para pagar o boleto, na vida real seria maior
            LocalDateTime prazoValidade = LocalDateTime.now().plusSeconds(20);

            Thread sorteadorCodigo = new Thread(() -> {
                try {

                    Thread.sleep(10000);
                    aluno.get().setCodigoBoleto(codigoBoleto);
                    alunoRepository.save(aluno.get());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            sorteadorCodigo.start();

            return ResponseEntity.status(202)
                    .header("codigo-boleto", codigoBoleto)
                    .header("prazo", prazoValidade.toString())
                    .build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @PutMapping("/pagar-boleto/{codigo}")
    public ResponseEntity pagarBoleto(@PathVariable String codigo){
        Optional<Aluno> aluno = Optional.ofNullable(alunoRepository.findAlunoByCodigoBoleto(codigo));

        if (aluno.isPresent()){
            aluno.get().setBoletoIsPago(true);
            // Com o boleto pago é retirado o aluno da fila de requisição de boletos
            filaEmicaoBoleto.poll();
            return ResponseEntity.status(200).body("Seu boleto foi pago!");
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping
    public ResponseEntity getAlunos() {
        List<Aluno> alunos = alunoRepository.findAll();

        if (!alunos.isEmpty()) {
            return ResponseEntity.status(200).body(alunos);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getAluno(@PathVariable Integer id){
        Optional<Aluno> aluno = alunoRepository.findById(id);

        if (aluno.isPresent()){
            return ResponseEntity.status(200).body(aluno);
        } else {
            return ResponseEntity.status(400).build();
        }
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
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/media/{id}/{idMateria}")
    public ResponseEntity getMedia(@PathVariable Integer id, @PathVariable Integer idMateria){
        Optional<AlunoMateria> alunoMateria = Optional.ofNullable(alunoRepository.acharNotasPeloId(id, idMateria));

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
            return ResponseEntity.status(400).build();
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
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/restaurar-aluno/{id}")
    public ResponseEntity restaurarAluno(@PathVariable Integer id){
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
