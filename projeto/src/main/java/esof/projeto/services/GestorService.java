package esof.projeto.services;

import esof.projeto.models.Cadeira;
import esof.projeto.models.Curso;
import esof.projeto.models.Faculdade;
import esof.projeto.repositories.CadeiraRepo;
import esof.projeto.repositories.CursoRepo;
import esof.projeto.repositories.FaculdadeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GestorService {

    private FaculdadeRepo faculdadeRepo;
    private CursoRepo cursoRepo;
    private CadeiraRepo cadeiraRepo;

    @Autowired
    public GestorService(FaculdadeRepo faculdadeRepo, CursoRepo cursoRepo, CadeiraRepo cadeiraRepo) {
        this.faculdadeRepo = faculdadeRepo;
        this.cursoRepo = cursoRepo;
        this.cadeiraRepo = cadeiraRepo;
    }

    public Optional<Faculdade> createFaculdade(Faculdade faculdade) {
        Optional<Faculdade> optionalClient = this.faculdadeRepo.findByNome(faculdade.getNome());
        if (optionalClient.isPresent()) {
            return Optional.empty();
        }
        Faculdade createdFaculdade = this.faculdadeRepo.save(faculdade);
        return Optional.of(createdFaculdade);
    }

    public Optional<Curso> createCursoByFaculdade(String faculdade, Curso curso) {
        Optional<Faculdade> optionalFaculdade = this.faculdadeRepo.findByNome(faculdade);
        if (optionalFaculdade.isPresent()) {
            if (optionalFaculdade.get().getCursos().contains(curso))
                return Optional.empty();
            optionalFaculdade.get().addCurso(curso);
            this.cursoRepo.save(curso);
            return Optional.of(curso);
        }
        return Optional.empty();
    }

    public Optional<Cadeira> createCadeiraByCurso(String curso, Cadeira cadeira) {
        Optional<Curso> optionalCurso = this.cursoRepo.findByNome(curso);
        if (optionalCurso.isPresent()) {
            if (optionalCurso.get().getCadeiras().contains(cadeira))
                return Optional.empty();
            optionalCurso.get().addCadeira(cadeira);
            this.cadeiraRepo.save(cadeira);
            return Optional.of(cadeira);
        }
        return Optional.empty();
    }
}
