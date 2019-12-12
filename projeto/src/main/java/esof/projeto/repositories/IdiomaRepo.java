package esof.projeto.repositories;

import esof.projeto.models.Idiomas;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdiomaRepo extends CrudRepository<Idiomas,Long> {

}
