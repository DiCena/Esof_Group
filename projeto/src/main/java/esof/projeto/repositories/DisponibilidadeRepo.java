package esof.projeto.repositories;

import esof.projeto.models.Disponibilidade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisponibilidadeRepo extends CrudRepository<Disponibilidade,Long> {
}
