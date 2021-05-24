package br.com.bandtec.apifaculdade.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Curso {

    /*Atributos*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nome;

    @NotBlank
    private String professorCoordenador;

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

    public String getProfessorCoordenador() {
        return professorCoordenador;
    }

    public void setProfessorCoordenador(String professorCoordenador) {
        this.professorCoordenador = professorCoordenador;
    }

    public CursoMateria getCursoMateria() {
        return cursoMateria;
    }

    public void setCursoMateria(CursoMateria cursoMateria) {
        this.cursoMateria = cursoMateria;
    }
}
