package br.com.bandtec.apifaculdade.repository;

import br.com.bandtec.apifaculdade.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
}
