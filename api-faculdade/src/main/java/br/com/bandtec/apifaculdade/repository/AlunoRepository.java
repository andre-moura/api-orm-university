package br.com.bandtec.apifaculdade.repository;

import br.com.bandtec.apifaculdade.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
}
