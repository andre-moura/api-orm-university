package br.com.bandtec.apifaculdade.classes;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GerarTXT {

    static int countRegistros = 0;

    public static  void gravarRegistro(String nomeArq, String registro){
        BufferedWriter saida = null;

        try {
            saida = new BufferedWriter(new FileWriter(nomeArq, true));
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

    public  static void gerarHeader(String nomeArq) {
        String header = "";

        Date dataDeHoje = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        header += "00NOTA" + JOptionPane.showInputDialog("Digite o ano e o semestre (ex: 202103)");
        header += formatter.format(dataDeHoje);
        header += "01";

        gravarRegistro(nomeArq, header);
    }

    public static void gerarCorpo(String nomeArq) {
        String corpo = "";

        corpo += "02";
        corpo += String.format("%-7s", JOptionPane.showInputDialog("Digite o curso (ex: CCO)"));
        corpo += String.format("%-10s", JOptionPane.showInputDialog("Digite o RA do aluno:"));
        corpo += String.format("%-50s", JOptionPane.showInputDialog("Digite o nome do aluno:"));
        corpo += String.format("%-40s", JOptionPane.showInputDialog("Digite a matéria:"));
        corpo += String.format("%5.2f", Double.parseDouble(JOptionPane.showInputDialog("Digite a média:")));
        corpo += String.format("%03d", Integer.parseInt(JOptionPane.showInputDialog("Digite as faltas")));

        countRegistros++;

        gravarRegistro(nomeArq, corpo);
    }


    public static void gerarTrailer(String nomeArq){
        String trailer = "";

        trailer += "01";
        trailer += String.format("%010d", countRegistros);
        gravarRegistro(nomeArq,trailer);
    }

}
