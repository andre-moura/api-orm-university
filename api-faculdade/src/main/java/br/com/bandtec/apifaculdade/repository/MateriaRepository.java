package br.com.bandtec.apifaculdade.repository;

import br.com.bandtec.apifaculdade.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {

    //Acha a materia pelo nome
    Materia findMateriaByNome(String nome);
}
