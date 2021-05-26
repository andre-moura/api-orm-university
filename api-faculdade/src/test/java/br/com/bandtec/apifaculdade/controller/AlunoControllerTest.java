package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.entity.AlunoMateria;
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

    // 5° Metodo
    @Test
    void exportarArquivoAlunosSucesso() {

    }

    @Test
    void exportarArquivoAlunosErro() {

    }

    // 6° Metodo
    @Test
    @DisplayName("PUT /lancar-notas/{idAluno}/{idMateria} - Quando houver registros, status 200")
    void lancarNotasSucesso() {
        // Given
        int idAlunoTeste = 1;
        int idMateriaTeste = 1;
        AlunoMateria alunoMateriaTeste = new AlunoMateria();

        // When
        Mockito.when(alunoRepository.acharNotasPeloId(idAlunoTeste, idMateriaTeste)).thenReturn(alunoMateriaTeste);

        ResponseEntity resposta = alunoController.lancarNotas(
                idAlunoTeste,
                idMateriaTeste,
                alunoMateriaTeste);

        // Then
        Assertions.assertEquals(200, resposta.getStatusCodeValue());
    }

    @Test
    @DisplayName("PUT /lancar-notas/{idAluno}/{idMateria} - Quando não houver registros, status 204")
    void lancarNotasSemRegistro() {
        // Given
        int idAlunoTeste = 1;
        int idMateriaTeste = 1;
        AlunoMateria alunoMateriaTeste = new AlunoMateria();

        // When
        ResponseEntity resposta = alunoController.lancarNotas(
                idAlunoTeste,
                idMateriaTeste,
                alunoMateriaTeste);

        // Then
        Assertions.assertEquals(204, resposta.getStatusCodeValue());
    }

    // 7° Metodo
    @Test
    @DisplayName("GET /media/{idAluno}/{idMateria} - Quando houver registros, status 200")
    void getMediaSucesso() {
        // Given
        int idAlunoTeste = 1;
        int idMateriaTeste = 1;
        AlunoMateria alunoMateriaTeste = new AlunoMateria();

        alunoMateriaTeste.setNota1(10.0);
        alunoMateriaTeste.setNota2(8.0);
        alunoMateriaTeste.setNota3(9.0);
        alunoMateriaTeste.setNota4(8.0);

        // When
        Mockito.when(alunoRepository.acharNotasPeloId(idAlunoTeste, idMateriaTeste))
                .thenReturn(alunoMateriaTeste);

        ResponseEntity responseEntity = alunoController.getMedia(idAlunoTeste, idMateriaTeste);

        // Then
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
        Assertions.assertEquals(8.75, responseEntity.getBody());
    }

    @Test
    @DisplayName("GET /media/{idAluno}/{idMateria} - Quando não houver registros nem nota, status 204")
    void getMediaSemNota() {
        // Given
        int idAlunoTeste = 1;
        int idMateriaTeste = 1;

        // When
        ResponseEntity responseEntity = alunoController.getMedia(idAlunoTeste, idMateriaTeste);

        // Then
        Assertions.assertEquals(204, responseEntity.getStatusCodeValue());
    }

    // 8° Metodo
    @Test
    @DisplayName("DELETE /{id} - Quando houver registros nem nota, status 200")
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
    @DisplayName("DELETE /{id} - Quando não houver registro, status 204")
    void deleteAlunoSemAluno() {
        // Given
        int idAluno = 1;

        // When
        Mockito.when(alunoRepository.existsById(idAluno)).
                thenReturn(true);

        ResponseEntity responseEntity = alunoController.deleteAluno(idAluno);

        // Then
        Assertions.assertEquals(204, responseEntity.getStatusCodeValue());
    }

    // 9° Metodo
    @Test
    void restaurarAluno() {
    }

    @Test
    void postAluno() {
    }

    @Test
    void mediaRecursiva() {
    }
}