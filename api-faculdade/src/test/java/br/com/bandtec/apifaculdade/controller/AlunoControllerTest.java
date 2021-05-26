package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.repository.AlunoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class AlunoControllerTest {

    @Autowired
    private AlunoController alunoController;

    @MockBean
    private AlunoRepository alunoRepository;

    // 3° Metodo
    /* 1° Cenario - Vai dar status code 400 pois não conseguirá pagar o boleto
    de um código inexistente*/
    @Test
    void getAlunos() {

    }

    /* 2° Cenario - Vai dar status code 200 pois conseguirá pagar o boleto
    de um alunop e um codigo existente*/
    @Test
    void getAlunoSucesso() {

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
        List<Aluno> alunoTeste = Arrays.asList(new Aluno(), new Aluno(), new Aluno());
        Mockito.when(alunoRepository.findAll()).thenReturn(alunoTeste);

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