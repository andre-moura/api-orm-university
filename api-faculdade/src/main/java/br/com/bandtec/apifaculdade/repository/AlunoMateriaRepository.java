package br.com.bandtec.apifaculdade.repository;

import br.com.bandtec.apifaculdade.entity.AlunoMateria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoMateriaRepository extends JpaRepository<AlunoMateria, Integer> {
}
