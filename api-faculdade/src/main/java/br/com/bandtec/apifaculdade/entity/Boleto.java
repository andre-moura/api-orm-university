package br.com.bandtec.apifaculdade.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

@Entity
public class Boleto {

    /*Atributos*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true)
    private String codigoBoleto;

    @Column(nullable = true)
    private int mes;

    @Column(nullable = true)
    private int ano;

    private boolean boletoIsPago = false;

    @ManyToOne
    private Aluno aluno;

    /*Metodos*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoBoleto() {
        return codigoBoleto;
    }

    public void setCodigoBoleto(String codigoBoleto) {
        this.codigoBoleto = codigoBoleto;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public boolean isBoletoIsPago() {
        return boletoIsPago;
    }

    public void setBoletoIsPago(boolean boletoIsPago) {
        this.boletoIsPago = boletoIsPago;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}
