package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.repository.AlunoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AlunoControllerTest {

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private AlunoRepository alunoRepository;

    // 1° Metodo
    /* 1° Cenario - Não vai conseguir emitir boleto, pois usuário é inexistente,
    logo emitirá status code 400*/
    @Test
    void emitirBoleto(){
        ResponseEntity resposta = alunoController.emitirBoleto(1);
        Assertions.assertEquals(400, resposta.getStatusCodeValue());
    }

    /* 2° Cenario - Emição do boleto bem sucedidada, pois o usuário foi cadastrado antes
    deverá emitir o 202, pois está em promise*/
    @Test
    void emitirBoletoSucesso() {
        postAlunoSucesso();
        ResponseEntity resposta = alunoController.emitirBoleto(1);
        Assertions.assertEquals(202, resposta.getStatusCodeValue());
    }

    // 2° Metodo
    /* 1° Cenario - Não vai conseguir emitir boleto, pois usuário é inexistente,
    logo emitirá status code 400*/
    @Test
    void pagarBoleto() {
    }

    /* 1° Cenario - Não vai conseguir emitir boleto, pois usuário é inexistente,
    logo emitirá status code 400*/
    @Test
    void pagarBoletoSucesso() {
    }

    // 3° Metodo
    /* 1° Cenario - Vai dar status code 400 pois não conseguirá pagar o boleto
    de um código inexistente*/
    @Test
    void getAlunos() {
        postAlunoSucesso();
        ResponseEntity resposta = alunoController.pagarBoleto("Olá mundo!");
        Assertions.assertEquals(400, resposta.getStatusCodeValue());
    }

    /* 2° Cenario - Vai dar status code 200 pois conseguirá pagar o boleto
    de um alunop e um codigo existente*/
    @Test
    void getAlunoSucesso() {
        postAlunoSucesso();

        ResponseEntity resposta = alunoController.pagarBoleto
                (alunoRepository.findById(1).get().getCodigoBoleto());

        Assertions.assertEquals(200, resposta.getStatusCodeValue());
    }

    // 4° Metodo
    /* 1° Cenario - */
    @Test
    void exportarArquivoAlunos() {
    }

    /* 2° Cenario - Vai conseguir exportar com sucesso, dando status code 200
    Pois o aluno foi exportado anteriormente*/
    @Test
    void exportarArquivoAlunosSucesso() {
        postAlunoSucesso();
        ResponseEntity resposta = alunoController.exportarArquivoAlunos("ArquivoAlunoTeste");
        Assertions.assertEquals(200, resposta.getStatusCodeValue());
    }

    @Test
    void getMedia() {
    }

    @Test
    void deleteAluno() {
    }

    @Test
    void restaurarAluno() {
    }

    // 10° Metodo
    /* 1° Cenario - */

    @Test
    void postAluno(){

    }

    /*2° Cenario - Post bem sucedido com status 201*/
    @Test
    void postAlunoSucesso() {
        Aluno novoAluno = new Aluno();

        novoAluno.setId(1);
        novoAluno.setRa("02201002");
        novoAluno.setNome("André Moura");
        novoAluno.setCurso("3CCOA");

        ResponseEntity resposta = alunoController.postAluno(novoAluno);
        Assertions.assertEquals(201, resposta.getStatusCodeValue());
    }

    @Test
    void mediaRecursiva() {
    }
}