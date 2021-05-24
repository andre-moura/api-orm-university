package br.com.bandtec.apifaculdade.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Aluno {

    /*Atributos*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(min = 8, max = 8)
    private String ra;

    @NotBlank
    private String nome;

    @NotBlank
    private String curso;

    private boolean boletoIsPago = false;

    @Column(nullable = true)
    private String codigoBoleto;

    /*Metodos*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public boolean isBoletoIsPago() {
        return boletoIsPago;
    }

    public void setBoletoIsPago(boolean boletoIsPago) {
        this.boletoIsPago = boletoIsPago;
    }

    public String getCodigoBoleto() {
        return codigoBoleto;
    }

    public void setCodigoBoleto(String codigoBoleto) {
        this.codigoBoleto = codigoBoleto;
    }
}
