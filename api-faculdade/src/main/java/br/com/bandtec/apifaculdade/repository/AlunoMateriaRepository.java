package br.com.bandtec.apifaculdade.repository;

import br.com.bandtec.apifaculdade.entity.AlunoMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlunoMateriaRepository extends JpaRepository<AlunoMateria, Integer> {

    // Acha todas as jotas pelo id da materia e do aluno
    @Query("from AlunoMateria where aluno.id = ?1 and materia.id = ?2")
    AlunoMateria acharNotasPeloId(Integer idAluno, Integer idMateria);
}
