package br.com.bandtec.apifaculdade.model;

import br.com.bandtec.apifaculdade.entity.Aluno;

public class AlunoSimplesResposta {

    /*Atributos*/
    private String ra;

    private String nome;

    private String nomeCurso;

    /*Construtor*/
    public AlunoSimplesResposta(Aluno alunoEntidade) {
        this.ra = alunoEntidade.getRa();
        this.nome = alunoEntidade.getNome();
        this.nomeCurso = alunoEntidade.getCurso().getNome();
    }

    /*Metodos*/
    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

}
