package esof.projeto.repositories;

import esof.projeto.models.Explicador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExplicadorRepo extends CrudRepository<Explicador,Long> {
}
