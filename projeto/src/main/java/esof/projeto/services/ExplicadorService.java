package esof.projeto.services;


import esof.projeto.caches.ExplicadorCache;
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

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private ExplicadorRepo explicadorRepo;

    private ExplicadorCache cache = ExplicadorCache.getInstance();

    @Autowired
    public ExplicadorService(ExplicadorRepo explicadorRepo) {

        this.explicadorRepo = explicadorRepo;
    }


    /**
     * Devolve todos os explicadores na base de dados.
     * @return Explicadores
     */
    public Set<Explicador> findAll() {
        this.logger.info("A devolver todos os explicadores");
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
        this.logger.info("A tentar criar explicador");
        Optional<Explicador> optionalExplicador = procurarExplicador(explicador.getNome());
        if(optionalExplicador.isEmpty()) return Optional.of(this.explicadorRepo.save(explicador));
        this.logger.info("Explicador ja existente");
        return Optional.empty();
    }


    /**
     * Procura um explicador no repositorio com determinado nome.
     * Primeiro utiliza a cache ( lista em memória)
     * Atualiza a lista de cache com o ultimo explicador procurado
     * Se nao tiver na cache procura na BD
     * @param nome nome do explicador a procurar.
     * @return Explicador
     */
    public Optional<Explicador> procurarExplicador(String nome) {
        this.logger.info("A procurar explicador " + nome + " na cache");
        Optional<Explicador> explicadorCache = this.cache.consultarCache(nome); // Procura na cache
        if(explicadorCache.isPresent()) return explicadorCache; // Retorna da cache
        this.logger.info("Nao foi encontrado " + nome + " na cache");
        Optional<Explicador> explicadorBD = this.explicadorRepo.findByNome(nome);   // Nao encontrou na cache , busca na BD
        explicadorBD.ifPresent(explicador -> this.cache.addExplicador(explicador)); // Se tiver o explicador na BD , adiciona na cache
        return explicadorBD;
    }


    /**
     * Apaga os atributos antigos e substitui pelos atributos passados
     * no objeto explicador.
     * @param explicador explicador a editar
     * @return Explicador criado
     */
    public Optional<Explicador> editarExplicador(Explicador explicador) {
        this.logger.info("A tentar editar o explicador " + explicador.getNome());
        Optional<Explicador> explicadorOptional =  this.procurarExplicador(explicador.getNome());
        if(explicadorOptional.isEmpty()) return Optional.empty();
        this.logger.info("Explicador encontrado , a edita-lo ...");
        Explicador explicador1 = explicadorOptional.get();
        if(explicador1.equals(explicador)) return Optional.of(explicador1); //se sao iguais , nao faz nada
        explicador1.removeAllCadeiras();
        explicador1.addCadeiras(explicador.getCadeiras());
        explicador1.removeAllDisponibilidades();
        explicador1.addDisponibilidades(explicador.getDisponibilidades());
        explicador1.removeAllIdiomas();
        explicador1.addIdiomas(explicador.getIdiomas());
        this.logger.info("A devolver explicador editado");
        Explicador explicadorSalvado = this.explicadorRepo.save(explicador1);
        cache.addExplicador(explicadorSalvado);
        System.out.println(explicadorSalvado.getDisponibilidades().toString());
        return Optional.of(explicadorSalvado);
    }
}
