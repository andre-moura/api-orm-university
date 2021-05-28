package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.scheduler.AgendamentoAvisos;
import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.entity.Boleto;
import br.com.bandtec.apifaculdade.repository.AlunoRepository;
import br.com.bandtec.apifaculdade.repository.BoletoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    // Emite um boleto e vincula esse boleto ao aluno utilizando seu ID
    @PostMapping("/emitir/{idAluno}")
    public ResponseEntity emitirBoleto(@PathVariable @Valid  Integer idAluno){

        Optional<Aluno> aluno = alunoRepository.findById(idAluno);

        if (aluno.isPresent()){

            // Aluno vai para uma fila, aonde receberá notificações ao não pagar o boleto
            // O ID do usuário será o ID usado para a requisição
            agendamentoAvisos.getFilaPagamentosBoletos().insert(aluno.get());

            LocalDate dataBoleto = LocalDate.now();

            Optional<Boleto> boleto = Optional.ofNullable(boletoRepository.acharBoletoPorAlunoData
                    (idAluno, dataBoleto.getYear(), dataBoleto.getMonthValue()));

            if (!boleto.isPresent()){

                // Cria um UUID que será o código do boleto
                String codigoBoleto = UUID.randomUUID().toString();

                LocalDateTime prazoValidade = LocalDateTime.now().plusSeconds(20);

                Thread sorteadorCodigo = new Thread(() -> {
                    try {

                        // Cria o boleto após 10 segundo e vincula ao aluno
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
                        .body("ID da requisição: " + aluno.get().getId());
            } else {
                return ResponseEntity.status(400).body("Boleto desse ano e mês já foi emitido!");
            }

        } else {
            return ResponseEntity.status(204).build();
        }
    }

    /* Recebe o codigo do boleto que será pago, muda o status do boleto para pago
     e Retira o Aluno da lista de cobrança caso tenha pago com sucesso*/
    @PutMapping("/pagar/{codigo}")
    public ResponseEntity<String> pagarBoleto(@PathVariable @Valid String codigo){
        Optional<Boleto> boleto = Optional.ofNullable(boletoRepository.acharBoletoPorCodigo(codigo));

        if (boleto.isPresent()){
            boleto.get().setBoletoIsPago(true);

            // Uma vez o boleto pago, o aluno sairá da fila e deixará de receber o aviso
            agendamentoAvisos.getFilaPagamentosBoletos().poll();

            return ResponseEntity.status(200).body("Seu boleto foi pago!");
        } else {
            return ResponseEntity.status(204).build();
        }
    }

    // Retorna todos os boletos existentes
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
