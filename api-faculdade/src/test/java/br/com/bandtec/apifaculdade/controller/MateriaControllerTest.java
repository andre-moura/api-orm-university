package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.entity.Materia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MateriaControllerTest {

    @Autowired
    MateriaController materiaController;

    // 1° Metodo
    /* 1° Cenário Post - Vai dar erro*/
    @Test
    void postMateria() {
        Materia novaMateria = new Materia();
        ResponseEntity responseEntity = materiaController.postMateria(novaMateria);
        Assertions.assertEquals(201, responseEntity.getStatusCodeValue());
    }

    /* 2° Cenario Post - informações enviadas corretamente, portanto post retornando 201*/
    @Test
    void postMateriaSucesso() {
        Materia novaMateria = new Materia();

        novaMateria.setId(1);
        novaMateria.setNome("PWEB");
        novaMateria.setProfessor("Yoshi");

        ResponseEntity responseEntity = materiaController.postMateria(novaMateria);
        Assertions.assertEquals(201, responseEntity.getStatusCodeValue());
    }

    // 2° Metodo
    /* 1° Cenario Get todos materias - Deve dar o erro 400,
    pois não há nenhuma materia cadastrada*/
    @Test
    void getMaterias() {
        ResponseEntity resposta = materiaController.getMaterias();
        Assertions.assertEquals(400, resposta.getStatusCodeValue());
    }

    /* 2° Cenario Get todos materias - Deve dar o status code 200,
    pois foi cadastrado um post antes de solicitar todas as materias*/
    @Test
    void getMateriasSucesso() {
        postMateriaSucesso();

        ResponseEntity resposta = materiaController.getMaterias();
        Assertions.assertEquals(200, resposta.getStatusCodeValue());
    }

    // 3° Metodo
    /*1 Cenario Get 1 Materia - Ele vai dar erro 400, pois não existe nenhuma cadastrada*/
    @Test
    void getMateria() {
        ResponseEntity resposta = materiaController.getMateria(1);
        Assertions.assertEquals(400, resposta.getStatusCodeValue());
    }

    /*2 Cenario Get 1 Materia - Ele deve dar status 200 pois foi cadastrado uma materia
    com id = 1 antes de solicitar uma materia de id = 1*/
    @Test
    void getMateriaSucesso() {
        postMateriaSucesso();

        ResponseEntity resposta = materiaController.getMateria(1);
        Assertions.assertEquals(200, resposta.getStatusCodeValue());
    }

    // 4° Metodo
    /* 1° Cenario exportar arquivo- Vai dar erro, pois não tem nenhum dado no banco
    para exportar*/
    @Test
    void exportarMaterias() {
        String teste = "ArquivoInexistente";
        ResponseEntity resposta = materiaController.exportarMaterias(teste);
        Assertions.assertEquals(400, resposta.getStatusCodeValue());
    }

    /* 2° Cenario exportar arquivo - vai exportar um arquivo com 1 materia,
    e vir status code 200 pois foi cadastrado 1 materia antes*/
    @Test
    void exportarMateriasSucesso() {
        postMateriaSucesso();

        ResponseEntity resposta = materiaController.exportarMaterias("ArquivoMateriaTeste");
        Assertions.assertEquals(200, resposta.getStatusCodeValue());
    }
}