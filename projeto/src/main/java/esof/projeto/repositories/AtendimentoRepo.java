package esof.projeto.repositories;

import esof.projeto.models.Atendimento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendimentoRepo extends CrudRepository<Atendimento,Long> {
}
