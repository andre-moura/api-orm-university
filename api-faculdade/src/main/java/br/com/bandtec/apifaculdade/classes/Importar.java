package br.com.bandtec.apifaculdade.classes;

import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.model.AlunoSimplesResposta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Importar {

    public static List<AlunoSimplesResposta> leArquivo(String nomeArq) {
        List<AlunoSimplesResposta> listaRegistros = new ArrayList<>();

        BufferedReader entrada = null;
        String registro;
        String tipoRegistro;
        String curso, ra, nomeAluno;
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
                    System.out.println("Tipo de arquivo: " + registro.substring(2, 6));
                    System.out.println("Data/hora de geração do arquivo: " + registro.substring(6, 25));
                    System.out.println("Versão do layout: " + registro.substring(25,27));
                }
                else if (tipoRegistro.equals("02")) {
                    System.out.println("\nTrailer");
                    int qtdRegistro = Integer.parseInt(registro.substring(2,12));
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

                    ra = registro.substring(2,8);
                    nomeAluno = registro.substring(8,33);
                    curso = registro.substring(33, 43);

                    Aluno aluno = new Aluno();
                    AlunoSimplesResposta novoAluno = new AlunoSimplesResposta(aluno);
                    novoAluno.setRa(ra);
                    novoAluno.setNome(nomeAluno);
                    novoAluno.setNomeCurso(curso);

                    listaRegistros.add(novoAluno);

                    System.out.printf("%-8s %-25s %-10s\n", ra, nomeAluno, curso);
                    contRegistro++;
                }
                else {
                    System.out.println("Tipo de registro inválido");
                }

                // lê o próximo registro
                registro = entrada.readLine();
            }

            // Fecha o arquivo
            entrada.close();
        } catch (IOException e) {
            System.err.printf("Erro ao ler arquivo: %s.\n", e.getMessage());
        }

        return listaRegistros;
    }
}
