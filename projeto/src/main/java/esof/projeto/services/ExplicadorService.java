package esof.projeto.services;


import esof.projeto.models.Atendimento;
import esof.projeto.models.Explicador;
import esof.projeto.repositories.AtendimentoRepo;
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

    private AtendimentoRepo atendimentoRepo;


    private HashSet<Explicador> MRU = new HashSet<Explicador>(); // Cache

    @Autowired
    public ExplicadorService(ExplicadorRepo explicadorRepo, AtendimentoRepo atendimentoRepo) {

        this.explicadorRepo = explicadorRepo;
        this.atendimentoRepo = atendimentoRepo;
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
     * Primeiro utiliza a cache ( lista em memória)
     * Depois de procurar atualiza os valores da cache.
     * @param nome nome do explicador a procurar.
     * @return Explicador
     */
    public Optional<Explicador> procurarExplicador(String nome) {
        Optional<Explicador> explicador = procurarExplicadorCache(nome);    // Procura na cache.
        if(explicador.isPresent()) return explicador;
        return this.explicadorRepo.findByNome(nome);                        // Se não encontrar na cache , recorre à BD
    }


    /**
     * Procura explicador por nome na lista em memória.
     * @param nome nome do explicador a procurar na cache.
     * @return Explicador
     */
    private Optional<Explicador> procurarExplicadorCache(String nome) {
        for(Explicador explicador : this.MRU) {
            if(explicador.getNome().equals(explicador.getNome()))
                return Optional.of(explicador);
        }
        return Optional.empty();
    }


    private void addConsultaMRU(Explicador explicador) {

    }



    /**
     * Apaga os atributos antigos e substitui pelos atributos passados
     * no objeto explicador.
     * @param explicador explicador a editar
     * @return Explicador criado
     */
    public Optional<Explicador> editarExplicador(Explicador explicador) {
        Optional<Explicador> explicadorOptional =  this.procurarExplicador(explicador.getNome());
        if(explicadorOptional.isEmpty()) return Optional.empty();
        Explicador explicador1 = explicadorOptional.get();
        explicador1.removeAllAtendimentos();                                    // Remove atributos
        explicador1.addAtendimentos(explicador.getAtendimentos());              // Adiciona novos atributos
        explicador1.removeAllCadeiras();
        explicador1.addCadeiras(explicador.getCadeiras());
        explicador1.removeAllDisponibilidades();
        explicador1.addDisponibilidades(explicador.getDisponibilidades());
        explicador1.removeAllIdiomas();
        explicador1.addIdiomas(explicador.getIdiomas());
        return Optional.of(this.explicadorRepo.save(explicador1));
    }
}
