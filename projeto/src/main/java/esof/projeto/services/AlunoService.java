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

    public Set<Aluno> findAll(){
        Set<Aluno> alunos = new HashSet<>();
        for (Aluno aluno: this.alunoRepo.findAll())
            alunos.add(aluno);
        return alunos;
    }

    public Optional<Aluno> findById(Long id) {
        return this.alunoRepo.findById(id);
    }

    public Optional<Aluno> createAluno(Aluno aluno) {
        Optional<Aluno> optionalClient=this.alunoRepo.findByNome(aluno.getNome());
        if(optionalClient.isPresent()){
            return Optional.empty();
        }
        Aluno createdAluno=this.alunoRepo.save(aluno);
        return Optional.of(createdAluno);
    }
}
