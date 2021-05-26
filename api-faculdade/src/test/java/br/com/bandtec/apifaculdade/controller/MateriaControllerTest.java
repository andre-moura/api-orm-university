package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.entity.Materia;
import br.com.bandtec.apifaculdade.repository.MateriaRepository;
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
class MateriaControllerTest {

    @Autowired
    MateriaController materiaController;

    @MockBean
    MateriaRepository materiaRepository;

    // 1° Metodo
    @Test
    @DisplayName("POST /materias - Quando cadastrado com sucesso, status 201")
    void postMateriaCadastradoStatus() {
        // Given
        Materia novaMateria = new Materia();

        // When
        ResponseEntity responseEntity = materiaController.postMateria(novaMateria);

        // Then
        Assertions.assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    @DisplayName("POST /materias - Quando cadastrado com sucesso, mensagem")
    void postMateriaCadastradoBody() {
        // Given
        Materia novaMateria = new Materia();

        // When
        ResponseEntity responseEntity = materiaController.postMateria(novaMateria);

        // Then
        Assertions.assertEquals("Materia cadastrada com sucesso!", responseEntity.getBody());
    }

    // 2° Metodo
    @Test
    @DisplayName("GET /materias - Quando encontra as materias, status 200")
    void getMateriasEncontrado() {
        // Given
        List<Materia> materiasTeste = Arrays.asList(new Materia(), new Materia(), new Materia());

        // When
        Mockito.when(materiaRepository.findAll()).thenReturn(materiasTeste);
        ResponseEntity<List<Materia>> resposta = materiaController.getMaterias();

        // Then
        Assertions.assertEquals(200, resposta.getStatusCodeValue());
        Assertions.assertEquals(3, resposta.getBody().size());

    }

    @Test
    @DisplayName("GET /materias - Quando não encontra as materias, status 204")
    void getMateriasVazio() {
        // Given
        List<Materia> materiasTeste = Collections.emptyList();

        // When
        Mockito.when(materiaRepository.findAll()).thenReturn(materiasTeste);
        ResponseEntity<List<Materia>> resposta = materiaController.getMaterias();

        // Then
        Assertions.assertEquals(204, resposta.getStatusCodeValue());
    }

    // 3° Metodo
    @Test
    @DisplayName("GET /materias/{id} - Quando achar a materia, status 200")
    void getMateriaEncontrado() {
        // Given
        int idTeste = 1;
        Materia materiaTeste = new Materia();
        materiaTeste.setId(idTeste);
        materiaTeste.setNome("PWEB");
        materiaTeste.setProfessor("Yoshi");

        // When
        Mockito.when(materiaRepository.findById
                (materiaTeste.getId())).thenReturn(java.util.Optional.of(materiaTeste));
        ResponseEntity resposta = materiaController.getMateria(materiaTeste.getId());

        // Then
        Assertions.assertEquals(200, resposta.getStatusCodeValue());
    }

    @Test
    @DisplayName("GET /materias/{id} - Quando não achar a materia, status 204")
    void getMateriaVazio() {
        // Given
        int idTeste = 1;

        // When
        ResponseEntity resposta = materiaController.getMateria(idTeste);

        // Then
        Assertions.assertEquals(204, resposta.getStatusCodeValue());
    }

    // 4° Metodo
    @Test
    @DisplayName("GET /materias/exportar/{nomeArq} - Quando encontrar registros, status 200")
    void exportarMateriasSucesso() {
        // Given
        Materia materiaTeste = new Materia();
        materiaTeste.setId(1);
        materiaTeste.setNome("PWEB");
        materiaTeste.setProfessor("Yoshi");

        List<Materia> materiasTeste = new ArrayList<Materia>();
        materiasTeste.add(materiaTeste);

        // When
        Mockito.when(materiaRepository.findAll()).thenReturn(materiasTeste);
        ResponseEntity resposta = materiaController.exportarMaterias("ArquivoMateriaTeste");

        // Then
        Assertions.assertEquals(200, resposta.getStatusCodeValue());
    }


    @Test
    @DisplayName("GET /exportar/{nomeArq} - Quando não encontra registros, status 204")
    void exportarMateriasSemRegistro() {
        // Given
        List<Materia> materiasTeste = Collections.emptyList();

        // When
        Mockito.when(materiaRepository.findAll()).thenReturn(materiasTeste);
        ResponseEntity resposta = materiaController.exportarMaterias("ArquivoMateriaTeste");

        // Then
        Assertions.assertEquals(204, resposta.getStatusCodeValue());
    }
}