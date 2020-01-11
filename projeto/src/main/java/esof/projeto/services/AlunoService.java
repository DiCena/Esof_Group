package esof.projeto.services;

import esof.projeto.models.Aluno;
import esof.projeto.repositories.AlunoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AlunoService {

    private AlunoRepo alunoRepo;


    @Autowired
    public AlunoService(AlunoRepo alunoRepo){
        this.alunoRepo=alunoRepo;
    }

    /**
     * vai ao repo buscar os alunos e armazena-os num hashSet
     * @return Todos os alunos da base de dados
     */
    public Set<Aluno> findAll(){
        Set<Aluno> alunos = new HashSet<>();
        for (Aluno aluno: this.alunoRepo.findAll())
            alunos.add(aluno);
        return alunos;
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
        Optional<Aluno> optionalClient=this.alunoRepo.findByNome(aluno.getNome());
        if(optionalClient.isPresent()){
            return Optional.empty();
        }
        Aluno createdAluno=this.alunoRepo.save(aluno);
        return Optional.of(createdAluno);
    }
}
