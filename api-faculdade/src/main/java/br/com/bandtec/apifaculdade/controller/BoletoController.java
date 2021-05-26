package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.agendamento.AgendamentoAvisos;
import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.entity.Boleto;
import br.com.bandtec.apifaculdade.repository.AlunoRepository;
import br.com.bandtec.apifaculdade.repository.BoletoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/boletos")
public class BoletoController {

    @Autowired
    private BoletoRepository boletoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AgendamentoAvisos agendamentoAvisos;

    @PostMapping("/emitir-boleto/{idAluno}")
    public ResponseEntity<String> emitirBoleto(@PathVariable Integer idAluno){

        Optional<Aluno> aluno = alunoRepository.findById(idAluno);
        if (aluno.isPresent()){

            /* Uma vez o boleto emitido o aluno vai para a lista de avisos, onde será avisado
            a ele que ele tem um boleto para pagar*/
            agendamentoAvisos.getFilaEmicaoBoleto().insert(aluno.get());

            LocalDate dataBoleto = LocalDate.now();

            Optional<Boleto> boleto = Optional.ofNullable(boletoRepository.acharBoletoPorAlunoData
                    (idAluno, dataBoleto.getYear(), dataBoleto.getMonthValue()));

            if (!boleto.isPresent()){
                String codigoBoleto = UUID.randomUUID().toString();

                LocalDateTime prazoValidade = LocalDateTime.now().plusSeconds(20);

                Thread sorteadorCodigo = new Thread(() -> {
                    try {
                        Thread.sleep(10000);

                        Boleto novoBoleto = new Boleto();

                        novoBoleto.setCodigoBoleto(codigoBoleto);
                        novoBoleto.setAno(LocalDateTime.now().getYear());
                        novoBoleto.setMes(LocalDateTime.now().getMonthValue());
                        novoBoleto.setAluno(aluno.get());

                        boletoRepository.save(novoBoleto);

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
                return ResponseEntity.status(400).body("Boleto desse ano e mês já foi emitido!");
            }

        } else {
            return ResponseEntity.status(204).build();
        }
    }

    @PutMapping("/pagar-boleto/{codigo}")
    public ResponseEntity<String> pagarBoleto(@PathVariable String codigo){
        Optional<Boleto> boleto = Optional.ofNullable(boletoRepository.acharBoletoPorCodigo(codigo));

        if (boleto.isPresent()){
            boleto.get().setBoletoIsPago(true);

            // Uma vez o boleto pago, o aluno sairá da fila e deixará de receber o aviso
            agendamentoAvisos.getFilaEmicaoBoleto().poll();

            return ResponseEntity.status(200).body("Seu boleto foi pago!");
        } else {
            return ResponseEntity.status(204).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Boleto>> getBoletos(){
        List<Boleto> boletos = boletoRepository.findAll();

        if (!boletos.isEmpty()){
            return ResponseEntity.status(200).body(boletos);
        } else {
            return ResponseEntity.status(204).body(boletos);
        }
    }
}
