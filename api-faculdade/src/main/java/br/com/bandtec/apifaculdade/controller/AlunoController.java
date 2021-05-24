package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.classes.Exportar;
import br.com.bandtec.apifaculdade.classes.Importar;
import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.entity.Curso;
import br.com.bandtec.apifaculdade.entity.Materia;
import br.com.bandtec.apifaculdade.model.AlunoSimplesResposta;
import br.com.bandtec.apifaculdade.repository.AlunoRepository;
import br.com.bandtec.apifaculdade.repository.CursoRepository;
import br.com.bandtec.apifaculdade.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private MateriaRepository materiaRepository;


    @PutMapping("/emitir-boleto/{id}")
    public ResponseEntity emitirBoleto(@PathVariable Integer id){

        // Aqui eu coloco um UID aleatório como o codigo do boleto
        String codigoBoleto = UUID.randomUUID().toString();

        // Aqui e o prazo limite para pagar o boleto, na vida real seria maior
        LocalDateTime prazoValidade = LocalDateTime.now().plusSeconds(20);

        Thread sorteadorCodigo = new Thread(() -> {
            try {
                Thread.sleep(10000);

                Optional<Aluno> aluno = alunoRepository.findById(id);

                if (aluno.isPresent()){
                    aluno.get().setCodigoBoleto(codigoBoleto);
                    alunoRepository.save(aluno.get());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        sorteadorCodigo.start();

        return ResponseEntity.status(202)
                .header("codigo-boleto", codigoBoleto)
                .header("prazo", prazoValidade.toString())
                .build();
    }

    @PutMapping("/pagar-boleto/{codigo}")
    public ResponseEntity pagarBoleto(@PathVariable String codigo){
        Optional<Aluno> aluno = Optional.ofNullable(alunoRepository.findAlunoByCodigoBoleto(codigo));

        if (aluno.isPresent()){
            aluno.get().setBoletoIsPago(true);
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
        List<AlunoSimplesResposta> alunoSimplesRespostas = alunoRepository
                .findAll()
                .stream()
                .map(AlunoSimplesResposta::new)
                .collect(Collectors.toList());

        for (int i = 0; i < alunoSimplesRespostas.size(); i++) {
            Exportar.gerarHeader(nomeArq);
            Exportar.gerarCorpoAluno(nomeArq, alunoSimplesRespostas.get(i));
            Exportar.gerarTrailer(nomeArq);
        }
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/importar-alunos/{nomeArq}")
    public ResponseEntity importarArquivoAlunos(@PathVariable String nomeArq){
        List<AlunoSimplesResposta> listaAlunos = Importar.leArquivo(nomeArq);
        if (!listaAlunos.isEmpty()){
            for (int i = 0; i < listaAlunos.size(); i++) {
                Aluno novoAluno = new Aluno();
                novoAluno.setNome(listaAlunos.get(i).getNome());
                novoAluno.setRa(listaAlunos.get(i).getRa());
                Curso curso = cursoRepository.findCursoByNome(listaAlunos.get(i).getNomeCurso());
                novoAluno.setCurso(curso);
                alunoRepository.save(novoAluno);
            }
            return ResponseEntity.status(201).build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/media/{idUsuario}")
    public ResponseEntity getMedia(@PathVariable Integer idUsuario){
        Optional<Aluno> aluno = alunoRepository.findById(idUsuario);
        if (aluno.isPresent()){

            double[] vetorNota = {
                    aluno.get().getCurso().getCursoMateria().getNota1(),
                    aluno.get().getCurso().getCursoMateria().getNota2(),
                    aluno.get().getCurso().getCursoMateria().getNota3(),
                    aluno.get().getCurso().getCursoMateria().getNota4()
            };

            return ResponseEntity.status(200)
                    .body("Media do aluno " + aluno.get().getNome()+" na materia "+
                            aluno.get().getCurso().getCursoMateria().getMateria()+" é " +
                            mediaRecursiva(vetorNota, 4));
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping
    public ResponseEntity postAluno(@RequestBody @Valid Aluno novoAluno){
        alunoRepository.save(novoAluno);
        return ResponseEntity.status(201).build();
    }

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
