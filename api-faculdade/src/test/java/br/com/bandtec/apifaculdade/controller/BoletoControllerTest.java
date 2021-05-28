package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.entity.Boleto;
import br.com.bandtec.apifaculdade.repository.AlunoRepository;
import br.com.bandtec.apifaculdade.repository.BoletoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.*;

@SpringBootTest
class BoletoControllerTest {

    @Autowired
    BoletoController boletoController;

    @MockBean
    BoletoRepository boletoRepository;

    @MockBean
    AlunoRepository alunoRepository;

    // 1° Metodo
    @Test
    @DisplayName("POST /boletos/emitir/{idAluno} - Quando achar o aluno, status 202")
    void emitirBoletoSucesso(){
        // Given
        Aluno alunoTeste = new Aluno();
        alunoTeste.setId(1);
        alunoTeste.setRa("02201002");
        alunoTeste.setNome("André Moura");
        alunoTeste.setCurso("3CCOA");
        alunoTeste.setCaixaDeEntrada("Vazia");

        Boleto boletoTeste = new Boleto();
        boletoTeste.setId(1);

        String codigoBoleto = UUID.randomUUID().toString();

        boletoTeste.setCodigoBoleto(codigoBoleto);
        boletoTeste.setAluno(alunoTeste);

        // When
        Mockito.when(alunoRepository.findById(alunoTeste.getId())).
                thenReturn(Optional.of(alunoTeste));

        Mockito.when(boletoRepository.acharBoletoPorAlunoData
                (alunoTeste.getId(), boletoTeste.getAno(), boletoTeste.getMes()))
                .thenReturn(boletoTeste);

        ResponseEntity resposta = boletoController.emitirBoleto(alunoTeste.getId());

        // Then
        Assertions.assertEquals(202, resposta.getStatusCodeValue());
    }


    @Test
    @DisplayName("POST /boletos/emitir/{idAluno} - Quando não achar o aluno, status 204")
    void emitirBoletoSemAluno() {
        // Given
        int idTeste = 1;

        // When
        ResponseEntity resposta = boletoController.emitirBoleto(idTeste);

        // Then
        Assertions.assertEquals(204, resposta.getStatusCodeValue());
    }

    // 2° Metodo
    @Test
    @DisplayName("PUT /boletos/pagar/{codigo} - Quando achar o codigo do boleto, status 200")
    void pagarBoletoSucesso() {
        // Given
        String codigoBoletoTeste = UUID.randomUUID().toString();
        Boleto boletoTeste = new Boleto();
        boletoTeste.setCodigoBoleto(codigoBoletoTeste);

        // When
        Mockito.when(boletoRepository.acharBoletoPorCodigo
                (boletoTeste.getCodigoBoleto())).thenReturn(boletoTeste);

        ResponseEntity resposta = boletoController.pagarBoleto(codigoBoletoTeste);

        // Then
        Assertions.assertEquals(200, resposta.getStatusCodeValue());
        Assertions.assertEquals("Seu boleto foi pago!", resposta.getBody());
    }

    @Test
    @DisplayName("PUT /boletos/pagar/{codigo} - Quando não tem o codigo do boleto, status 204")
    void pagarBoletoSemCodigo() {
        // Given
        Boleto boletoTeste = new Boleto();

        // When
        ResponseEntity resposta = boletoController.pagarBoleto
                (boletoTeste.getCodigoBoleto());

        // Then
        Assertions.assertEquals(204, resposta.getStatusCodeValue());
    }

    // 3° Metodo
    @Test
    @DisplayName("GET /boletos - Quando achar boleto, status 200")
    void getBoletosSucesso() {
        // Given
        List<Boleto> boletosTeste = Arrays.asList(new Boleto(), new Boleto(), new Boleto());

        // When
        Mockito.when(boletoRepository.findAll()).thenReturn(boletosTeste);
        ResponseEntity resposta = boletoController.getBoletos();

        // Then
        Assertions.assertEquals(200, resposta.getStatusCodeValue());
    }

    @Test
    @DisplayName("GET /boletos - Quando não achar boleto, status 204")
    void getBoletosSemBoletos() {
        // Given
        List<Boleto> boletosTeste = Collections.emptyList();

        // When
        Mockito.when(boletoRepository.findAll()).thenReturn(boletosTeste);
        ResponseEntity resposta = boletoController.getBoletos();

        // Then
        Assertions.assertEquals(204, resposta.getStatusCodeValue());
    }
}