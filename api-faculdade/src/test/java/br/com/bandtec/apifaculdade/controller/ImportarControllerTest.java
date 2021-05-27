package br.com.bandtec.apifaculdade.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@SpringBootTest
class ImportarControllerTest {

    @Autowired
    private ImportarController importarController;

    @Test
    @DisplayName("POST /importar - Quando o arquivo é vazio, status 204")
    void importarArquivoAluno() throws IOException {
        // Given
        MultipartFile file = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return true;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {

            }
        };

        // When
        ResponseEntity resposta = importarController.importarArquivo(file);

        // Then
        Assertions.assertEquals(204, resposta.getStatusCodeValue());
    }

    @Test
    @DisplayName("POST /importar - Quando encontra o arquivo de Materia, mensagem")
    void importarArquivoMateria() throws IOException {
        // Given
        MultipartFile file = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return true;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {

            }
        };

        // When
        ResponseEntity resposta = importarController.importarArquivo(file);

        // Then
        Assertions.assertEquals("Arquivo vazio ou não encontrado!", resposta.getBody());
    }
}