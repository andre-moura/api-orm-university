package br.com.bandtec.apifaculdade.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Materia {

    /*Atributos*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nome;

    @NotBlank
    private String professor;

    @ManyToOne
    private CursoMateria cursoMateria;

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

    public CursoMateria getCursoMateria() {
        return cursoMateria;
    }

    public void setCursoMateria(CursoMateria cursoMateria) {
        this.cursoMateria = cursoMateria;
    }
}
