package esof.projeto.services;

import esof.projeto.models.Aluno;
import esof.projeto.models.Atendimento;
import esof.projeto.models.Explicador;
import esof.projeto.models.body.AtendimentoBody;
import esof.projeto.repositories.AlunoRepo;
import esof.projeto.repositories.AtendimentoRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AlunoService {



    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private AlunoRepo alunoRepo;

    private AtendimentoRepo atendimentoRepo;


    private final ExplicadorService explicadorService;


    @Autowired
    public AlunoService(AlunoRepo alunoRepo, AtendimentoRepo atendimentoRepo, ExplicadorService explicadorService){
        this.alunoRepo=alunoRepo;
        this.atendimentoRepo=atendimentoRepo;
        this.explicadorService = explicadorService;
    }

    /**
     * vai ao repo buscar os alunos e armazena-os num hashSet
     * @return Todos os alunos da base de dados
     */
    public Set<Aluno> findAll(){
        this.logger.info("A procurar todos os alunos");
        Set<Aluno> alunos = new HashSet<>();
        for (Aluno aluno: this.alunoRepo.findAll())
            alunos.add(aluno);
        this.logger.info("A devolver todos os alunos");
        return alunos;
    }



    /**
     * Procura aluno por nome
     * @param nome nome do aluno a procurar.
     * @return Aluno
     */
    public Optional<Aluno> procurarAluno(String nome) {
        this.logger.info("A procurar " + nome + " na bd");
        return this.alunoRepo.findByNome(nome);
    }

    /**
     *
     * @param id id do aluno que se pretende encontrar
     * @return aluno com o id procurado
     */
    public Optional<Aluno> findById(Long id) {
        return this.alunoRepo.findById(id);
    }

    /**
     * se já existir um aluno com o nome retorna empty, caso contrário adiciona-o
     * á base de dados
     * @param aluno aluno que se pretende criar
     * @return success -> optional do aluno else-> optional.empty()
     */
    public Optional<Aluno> createAluno(Aluno aluno) {
        this.logger.info("A criar novo aluno " + aluno.getNome());
        Optional<Aluno> optionalClient=this.alunoRepo.findByNome(aluno.getNome());
        if(optionalClient.isPresent()){
            this.logger.info("Aluno já existia");
            return Optional.empty();
        }
        Aluno createdAluno=this.alunoRepo.save(aluno);
        this.logger.info("A devolver aluno criado");
        return Optional.of(createdAluno);
    }


    /**
     * Marca um atendimento
     * @param atendimentoBody class Atendimento com campos simplificados
     * @return Atendimento
     */
    public Optional<Atendimento> marcarAtendimento(AtendimentoBody atendimentoBody) {
        this.logger.info("A marcar atendimento para o explicador " + atendimentoBody.getExplicador());
        Optional<Aluno> optionalAluno = this.procurarAluno(atendimentoBody.getAluno());
        Optional<Explicador> optionalExplicador = explicadorService.procurarExplicador(atendimentoBody.getExplicador());
        if(optionalExplicador.isEmpty() ||
                optionalAluno.isEmpty() ||
                atendimentoBody.getDiaAtendimento() == null ||
                atendimentoBody.getHoraAtendimento() == null) return Optional.empty();
        Explicador explicador = optionalExplicador.get();
        Aluno aluno = optionalAluno.get();
        if(explicador.criarAtendimento(aluno,atendimentoBody.getDiaAtendimento(),atendimentoBody.getHoraAtendimento())){
            // caso consiga marcar o atendimento
            this.alunoRepo.save(aluno);
            this.logger.info("A devolver atendimento");
            return explicador.procurarAtendimento(atendimentoBody.getHoraAtendimento(),atendimentoBody.getDiaAtendimento());
        } else {
            this.logger.info("Não foi possível marcar atendimento");
            return Optional.empty();
        }
    }
}
