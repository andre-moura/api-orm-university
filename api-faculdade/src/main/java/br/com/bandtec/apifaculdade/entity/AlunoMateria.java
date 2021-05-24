package br.com.bandtec.apifaculdade.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class AlunoMateria {

    /*Atributos*/
    @Id
    private Integer id;

    @ManyToOne
    private Materia materia;

    @ManyToOne
    private Aluno aluno;

    @Column(nullable = true)
    @PositiveOrZero
    private Double nota1;

    @Column(nullable = true)
    @PositiveOrZero
    private Double nota2;

    @Column(nullable = true)
    @PositiveOrZero
    private Double nota3;

    @Column(nullable = true)
    @PositiveOrZero
    private Double nota4;

    /*Metodos*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
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
}
