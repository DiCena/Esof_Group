package esof.projeto.services;


import esof.projeto.caches.ExplicadorCache;
import esof.projeto.models.Explicador;
import esof.projeto.repositories.ExplicadorRepo;
import esof.projeto.services.filters.explicadores.ExplicadorFilterService;
import esof.projeto.services.filters.explicadores.FilterObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class ExplicadorService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private ExplicadorRepo explicadorRepo;

    private ExplicadorCache cache = ExplicadorCache.getInstance();

    private ExplicadorFilterService explicadorFilterService = new ExplicadorFilterService();

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
        if(!explicador1.getDisponibilidades().equals(explicador.getDisponibilidades())){
            this.logger.info("A alterar as disponibilidades...");
            explicador1.removeAllDisponibilidades();
            explicador1.addDisponibilidades(explicador.getDisponibilidades());
        }
        if(!explicador1.getCadeiras().equals(explicador.getCadeiras())){
            this.logger.info("A alterar as cadeiras...");
            explicador1.removeAllCadeiras();
            explicador1.addCadeiras(explicador.getCadeiras());
        }

        if(!explicador1.getAtendimentos().equals(explicador.getAtendimentos())){
            this.logger.info("A alterar os atendimentos...");
            explicador1.removeAllAtendimentos();
            explicador1.addAtendimentos(explicador.getAtendimentos());
        }

        if(!explicador1.getIdiomas().equals(explicador.getIdiomas())){
            this.logger.info("A alterar os idiomas...");
            explicador1.removeAllIdiomas();
            explicador1.addIdiomas(explicador.getIdiomas());
        }

        this.logger.info("A devolver explicador editado");
        Explicador explicadorSalvado = this.explicadorRepo.save(explicador1);
        cache.addExplicador(explicadorSalvado);
        System.out.println(explicadorSalvado.getDisponibilidades().toString());
        return Optional.of(explicadorSalvado);
    }

    /**
     * Devolve explicadores filtrados por parametros
     * @param searchParams parametros a filtrar
     * @return explicadores filtrados
     */
    public Set<Explicador> filterExplicador(Map<String, String> searchParams) {
        System.out.println(searchParams);
        this.logger.info("A filtrar explicadores");
        if(searchParams.isEmpty()) {
            this.logger.info("A devolver todos os explicadores");
            return this.findAll();   // Nao ha parametros de procura , devolve tudo
        }
        FilterObject filterObject = new FilterObject(searchParams);
        System.out.println(explicadorFilterService.filter(this.findAll(),filterObject).size());
        return explicadorFilterService.filter(this.findAll(),filterObject);
    }


    /**
     * Edita explicador , mas em vez de o editar na totalidade ,
     * apenas acrescenta valores
     * @param explicador a editar
     * @return Explicador
     */
    public Optional<Explicador> patchExplicador(Explicador explicador) {
        this.logger.info("A editar explicador com patch");
        Optional<Explicador> optionalExplicador = procurarExplicador(explicador.getNome());
        if(optionalExplicador.isEmpty()) return Optional.empty();
        this.logger.info("Explicador encontrado , a dar patch ...");
        Explicador explicador1 = optionalExplicador.get();
        if(explicador.getAtendimentos().size() != 0) explicador1.addAtendimentos(explicador.getAtendimentos());
        if(explicador.getCadeiras().size() != 0) explicador1.addCadeiras(explicador.getCadeiras());
        if(explicador.getDisponibilidades().size() != 0) explicador1.addDisponibilidades(explicador.getDisponibilidades());
        if(explicador.getIdiomas().size() != 0) explicador1.addIdiomas(explicador.getIdiomas());
        this.logger.info("A devolver explicador editado em patch");
        Explicador explicadorSalvado = this.explicadorRepo.save(explicador1);
        cache.addExplicador(explicadorSalvado);
        System.out.println(explicadorSalvado.getDisponibilidades().toString());
        return Optional.of(explicadorSalvado);
    }
}
