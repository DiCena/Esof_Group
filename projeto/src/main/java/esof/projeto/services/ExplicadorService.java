package esof.projeto.services;


import esof.projeto.models.Explicador;
import esof.projeto.repositories.ExplicadorRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ExplicadorService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ExplicadorRepo explicadorRepo;

    @Autowired
    public ExplicadorService(ExplicadorRepo explicadorRepo) {
        this.explicadorRepo = explicadorRepo;
    }


    /**
     * Devolve todos os explicadores na base de dados.
     * @return Explicadores
     */
    public Set<Explicador> findAll() {
        Set<Explicador> explicadores = new HashSet<>();
            for(Explicador exp : this.explicadorRepo.findAll()) {
                explicadores.add(exp);
            }
            return explicadores;
    }


    /**
     * Se explicador nao existir na base de dados , cria-o.
     * @param explicador explicador a adicionar na base de dados.
     * @return Explicador
     */
    public Optional<Explicador> criarExplicador(Explicador explicador) {
        Optional<Explicador> optionalExplicador = procurarExplicador(explicador.getNome());
        if(optionalExplicador.isEmpty()) return Optional.of(this.explicadorRepo.save(explicador));
        return Optional.empty();
    }


    /**
     * Procura um explicador no repositorio com determinado nome.
     * @param nome nome do explicador a procurar.
     * @return Explicador
     */
    public Optional<Explicador> procurarExplicador(String nome) {
        return this.explicadorRepo.findByNome(nome);
    }


    /**
     * Apaga explicador pelo nome e cria um novo com o mesmo nome e campos editados.
     * @param explicador explicador a editar
     * @return Explicador criado
     */
    public Optional<Explicador> editarExplicador(Explicador explicador) {
        Optional<Explicador> explicadorOptional =  this.procurarExplicador(explicador.getNome());
        if(explicadorOptional.isEmpty()) return Optional.empty();
        Explicador e1 = explicadorOptional.get();
        this.explicadorRepo.deleteById(e1.getId());
        e1.setAtendimentos(explicador.getAtendimentos());
        e1.setDisponibilidades(explicador.getDisponibilidades());
        e1.setCadeiras(explicador.getCadeiras());
        return Optional.of(this.explicadorRepo.save(e1));
    }
}
