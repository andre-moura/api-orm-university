package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.entity.AlunoMateria;
import br.com.bandtec.apifaculdade.entity.Materia;
import br.com.bandtec.apifaculdade.repository.AlunoMateriaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class AlunoMateriaControllerTest {

    @Autowired
    AlunoMateriaController alunoMateriaController;

    @MockBean
    AlunoMateriaRepository alunoMateriaRepository;

    // 1° Metodo
    @Test
    @DisplayName("PUT /alunos-materias/lancar-notas/{idAluno}/{idMateria} - Quando houver registros, status 200")
    void lancarNotasSucesso() {
        // Given
        int idAlunoTeste = 1;
        int idMateriaTeste = 1;
        AlunoMateria alunoMateriaTeste = new AlunoMateria();

        // When
        Mockito.when(alunoMateriaRepository.acharNotasPeloId(idAlunoTeste, idMateriaTeste)).thenReturn(alunoMateriaTeste);

        ResponseEntity resposta = alunoMateriaController.lancarNotas(
                idAlunoTeste,
                idMateriaTeste,
                alunoMateriaTeste);

        // Then
        Assertions.assertEquals(200, resposta.getStatusCodeValue());
    }

    @Test
    @DisplayName("PUT /alunos-materias/lancar-notas/{idAluno}/{idMateria} - Quando não houver registros, status 204")
    void lancarNotasSemRegistro() {
        // Given
        int idAlunoTeste = 1;
        int idMateriaTeste = 1;
        AlunoMateria alunoMateriaTeste = new AlunoMateria();

        // When
        ResponseEntity resposta = alunoMateriaController.lancarNotas(
                idAlunoTeste,
                idMateriaTeste,
                alunoMateriaTeste);

        // Then
        Assertions.assertEquals(204, resposta.getStatusCodeValue());
    }

    // 2° Metodo
    @Test
    @DisplayName("GET /alunos-materias/media/{idAluno}/{idMateria} - Quando houver registros, status 200")
    void getMediaSucesso() {
        // Given
        Aluno alunoTeste = new Aluno();
        Materia materiaTeste = new Materia();

        alunoTeste.setId(1);
        alunoTeste.setNome("André Moura da Silva");
        materiaTeste.setId(1);
        materiaTeste.setNome("PWEB");

        AlunoMateria alunoMateriaTeste = new AlunoMateria();

        alunoMateriaTeste.setAluno(alunoTeste);
        alunoMateriaTeste.setMateria(materiaTeste);

        alunoMateriaTeste.setNota1(10.0);
        alunoMateriaTeste.setNota2(8.0);
        alunoMateriaTeste.setNota3(9.0);
        alunoMateriaTeste.setNota4(8.0);

        // When
        Mockito.when(alunoMateriaRepository.acharNotasPeloId(alunoTeste.getId(), materiaTeste.getId()))
                .thenReturn(alunoMateriaTeste);

        ResponseEntity responseEntity = alunoMateriaController.getMedia(alunoTeste.getId(), materiaTeste.getId());

        // Then
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("Media do aluno "+
                alunoMateriaTeste.getAluno().getNome()+ " em " +
                alunoMateriaTeste.getMateria().getNome() +": " +
                8.75, responseEntity.getBody());
    }

    @Test
    @DisplayName("GET /alunos-materias/media/{idAluno}/{idMateria} - Quando não houver registros nem nota, status 204")
    void getMediaSemNota() {
        // Given
        int idAlunoTeste = 1;
        int idMateriaTeste = 1;

        // When
        ResponseEntity responseEntity = alunoMateriaController.getMedia(idAlunoTeste, idMateriaTeste);

        // Then
        Assertions.assertEquals(204, responseEntity.getStatusCodeValue());
    }
}