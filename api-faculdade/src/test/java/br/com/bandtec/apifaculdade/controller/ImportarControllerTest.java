package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.repository.AlunoRepository;
import br.com.bandtec.apifaculdade.repository.MateriaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ImportarControllerTest {

    @Autowired
    private ImportarController importarController;

    @MockBean
    private AlunoRepository alunoRepository;

    @MockBean
    private MateriaRepository materiaRepository;

    @Test
    @DisplayName("POST /importar/{nomeArq} - Quando encontra o arquivo de Aluno, status 201")
    void importarArquivoAluno() {
        // Given

        // When
        ResponseEntity resposta = importarController.importarArquivo("ArquivoAlunoTeste");

        // Then
        Assertions.assertEquals(201, resposta.getStatusCodeValue());
    }

    @Test
    @DisplayName("POST /importar/{nomeArq} - Quando encontra o arquivo de Materia, status 201")
    void importarArquivoMateria() {
        // Given

        // When
        ResponseEntity resposta = importarController.importarArquivo("ArquivoMateriaTeste");

        // Then
        Assertions.assertEquals(201, resposta.getStatusCodeValue());
    }
}