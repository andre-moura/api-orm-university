package br.com.bandtec.apifaculdade.entity;

import javax.persistence.*;

@Entity
public class Materia {

    /*Atributos*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String professor;

    private Double nota1;

    private Double nota2;

    private Double nota3;

    private Double nota4;

    @ManyToOne
    private MateriaCurso materiaCurso;

    /*Metodos*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Double getNota1() {
        return nota1;
    }

    public void setNota1(Double nota1) {
        this.nota1 = nota1;
    }

    public Double getNota2() {
        return nota2;
    }

    public void setNota2(Double nota2) {
        this.nota2 = nota2;
    }

    public Double getNota3() {
        return nota3;
    }

    public void setNota3(Double nota3) {
        this.nota3 = nota3;
    }

    public Double getNota4() {
        return nota4;
    }

    public void setNota4(Double nota4) {
        this.nota4 = nota4;
    }

    public MateriaCurso getMateriaCurso() {
        return materiaCurso;
    }

    public void setMateriaCurso(MateriaCurso materiaCurso) {
        this.materiaCurso = materiaCurso;
    }
}
