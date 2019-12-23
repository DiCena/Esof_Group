package esof.projeto.repositories;

import esof.projeto.models.Aluno;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepo extends CrudRepository<Aluno,Long>{
    Optional<Aluno> findByNome(String name);
}
