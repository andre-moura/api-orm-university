package br.com.bandtec.apifaculdade.repository;

import br.com.bandtec.apifaculdade.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    Aluno findAlunoByCodigoBoleto(String codigo);
}
