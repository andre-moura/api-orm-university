package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.repository.AlunoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@SpringBootTest
class AlunoControllerTest {

    @Autowired
    AlunoController alunoController;

    @MockBean
    AlunoRepository alunoRepository;

    // 1° Metodo
    @Test
    @DisplayName("GET /alunos - Quando houver registros, status 200")
    void getAlunosSucesso() {
        // Given
        List<Aluno> alunosTeste = Arrays.asList(new Aluno(), new Aluno(), new Aluno());

        // When
        Mockito.when(alunoRepository.findAll()).thenReturn(alunosTeste);
        ResponseEntity<List<Aluno>> resposta = alunoController.getAlunos();

        // Then
        Assertions.assertEquals(200, resposta.getStatusCodeValue());
        Assertions.assertEquals(3, resposta.getBody().size());
    }

    @Test
    @DisplayName("GET /alunos - Quando não houver registros, status 204")
    void getAlunosSemAluno() {
        // Given
        Mockito.when(alunoRepository.findAll()).thenReturn(new ArrayList<>());

        // When
        ResponseEntity<List<Aluno>> resposta = alunoController.getAlunos();

        // Then
        Assertions.assertEquals(204, resposta.getStatusCodeValue());
    }

    // 2° Metodo
    @Test
    @DisplayName("GET /alunos/{id} - Quando houver registros, status 200")
    void getAlunoSucesso() {
        // Given
        Aluno alunoTeste = new Aluno();

        // When
        Mockito.when(alunoRepository.findById(alunoTeste.getId())).
                thenReturn(java.util.Optional.of(alunoTeste));

        ResponseEntity responseEntity = alunoController.getAluno(alunoTeste.getId());

        // Then
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @DisplayName("GET /alunos/{id} - Quando não houver registros, status 204")
    void getAlunoSemAluno(){
        // Given
        int idTeste = 1;

        // When
        ResponseEntity responseEntity = alunoController.getAluno(idTeste);

        // Then
        Assertions.assertEquals(204, responseEntity.getStatusCodeValue());
    }

    // 3° Metodo
    @Test
    @DisplayName("GET /alunos/exportar/{nomeArq} - Quando houver registros, status 200")
    void exportarArquivoAlunosSucesso() {
        // Given
        Aluno alunoTeste = new Aluno();
        alunoTeste.setId(1);
        alunoTeste.setRa("02201002");
        alunoTeste.setNome("André Moura");
        alunoTeste.setCurso("3CCOA");

        List<Aluno> listaAlunos = new ArrayList<Aluno>();
        listaAlunos.add(alunoTeste);

        // When
        Mockito.when(alunoRepository.findAll()).thenReturn(listaAlunos);
        ResponseEntity resposta = alunoController.exportarArquivoAlunos("ArquivoAlunoTeste");

        // Then
        Assertions.assertEquals(200, resposta.getStatusCodeValue());
    }

    @Test
    @DisplayName("GET /alunos/exportar/{nomeArq} - Quando não houver registros, status 204")
    void exportarArquivoAlunosSemRegistro() {
        // Given
        List<Aluno> alunosTeste = Collections.emptyList();

        // When
        Mockito.when(alunoRepository.findAll()).thenReturn(alunosTeste);
        ResponseEntity resposta = alunoController.exportarArquivoAlunos("ArquivoAlunoTeste");

        // Then
        Assertions.assertEquals(204, resposta.getStatusCodeValue());
    }

    // 4° Metodo
    @Test
    @DisplayName("DELETE /alunos/{id} - Quando houver registros nem nota, status 200")
    void deleteAlunoSucesso() {
        // Given
        Aluno alunoTeste = new Aluno();
        alunoTeste.setId(1);

        // When
        Mockito.when(alunoRepository.findById(alunoTeste.getId())).
                thenReturn(java.util.Optional.of(alunoTeste));

        ResponseEntity responseEntity = alunoController.deleteAluno(alunoTeste.getId());

        // Then
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @DisplayName("DELETE /alunos/{id} - Quando não houver registro, status 204")
    void deleteAlunoSemAlunoStatus() {
        // Given
        int idAlunoTeste = 1;

        // When
        Mockito.when(alunoRepository.existsById(idAlunoTeste)).
                thenReturn(true);

        ResponseEntity responseEntity = alunoController.deleteAluno(idAlunoTeste);

        // Then
        Assertions.assertEquals(204, responseEntity.getStatusCodeValue());
    }

    // 5° Metodo
    @Test
    @DisplayName("POST /alunos/restaurar - Quando não houver registro, mensagem")
    void restaurarAlunoSemAlunoBody() {
        // Given

        // When
        ResponseEntity resposta = alunoController.restaurarAluno();

        // Then
        Assertions.assertEquals("Aluno não encontrado!", resposta.getBody());
    }

    @Test
    @DisplayName("POST /alunos/restaurar - Quando não houver registros dentro da fila, status 204")
    void restaurarAlunoErro() {
        // Given

        // When
        ResponseEntity resposta = alunoController.restaurarAluno();

        // Then
        Assertions.assertEquals(204, resposta.getStatusCodeValue());
    }

    // 6° Metodo
    @Test
    @DisplayName("POST /alunos - Quando tudo estiver preenchido corretamente , status 201")
    void postAlunoSucesso() {
        // Given
        Aluno alunoTeste = new Aluno();

        // When
        ResponseEntity resposta = alunoController.postAluno(alunoTeste);

        // Then
        Assertions.assertEquals(201, resposta.getStatusCodeValue());
    }

    @Test
    @DisplayName("POST /alunos  - Quando estiver preenchido certo, mensagem")
    void postAlunoErro() {
        // Given
        Aluno alunoTeste = new Aluno();

        // When
        ResponseEntity resposta = alunoController.postAluno(alunoTeste);

        // Then
        Assertions.assertEquals( "Aluno cadastrado com sucesso!", resposta.getBody());
    }
}