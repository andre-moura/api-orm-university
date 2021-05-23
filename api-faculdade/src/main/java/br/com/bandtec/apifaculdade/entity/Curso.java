package br.com.bandtec.apifaculdade.entity;

import javax.persistence.*;

@Entity
public class Curso {

    /*Atributos*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String professorCoordenador;

    private boolean boletoIsPago = false;

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

    public String getProfessorCoordenador() {
        return professorCoordenador;
    }

    public void setProfessorCoordenador(String professorCoordenador) {
        this.professorCoordenador = professorCoordenador;
    }

    public boolean isBoletoIsPago() {
        return boletoIsPago;
    }

    public void setBoletoIsPago(boolean boletoIsPago) {
        this.boletoIsPago = boletoIsPago;
    }

    public MateriaCurso getMateriaCurso() {
        return materiaCurso;
    }

    public void setMateriaCurso(MateriaCurso materiaCurso) {
        this.materiaCurso = materiaCurso;
    }
}
