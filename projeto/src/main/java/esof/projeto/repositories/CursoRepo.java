package esof.projeto.repositories;

import esof.projeto.models.Curso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepo extends CrudRepository<Curso,Long> {
}
