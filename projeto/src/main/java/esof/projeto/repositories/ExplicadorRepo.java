package esof.projeto.repositories;

import esof.projeto.models.Explicador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExplicadorRepo extends CrudRepository<Explicador,Long> {

    Optional<Explicador> findByNome(String nome);
    void deleteByNome(String nome);
}
