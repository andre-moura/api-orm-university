package br.com.bandtec.apifaculdade.controller;

import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.entity.Materia;
import br.com.bandtec.apifaculdade.repository.AlunoRepository;
import br.com.bandtec.apifaculdade.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("/importar")
public class ImportarController {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @PostMapping("/{nomeArq}")
    public ResponseEntity importarArquivo(String nomeArq) {

        BufferedReader entrada = null;
        String registro;
        String tipoRegistro;
        String ra, nomeAluno, curso;

        String nomeMateria, nomeProfessor;

        int contRegistro=0;

        // Abre o arquivo
        try {
            entrada = new BufferedReader(new FileReader(nomeArq));
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        // Lê os registros do arquivo
        try {
            // Lê um registro
            registro = entrada.readLine();

            while (registro != null) {
                // Obtém o tipo do registro
                tipoRegistro = registro.substring(0, 2); // obtém os 2 primeiros caracteres do registro

                if (tipoRegistro.equals("00")) {
                    System.out.println("Header");
                    System.out.println("Tipo de arquivo: " + registro.substring(2, 11).trim());
                    System.out.println("Data/hora de geração do arquivo: " + registro.substring(11, 30).trim());
                    System.out.println("Versão do layout: " + registro.substring(30,32).trim());
                }
                else if (tipoRegistro.equals("03")) {
                    System.out.println("\nTrailer");
                    int qtdRegistro = Integer.parseInt(registro.substring(2,12).trim());
                    if (qtdRegistro == contRegistro) {
                        System.out.println("Quantidade de registros gravados compatível com quantidade lida");
                    }
                    else {
                        System.out.println("Quantidade de registros gravados não confere com quantidade lida");
                    }
                }
                else if (tipoRegistro.equals("01")) {
                    if (contRegistro == 0) {
                        System.out.println();
                        System.out.printf("%-8s %-25s %-10s\n",
                                "RA",
                                "NOME",
                                "CURSO");
                    }

                    ra = registro.substring(2,10).trim();
                    nomeAluno = registro.substring(10,35).trim();
                    curso = registro.substring(35,45).trim();

                    Aluno novoAluno = new Aluno();
                    novoAluno.setRa(ra);
                    novoAluno.setNome(nomeAluno);
                    novoAluno.setCurso(curso);

                    alunoRepository.save(novoAluno);

                    System.out.printf("%-8s %-25s %-10s\n", ra, nomeAluno, curso);
                    contRegistro++;
                } else if (tipoRegistro.equals("02")) {
                    if (contRegistro == 0) {
                        System.out.println();
                        System.out.printf("%-8s %-25s\n",
                                "MATERIA",
                                "PROFESSOR");
                    }

                    nomeMateria = registro.substring(2,10).trim();
                    nomeProfessor = registro.substring(10,35).trim();

                    Materia novaMateria = new Materia();
                    novaMateria.setNome(nomeMateria);
                    novaMateria.setProfessor(nomeProfessor);

                    materiaRepository.save(novaMateria);

                    System.out.printf("%-8s %-25s\n", nomeMateria, nomeProfessor);
                    contRegistro++;
                }
                else {
                    System.out.println("Tipo de registro inválido");
                    return ResponseEntity.status(400).build();
                }

                // lê o próximo registro
                registro = entrada.readLine();
            }

            // Fecha o arquivo
            entrada.close();
        } catch (IOException e) {
            System.err.printf("Erro ao ler arquivo: %s.\n", e.getMessage());
        }
        return ResponseEntity.status(201).build();
    }
}
