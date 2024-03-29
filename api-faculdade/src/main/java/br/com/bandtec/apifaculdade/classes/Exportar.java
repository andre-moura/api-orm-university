package br.com.bandtec.apifaculdade.classes;

import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.entity.Materia;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Exportar {

    // Conta quantos registros tem no arquivo
    static int countRegistros = 0;

    // Cria o arquivo e grava os registros nele
    public static  void gravarRegistro(String nomeArq, String registro){
        BufferedWriter saida = null;

        try {
            saida = new BufferedWriter(new FileWriter(nomeArq+".txt", true));
        } catch (IOException ex) {
            System.out.println("Erro na abertura do arquivo: %s. \n "+ ex.getMessage());
        }

        try {
            saida.append(registro + "\n");
            saida.close();
        }catch (IOException ex){
            System.out.println(" " + ex.getMessage());
        }
    }

    // Cria um Header para o arquivo
    public  static void gerarHeader(String nomeArq) {
        String header = "";

        Date dataDeHoje = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        header += "00FACULDADE2021";
        header += formatter.format(dataDeHoje);
        header += "00";

        gravarRegistro(nomeArq, header);
    }

    // Gera um corpo para o arquivo utilizando objetos Alunos vindos do banco
    public static void gerarCorpoAluno(String nomeArq, Aluno aluno) {
        if (aluno.getId() != null) {
            String corpo = "";

            corpo += "01";
            corpo += String.format("%-10s", aluno.getRa());
            corpo += String.format("%-25s", aluno.getNome());
            corpo += String.format("%-10s", aluno.getCurso());

            countRegistros++;

            gravarRegistro(nomeArq, corpo);
        }
    }

    // Gera o corpo para o arquivo utilizando Materias
    public static void gerarCorpoMateria(String nomeArq, Materia materia) {
        if (materia.getId() != null) {
            String corpo = "";

            corpo += "02";
            corpo += String.format("%-8s", materia.getNome());
            corpo += String.format("%-25s", materia.getProfessor());

            countRegistros++;

            gravarRegistro(nomeArq, corpo);
        }

    }

    // Gera um Trailer para o arquivo para fins de controle
    public static void gerarTrailer(String nomeArq){
        String trailer = "";

        trailer += "03";
        trailer += String.format("%010d", countRegistros);
        gravarRegistro(nomeArq,trailer);
    }
}
