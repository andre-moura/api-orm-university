package br.com.bandtec.apifaculdade.repository;

import br.com.bandtec.apifaculdade.entity.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoletoRepository extends JpaRepository<Boleto, Integer> {

    // Acha o boleto pelo codigo dele
    @Query("from Boleto where codigoBoleto = ?1")
    Boleto acharBoletoPorCodigo(String codigo);

    // Acha o boleto pelo aluno e pela data
    @Query("from Boleto where aluno.id = ?1 and ano = ?2 and mes = ?3")
    Boleto acharBoletoPorAlunoData(Integer id, int ano, int mes);
}
