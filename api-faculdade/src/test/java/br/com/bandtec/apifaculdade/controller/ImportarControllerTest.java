package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.controller.ImportarController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ImportarControllerTest {

    @Autowired
    private ImportarController importarController;

    /* 1° Cenario - Tenta importar o arquivo com o corpo de Aluno, retorna o status 201*/
    @Test
    void importarArquivoAluno() {
        ResponseEntity resposta = importarController.importarArquivo("ArquivoAlunoTeste");
        Assertions.assertEquals(201, resposta.getStatusCodeValue());
    }

    /* 2° Cenario - Tenta importar o arquivo com o corpo de materia, retorna o status 201*/
    @Test
    void importarArquivoMateria() {
        ResponseEntity resposta = importarController.importarArquivo("ArquivoMateriaTeste");
        Assertions.assertEquals(201, resposta.getStatusCodeValue());
    }
}