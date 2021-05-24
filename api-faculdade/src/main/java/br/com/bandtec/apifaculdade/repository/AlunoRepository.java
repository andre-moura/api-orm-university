package br.com.bandtec.apifaculdade.repository;

import br.com.bandtec.apifaculdade.entity.Aluno;
import br.com.bandtec.apifaculdade.entity.AlunoMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    Aluno findAlunoByCodigoBoleto(String codigo);

    @Query("from AlunoMateria where aluno = ?1")
    AlunoMateria acharNotasPeloId(Integer id);
}
