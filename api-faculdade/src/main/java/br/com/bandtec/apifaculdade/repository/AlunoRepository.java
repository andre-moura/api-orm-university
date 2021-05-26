package br.com.bandtec.apifaculdade.repository;

import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.entity.AlunoMateria;
import br.com.bandtec.apifaculdade.entity.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.Month;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    @Query("from AlunoMateria where aluno.id = ?1 and materia.id = ?2")
    AlunoMateria acharNotasPeloId(Integer id, Integer idMateria);
}
