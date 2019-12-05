package esof.projeto.repositories;

import esof.projeto.models.Aluno;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepo extends CrudRepository<Aluno,Long>{
}
