package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.entity.Boleto;
import br.com.bandtec.apifaculdade.repository.AlunoRepository;
import br.com.bandtec.apifaculdade.repository.BoletoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.persistence.Column;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoletoControllerTest {

    @Autowired
    private BoletoController boletoController;

    @Autowired
    private BoletoRepository boletoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    // 1° Metodo
    /* 1° Cenario - Não vai conseguir emitir boleto, pois usuário é inexistente,
    logo emitirá status code 400*/
    @Test
    void emitirBoleto(){
        ResponseEntity resposta = boletoController.emitirBoleto(1);
        Assertions.assertEquals(400, resposta.getStatusCodeValue());
    }

    /* 2° Cenario - Emição do boleto bem sucedidada, pois o usuário foi cadastrado antes
    deverá emitir o 202, pois está em promise*/
    @Test
    void emitirBoletoSucesso() {

        Aluno novoAluno = new Aluno();
        novoAluno.setId(1);
        novoAluno.setNome("André Moura");
        novoAluno.setRa("02201002");
        novoAluno.setCurso("CCO");

        alunoRepository.save(novoAluno);
        ResponseEntity resposta = boletoController.emitirBoleto(1);
        Assertions.assertEquals(202, resposta.getStatusCodeValue());
    }

    // 2° Metodo
    /* 1° Cenario - Não vai conseguir emitir boleto, pois usuário é inexistente,
    logo emitirá status code 400*/
    @Test
    void pagarBoleto() throws InterruptedException {
        emitirBoletoSucesso();

        ResponseEntity resposta = boletoController.pagarBoleto("Olá mundo!");
        Assertions.assertEquals(400, resposta.getStatusCodeValue());
    }

    /* 2° Cenario - Vai funcionar o pagamento de boleto e retornará o status 200*/
    @Test
    void pagarBoletoSucesso() throws InterruptedException {
        emitirBoletoSucesso();

        Thread.sleep(11000);

        Optional<Boleto> boleto = boletoRepository.findById(1);

        ResponseEntity resposta = boletoController.pagarBoleto(
                boleto.get().getCodigoBoleto());


        Assertions.assertEquals(200, resposta.getStatusCodeValue());
    }

}